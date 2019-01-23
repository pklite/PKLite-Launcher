/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import xyz.pklite.launcher.utils.Utils;

public class NewsLinkListener implements MouseListener
{
	private final String NEWS_LINK;
	private JLabel newsLinkJLabel;

	public NewsLinkListener(String news_link)
	{
		NEWS_LINK = news_link;
	}

	public NewsLinkListener(String NEWS_LINK, JLabel newsLinkJLabel)
	{
		this.NEWS_LINK = NEWS_LINK;
		this.newsLinkJLabel = newsLinkJLabel;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Utils.openWebpage(this.NEWS_LINK);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		if (this.newsLinkJLabel != null)
		{
			this.newsLinkJLabel.setForeground(Color.GREEN);
			this.newsLinkJLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		}
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if (this.newsLinkJLabel != null)
		{
			if (!this.newsLinkJLabel.getBorder().equals(null))
			{
				this.newsLinkJLabel.setForeground(Color.WHITE);
				this.newsLinkJLabel.setBorder(null);
			}
		}
	}
}
