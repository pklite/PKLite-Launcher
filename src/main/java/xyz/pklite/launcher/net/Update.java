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
import xyz.pklite.launcher.Settings;

public class Update
{

	public static byte updateExists()
	{
		File file = new File(Settings.SAVE_DIR + Settings.SAVE_NAME);
		if (!file.exists())
		{
			return 1;
		}

		String localCheck = Checksum.getLocalChecksum();
		String remoteCheck = Checksum.getRemoteChecksum();

		if (remoteCheck == null || localCheck == null)
		{
			return 2;
		}

		if (!remoteCheck.equalsIgnoreCase(localCheck))
		{
			return 3;
		}

		return 0;
	}

}
