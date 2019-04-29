package xyz.pklite.launcher.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DropDownListener implements ItemListener
{
	/**
	 * Invoked when an item has been selected or deselected by the user.
	 * The code written for this method performs the operations
	 * that need to occur when an item is selected (or deselected).
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		System.out.println(e.getItem());

	}
}
