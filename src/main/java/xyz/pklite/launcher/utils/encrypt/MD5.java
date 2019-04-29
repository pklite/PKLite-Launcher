/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{

	private static String convertToHex(byte[] data)
	{
		StringBuilder buf = new StringBuilder();
		for (byte datum : data)
		{
			int halfbyte = (datum >>> 4) & 0x0F;
			int two_halfs = 0;
			do
			{
				if (halfbyte <= 9)
				{
					buf.append((char) ('0' + halfbyte));
				}
				else
				{
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = datum & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String encrypt(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
		byte[] md5hash = md.digest();
		return convertToHex(md5hash);
	}

}
