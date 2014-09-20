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

	private final Deque<String> items = new LinkedList<String>();

	private ClipboardManager() {
	}

	public static ClipboardManager getInstance() {
		return ClipboardManagerHolder.INSTANCE;
	}

	private static class ClipboardManagerHolder {
		private static final ClipboardManager INSTANCE = new ClipboardManager();
	}

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

	public String[] getItems() {
		return items.size() > 0 ? items.toArray(PluginConstants.NO_ITEMS) : PluginConstants.NO_ITEMS;
	}
}
