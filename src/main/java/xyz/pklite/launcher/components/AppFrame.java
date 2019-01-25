/*
 *  Copyright (c) 2019. PKLite  - All Rights Reserved
 *  Unauthorized modification, distribution, or possession of this source file, via any medium is strictly prohibited.
 *  Proprietary and confidential. Refer to PKLite License file for more information on full terms of this copyright and to determine what constitutes authorized use.
 *  Written by PKLite(ST0NEWALL, others) <stonewall@pklite.xyz>, 2019
 *
 *
 */

package xyz.pklite.launcher.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.Instant;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import org.json.JSONException;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.listeners.ButtonListener;
import xyz.pklite.launcher.listeners.NewsLinkListener;
import xyz.pklite.launcher.utils.Utils;

public class AppFrame extends JFrame
{
	private static final long serialVersionUID = -3130053101521666537L;
	private static Point initialClick;

	public static int appWidth, appHeight;

	public AppFrame()
	{
		setPreferredSize(Settings.frameSize);

		appWidth = (int) getPreferredSize().getWidth();
		appHeight = (int) getPreferredSize().getHeight();

		setUndecorated(true);
		setTitle(Settings.PROJECT_NAME);
		setLayout(null);
		getRootPane().setBorder(new LineBorder(Color.BLACK));
		getContentPane().setBackground(Settings.backgroundColor);

		addMenuBar();
		addNewsBox();
		addLinks();
		addHeader();
		addPlayButton();
		addProgressBar();

		setIconImage(Utils.getImage("favicon_large.png").getImage());
		addMouseListener();
		pack();
	}

	public static JProgressBar pbar;

	private void addProgressBar()
	{
		pbar = new JProgressBar();

		pbar.setUI(new BasicProgressBarUI()
		{
			protected Color getSelectionBackground()
			{
				return Settings.primaryColor;
			}

			protected Color getSelectionForeground()
			{
				return Settings.primaryColor;
			}
		});

		pbar.setBounds(100, appHeight - 35, appWidth - 100, 35);
		pbar.setBackground(Settings.backgroundColor.darker());
		pbar.setBorderPainted(false);
		pbar.setStringPainted(true);
		pbar.setString("Click Launch to play " + Settings.PROJECT_NAME + "!");
		pbar.setBorder(new EmptyBorder(0, 0, 0, 0));
		pbar.setForeground(new Color(25, 25, 25));
		Utils.setFont(pbar, "OpenSans-Regular.ttf", 13);
		add(pbar);
	}

	public static Control playButton = new Control("Launch");

	private void addPlayButton()
	{
		playButton.setActionCommand("play");
		playButton.setBackground(Settings.primaryColor);
		playButton.addActionListener(new ButtonListener());
		// (appWidth / 2) - (167 / 2)
		playButton.setBounds(0, appHeight - 35, 100, 35);
		Utils.setFont(playButton, "OpenSans-Regular.ttf", 16);
		add(playButton);
	}

	public static JLabel tooltip;

	private void addLinks()
	{
		tooltip = new JLabel("");
		tooltip.setBounds(135, appHeight - 100, 335, 25);
		tooltip.setHorizontalAlignment(SwingConstants.CENTER);
		Utils.setFont(tooltip, "OpenSans-Light.ttf", 16);
		add(tooltip);

		IconLabel discord = new IconLabel("\uf392", "Discord", 50, true);
		discord.setBounds(135, 50, 64, 64);
		add(discord);

		IconLabel webIconLabel = new IconLabel("\uf268", "Website", 50, true);
		webIconLabel.setBounds(205, 50, 64, 64);
		add(webIconLabel);

		IconLabel gitHubIconLabel = new IconLabel("\uf092", "GitHub", 50, true);
		gitHubIconLabel.setBounds(275, 50, 64, 64);
		add(gitHubIconLabel);

		IconLabel featuresIconLabel = new IconLabel("\uf080", "Features", 50, false);
		featuresIconLabel.setBounds(345, 50, 64, 64);
		add(featuresIconLabel);

		IconLabel docsIconLabel = new IconLabel("\uf15c", "Documentation", 50, false);
		docsIconLabel.setBounds(415, 50, 64, 64);
		add(docsIconLabel);

	}

	private void addNewsBox()
	{
		final int red = Settings.primaryColor.getRed();
		final int green = Settings.primaryColor.getGreen();
		final int blue = Settings.primaryColor.getBlue();

		JLabel status1 = new JLabel(Settings.PROJECT_NAME + ": Open Source PvP Client");
		status1.setForeground(Settings.primaryColor);
		status1.setHorizontalAlignment(SwingConstants.CENTER);
		status1.setBounds(0, 75, appWidth, 75);
		Utils.setFont(status1, "OpenSans-Light.ttf", 28);
		add(status1);
		JLabel status2 = new JLabel("<html><h3>Latest news:</h3></html>");
		status2.setForeground(Color.WHITE);
		status2.setHorizontalAlignment(SwingConstants.CENTER);
		status2.setBounds(0, 132, appWidth, 30);
		Utils.setFont(status2, "OpenSans-Light.ttf", 16);
		add(status2);


		try
		{
			HttpResponse<JsonNode> newsFeedResponse = Unirest.get("https://www.pklite.xyz/wp-json/wp/v2/posts").asJson();
			for (int i = 0; i < Settings.NEWS_LIMIT; i++)
			{
				JLabel newsLabel = new JLabel();
				String newsEntry = "<html>";
				String date = newsFeedResponse.getBody().getArray().getJSONObject(i).getString("date");
				Date date1 = java.util.Date.from(Instant.parse(date + "Z"));
				String link = newsFeedResponse.getBody().getArray().getJSONObject(i).getString("link");
				String title = newsFeedResponse.getBody().getArray().getJSONObject(i).getJSONObject("title").getString("rendered");
				String postDate = Utils.dateFormat.format(date1);
				newsEntry += postDate + ": ";
				newsEntry += title;
				newsEntry += "</html>";
				newsLabel.setText(newsEntry);
				newsLabel.setForeground(Color.WHITE);
				newsLabel.setHorizontalAlignment(SwingConstants.CENTER);
				newsLabel.setBounds(0, 160 + (30 * i), appWidth, 30);
				newsLabel.addMouseListener(new NewsLinkListener(link, newsLabel));
				Utils.setFont(newsLabel, "OpenSans-Light.ttf", 14);
				add(newsLabel);
			}
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
			status2.setText("Error fetching news feed");
			status2.repaint();
		}
		catch (JSONException e)
		{

		}
	}

	private void addMenuBar()
	{
		MenuBar bar = new MenuBar();
		bar.setBounds(0, 0, appWidth, 25);
		add(bar);
	}

	@SuppressWarnings("unused")
	private void addHeader()
	{
		JLabel logo = new JLabel(Utils.getImage("logo.png"));
		logo.setBounds(30, 0, 350, 60);
		JLabel head = new JLabel("V" + Settings.LAUNCHER_VERSION);
		head.setOpaque(true);
		head.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.2f));
		head.setBounds(-1, 25, appWidth, 112);
		add(head);
	}

	private void addMouseListener()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{

				int iX = initialClick.x;
				int iY = initialClick.y;

				if (iX >= 0 && iX <= getPreferredSize().getWidth() && iY >= 0 && iY <= 25)
				{
					int thisX = getLocation().x;
					int thisY = getLocation().y;

					int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
					int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

					int X = thisX + xMoved;
					int Y = thisY + yMoved;
					setLocation(X, Y);
				}
			}
		});
	}

}
