/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;
import xyz.pklite.launcher.Settings;

@SuppressWarnings("serial")
public class Control extends JButton implements MouseListener
{

	public Control(Icon image)
	{
		super(image);

		setBorderPainted(false);
		setFocusable(false);
		addMouseListener(this);
	}

	public Control(String name)
	{
		super(name);
		setForeground(Settings.buttonDefaultColor);
		setBorderPainted(false);
		setFocusable(false);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent me)
	{
		setBackground(this.getBackground().darker());
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
		setBackground(this.getBackground().brighter());

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
}
