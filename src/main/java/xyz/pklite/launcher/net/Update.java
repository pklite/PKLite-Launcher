/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.net;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import xyz.pklite.launcher.Settings;

public class Update
{

	public static UpdateStatus updateExists()
	{
		File file = new File(Settings.SAVE_DIR + Settings.SAVE_NAME);
		String localHash = null;
		String remoteHash = null;
		if (!file.exists())
		{
			return UpdateStatus.FIRST_DOWNLOAD;
		}
		try (InputStream is = Files.newInputStream(file.toPath()))
		{
			localHash = org.apache.commons.codec.digest.DigestUtils.md5Hex(is.readAllBytes());
			remoteHash = new String(Unirest.get(Settings.HASH_URL).asBinary().getRawBody().readAllBytes());
			if (!localHash.equalsIgnoreCase(remoteHash))
			{
				System.out.println(remoteHash);
				System.out.println(localHash);
				System.out.println("update");
				return UpdateStatus.UPDATE_NEEDED;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return UpdateStatus.FIRST_DOWNLOAD;
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
		}
		return UpdateStatus.UP_TO_DATE;
	}
}
