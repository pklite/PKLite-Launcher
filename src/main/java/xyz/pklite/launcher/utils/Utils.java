/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.utils;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.components.AppFrame;

public class Utils
{

	public static DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");

	/**
	 * Uses the ProcessBuilder class to launch the client.jar from the specified
	 * folder
	 */
	public static void launchClient()
	{
		AppFrame.playButton.setEnabled(true);
		try
		{
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", Settings.SAVE_DIR + Settings.SAVE_NAME);
			pb.directory(new File(System.getProperty("java.home") + File.separator + "bin"));
			final Process proc = pb.start();
			AppFrame.playButton.setEnabled(false);
			proc.waitFor(5, TimeUnit.SECONDS);
			System.exit(0);

		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error launching " + Settings.PROJECT_NAME
				+ " client." + e.getMessage(), "Launch Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Loads a custom font from the data/font folder. Font must be either otf or
	 * ttf.
	 *
	 * @param fontName
	 * @param size
	 */
	public static void setFont(Component c, String fontName, float size)
	{
		try
		{
			Font font = Font.createFont(0, Utils.class.getResource("/fonts/" +
				fontName).openStream());
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			font = font.deriveFont(size);
			c.setFont(font);
		}
		catch (FontFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Opens the users browser and goes to the specified URL
	 *
	 * @param url
	 */
	public static void openWebpage(String url)
	{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
		{
			try
			{
				desktop.browse(new URL(url).toURI());
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
	}

	public static ImageIcon getImage(String name)
	{
		return new ImageIcon(Utils.class.getResource("/img/" + name));
	}
}