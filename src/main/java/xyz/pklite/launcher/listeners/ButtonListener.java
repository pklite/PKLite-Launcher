/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.Launcher;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.components.AppFrame;
import xyz.pklite.launcher.net.NIODownload;
import xyz.pklite.launcher.net.Update;
import xyz.pklite.launcher.net.UpdateStatus;
import xyz.pklite.launcher.utils.Utils;

public class ButtonListener implements ActionListener
{


	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case "_":
				Launcher.app.setState(JFrame.ICONIFIED);
				break;
			case "X":
				System.exit(0);
				break;
			case "play":
				Thread playThread = new Thread(() ->
				{
					AppFrame.playButton.setEnabled(false);
					AppFrame.pbar.setString("Checking for Client Updates...");

					UpdateStatus updateStatus = Update.updateExists();
					if (updateStatus.equals(UpdateStatus.UP_TO_DATE))
					{
						AppFrame.pbar.setString("Now Launching " + Settings.PROJECT_NAME + "!");
						Utils.launchClient();
						return;
					}
					if (updateStatus.equals(UpdateStatus.FIRST_DOWNLOAD) ||
						updateStatus.equals(UpdateStatus.UPDATE_NEEDED))

					{
						try
						{
							NIODownload nioDownload = new NIODownload(Settings.DOWNLOAD_URL);
							Future future = Executors.newSingleThreadExecutor().submit(nioDownload);
							future.get();
							AppFrame.pbar.setString("Update successful!");
							Utils.launchClient();
						}

						catch (Exception e1)
						{
							JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
					if (updateStatus.equals(UpdateStatus.REMOTE_ERROR))
					{
						JOptionPane.showMessageDialog(null, "There was an error contacting " +
								"PKLite servers",
							"Error!", JOptionPane.ERROR_MESSAGE);
					}

				});
				playThread.start();
				break;

			default:
				break;
		}
	}
}
