package ar.com.mulesoft.nicecopyandpaste.ui.nls;

import org.eclipse.osgi.util.NLS;

/**
 * Class Messages handles I18N functionality
 *
 * @author ldebello
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "resources.Messages";

	public static String PluginPreferencePage_MaximumNumberOfItems;
	public static String PluginPreferencePage_IsPluginMonitoring;
	public static String PluginPreferencePage_Description;
	public static String ListDialog_Title;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
