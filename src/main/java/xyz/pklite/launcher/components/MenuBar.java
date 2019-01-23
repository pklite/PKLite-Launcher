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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.listeners.ButtonListener;
import xyz.pklite.launcher.utils.Utils;

@SuppressWarnings("serial")
class MenuBar extends JPanel
{
	MenuBar()
	{
		setLayout(null);
		setBounds(0, 0, AppFrame.appWidth, 25);
		setBackground(new Color(60, 60, 60));

		Control exit = new Control("X");
		exit.setBackground(Settings.primaryColor);
		exit.addActionListener(new ButtonListener());
		exit.setBounds(AppFrame.appWidth - 50, 0, 50, 25);
		add(exit);

		Control mini = new Control("_");
		mini.setBackground(Settings.backgroundColor);
		mini.addActionListener(new ButtonListener());
		mini.setBounds(AppFrame.appWidth - 100, 0, 50, 25);
		add(mini);

		JLabel title = new JLabel("" + Settings.PROJECT_NAME + " Client Launcher");
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		Utils.setFont(title, "OpenSans-Regular.ttf", 14);
		title.setBounds(0, 0, AppFrame.appWidth, 25);

		JLabel icon = new JLabel();

		ImageIcon imageIcon = Utils.getImage("favicon.png");
		icon.setIcon(imageIcon);
		icon.setBounds(3, -2, 24, 28);
		add(icon);
		add(title);
	}
}
