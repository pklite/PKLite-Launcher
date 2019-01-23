/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.net;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.Launcher;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.components.AppFrame;
import xyz.pklite.launcher.utils.Utils;

public class Download extends Observable implements Runnable
{

	public static final String[] STATUSES = {"Downloading", "Paused", "Complete", "Cancelled", "Error"};
	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETE = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;
	private static final int MAX_BUFFER_SIZE = 1024;
	private URL url;
	private int size;
	private int downloaded;
	private int status;

	public Download(String url)
	{
		try
		{
			this.url = new URL(url);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
	}

	public String getUrl()
	{
		return url.toString();
	}

	public int getSize()
	{
		return size;
	}

	public float getProgress()
	{
		return ((float) downloaded / size) * 100;
	}

	public int getStatus()
	{
		return status;
	}

	public void pause()
	{
		status = PAUSED;
		stateChanged();
	}

	public void resume()
	{
		status = DOWNLOADING;
		stateChanged();
		download();
	}

	public void cancel()
	{
		status = CANCELLED;
		stateChanged();
	}

	private void error()
	{
		status = ERROR;
		stateChanged();
	}

	public void download()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("unused")
	private String getFileName(URL url)
	{
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	public void run()
	{
		AppFrame.playButton.setEnabled(false);
		RandomAccessFile file = null;
		InputStream stream = null;

		try
		{
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
			connection.connect();

			if (connection.getResponseCode() / 100 != 2)
			{
				error();
			}

			int contentLength = connection.getContentLength();

			if (contentLength < 1)
			{
				error();
			}

			if (size == -1)
			{
				size = contentLength;
				stateChanged();
			}

			file = new RandomAccessFile(Settings.SAVE_DIR + Settings.SAVE_NAME, "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();

			int lastNum = 0;

			while (status == DOWNLOADING)
			{

				byte[] buffer;

				if (size - downloaded > MAX_BUFFER_SIZE)
				{
					buffer = new byte[MAX_BUFFER_SIZE];
				}
				else
				{
					buffer = new byte[size - downloaded];
				}

				int read = stream.read(buffer);

				if (read == -1)
				{
					break;
				}

				int progress = (int) getProgress();

				if (progress > lastNum)
				{
					AppFrame.pbar.setValue(progress);
					lastNum = progress;
					AppFrame.pbar.setString("Downloading Update: " + progress + "%");
				}

				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			if (status == DOWNLOADING)
			{
				status = COMPLETE;
				stateChanged();
				AppFrame.pbar.setValue(0);
				AppFrame.pbar.setString("Click Launch to play " + Settings.PROJECT_NAME + "!");
				Utils.launchClient();
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(Launcher.app,
				e.getMessage(),
				"Inane error",
				JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			if (file != null)
			{
				try
				{
					file.close();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(Launcher.app,
						e.getMessage(),
						"Inane error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
			if (stream != null)
			{
				try
				{
					stream.close();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(Launcher.app,
						e.getMessage(),
						"Inane error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void stateChanged()
	{
		setChanged();
		notifyObservers();
	}

}