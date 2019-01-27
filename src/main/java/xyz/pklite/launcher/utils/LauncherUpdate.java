package xyz.pklite.launcher.utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.Settings;

public class LauncherUpdate implements Runnable
{
	@Override
	public void run()
	{
		String webVersion = null;
		try
		{
			webVersion = Unirest.get(Settings.VERSION_URL).asString().getBody();
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
		}

		if (!Settings.LAUNCHER_VERSION.equals(webVersion))
		{
			JOptionPane.showMessageDialog(null,"PKLite launcher is outdated. Download the" +
				" newest from the releases page on our github at " + Settings.RELEASES_URL);
			Utils.openWebpage(Settings.RELEASES_URL);
			System.exit(0);
		}
	}
}
