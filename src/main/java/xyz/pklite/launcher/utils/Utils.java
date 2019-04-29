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
import java.io.PrintStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.HardwareAccelerationMode;
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
			HardwareAccelerationMode selectedMode = (HardwareAccelerationMode) AppFrame.hardwareAccelerationComboBox.getSelectedItem();
			ProcessBuilder pb = new ProcessBuilder(selectedMode.toParams());
			pb.directory(new File(System.getProperty("java.home") + File.separator + "bin"));
			final Process proc = pb.start();
			System.setOut(new PrintStream(proc.getOutputStream()));
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
	 * ttf.	 *
	 * @param fontName the file name of the font to open
	 * @param size the size you want the font to be
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
	 * Opens the users browser and goes to the specified URL	 *
	 * @param url the String of the web address
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
				JOptionPane.showMessageDialog(null, "Error opening website " + url);
			}
		}
	}

	/**
	 * Gets an imageicon based on the file name of the image file
	 * @param name the name of the image file
	 * @return the ImageIcon object
	 */
	public static ImageIcon getImage(String name)
	{
		return new ImageIcon(Utils.class.getResource("/img/" + name));
	}
}
