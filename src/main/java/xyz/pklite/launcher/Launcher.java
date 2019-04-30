/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Color;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import xyz.pklite.launcher.components.AppFrame;
import xyz.pklite.launcher.utils.LauncherUpdate;

public class Launcher
{

	public static AppFrame app;

	public static void main(String[] main)
	{

		new File(Settings.SAVE_DIR).mkdirs();
		UIManager.put("Button.select", new Color(1.0f, 1.0f, 1.0f, 0.05f));
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		app = new AppFrame();

		app.setVisible(true);
		app.setLocationRelativeTo(null);
		ExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		executorService.submit(new LauncherUpdate());


	}
}