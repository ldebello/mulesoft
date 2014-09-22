package ar.com.mulesoft.nicecopyandpaste.ui.nls;

import org.eclipse.osgi.util.NLS;

/**
 * Class Messages handles I18N functionality.
 *
 * @author ldebello
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "resources.Messages";

	public static String PluginPreferencePage_MaximumNumberOfItems;
	public static String PluginPreferencePage_IsPluginMonitoring;
	public static String PluginPreferencePage_GitHubUserName;
	public static String PluginPreferencePage_GitHubPassword;
	public static String PluginPreferencePage_Description;
		
	public static String ListDialog_Title;

	public static String GistsView_LblDescription;
	
	public static String GistsView_ColumnId;
	public static String GistsView_ColumnDescription;
	public static String GistsView_ColumnURL;
	
	public static String AddGistPage_Title;
	public static String AddGistPage_LblDescription;
		
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
