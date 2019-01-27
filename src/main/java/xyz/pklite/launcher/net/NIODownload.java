package xyz.pklite.launcher.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import xyz.pklite.launcher.Settings;
import xyz.pklite.launcher.components.AppFrame;

public class NIODownload implements Runnable
{

	private final URL url;

	private ReadableByteChannel readableByteChannel;
	private FileOutputStream fileOutputStream;
	private FileChannel fileChannel;

	public NIODownload(String url) throws IOException
	{
		this.url = new URL(url);

	}

	@Override
	public void run()
	{
		try
		{
			this.readableByteChannel = Channels.newChannel(this.url.openStream());
			this.fileOutputStream = new FileOutputStream(Settings.SAVE_DIR + Settings.SAVE_NAME);
			AppFrame.pbar.setString("Downloading Update . . .");
			this.fileChannel = fileOutputStream.getChannel();
			this.fileOutputStream.getChannel()
				.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
