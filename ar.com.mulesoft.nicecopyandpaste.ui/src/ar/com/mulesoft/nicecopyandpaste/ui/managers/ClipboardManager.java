package ar.com.mulesoft.nicecopyandpaste.ui.managers;

import java.util.Deque;
import java.util.LinkedList;

import ar.com.mulesoft.nicecopyandpaste.ui.Activator;
import ar.com.mulesoft.nicecopyandpaste.ui.constants.PluginConstants;

/**
 * This class stores the items used by the user
 *
 * @author ldebello
 *
 */
public class ClipboardManager {

	private Deque<String> items = new LinkedList<String>();

	private ClipboardManager() {
	}

	public static ClipboardManager getInstance() {
		return ClipboardManagerHolder.INSTANCE;
	}

	private static class ClipboardManagerHolder {
		private static final ClipboardManager INSTANCE = new ClipboardManager();
	}

	/**
	 * Add the specified element as the first element in the history
	 * if the element already exists, it will remove it and add it again as first item
	 * 
	 * This method takes care of checking the maximum size configured in the preferences and remove the old items
	 * 
	 * @param contents item to be added to the clipboard history
	 */
	public void add(String contents) {
		if (items.contains(contents)) {
			items.remove(contents);
		}

		int maxNumberOfItems = Activator.getDefault().getMaxNumberOfItems();

		while (items.size() >= maxNumberOfItems) {
			items.removeLast();
		}

		items.addFirst(contents);
	}
	
	public void clear() {
		items = new LinkedList<String>();
	}

	public String[] getItems() {
		return items.size() > 0 ? items.toArray(PluginConstants.NO_ITEMS) : PluginConstants.NO_ITEMS;
	}
}
