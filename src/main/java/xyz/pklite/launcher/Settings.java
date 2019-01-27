/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

public class Settings
{

	public static final String PROJECT_NAME = "PKLite";
	public static final String DOWNLOAD_URL = "https://www.pklite.xyz/releases/client.jar";
	public static final String SAVE_NAME = "client.jar";
	public static final String SAVE_DIR = System.getProperty("user.home") + File.separator;
	public static final String HOMEPAGE_URL = "pklite.xyz";
	public static final String VERSION_URL = "https://www.pklite.xyz/releases/launcher";
	public static final String RELEASES_URL = "https://github.com/pklite/PKLite-Launcher/releases";
	public static final String LAUNCHER_VERSION = "1.0.2";
	public static final int NEWS_LIMIT = 4;

	public static final Dimension frameSize = new Dimension(600, 350);
	public static final Color borderColor = new Color(0, 0, 0);
	public static final Color backgroundColor = new Color(30, 30, 30);
	public static final Color primaryColor = new Color(59, 166, 226); // 226, 166, 59
	public static final Color iconShadow = new Color(0, 0, 0);
	public static final Color buttonDefaultColor = new Color(255, 255, 255);

	public static final String discord = "https://discord.gg/Dp3HuFM";
	public static final String website = "https://pklite.xyz";
	public static final String github = "https://github.com/pklite/";
	public static final String features = "https://www.pklite.xyz/features/";
	public static final String wiki = "https://www.pklite.xyz/wiki/";

}
