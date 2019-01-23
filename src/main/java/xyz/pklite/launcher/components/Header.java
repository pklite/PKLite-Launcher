/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.components;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import xyz.pklite.launcher.utils.Utils;

@SuppressWarnings("serial")
public class Header extends JLabel
{

	public Header(String text)
	{
		super(text);
		setForeground(Color.WHITE);
		setHorizontalAlignment(SwingConstants.CENTER);
		Utils.setFont(this, "OpenSans-Light.ttf", 32);
	}

}
