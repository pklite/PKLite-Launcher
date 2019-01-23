/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import xyz.pklite.launcher.Settings;

public class Checksum
{

	static String getLocalChecksum()
	{
		File local = new File(Settings.SAVE_DIR + Settings.SAVE_NAME);
		try (FileInputStream fis = new FileInputStream(local))
		{
			return Checksum.calculateMd5(fis);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getRemoteChecksum()
	{
		try (InputStream stream = new URL(Settings.DOWNLOAD_URL).openStream())
		{
			return Checksum.calculateMd5(stream);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,
				"Error checking for updates, please try again later", "Update Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return null;
		}
	}

	public static String calculateMd5(final InputStream inputStream)
	{
		return calculateDigest(inputStream);
	}

	private static String calculateDigest(final InputStream inputStream)
	{
		final byte[] buffer = new byte[4096];
		final MessageDigest messageDigest = getMessageDigest("MD5");
		messageDigest.reset();
		int bytesRead;
		try
		{
			while ((bytesRead = inputStream.read(buffer)) != -1)
			{
				messageDigest.update(buffer, 0, bytesRead);
			}
		}
		catch (IOException e)
		{
			System.err.println("Error making a '" + "MD5" + "' digest on the inputstream");
		}
		return toHex(messageDigest.digest());
	}

	public static String toHex(final byte[] ba)
	{
		int baLen = ba.length;
		char[] hexChars = new char[baLen * 2];
		int cIdx = 0;
		for (int i = 0; i < baLen; ++i)
		{
			hexChars[cIdx++] = hexdigit[(ba[i] >> 4) & 0x0F];
			hexChars[cIdx++] = hexdigit[ba[i] & 0x0F];
		}
		return new String(hexChars);
	}

	public static MessageDigest getMessageDigest(final String algorithm)
	{
		MessageDigest messageDigest = null;
		try
		{
			messageDigest = MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("The '" + algorithm + "' algorithm is not available");
		}
		return messageDigest;
	}

	private static final char[] hexdigit = {
		'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b',
		'c', 'd', 'e', 'f'
	};


}
