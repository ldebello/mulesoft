package ar.com.mulesoft.nicecopyandpaste.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ar.com.mulesoft.nicecopyandpaste.ui.constants.PluginConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ar.com.mulesoft.nicecopyandpaste.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	protected void initializeDefaultPreferences(IPreferenceStore store) {
        store.setDefault(PluginConstants.MAX_NUMBER_OF_ITEMS, 25);
        store.setDefault(PluginConstants.IS_PLUGIN_MONITORING, Boolean.TRUE);
    }

	public boolean isPluginMonitoringCutAndCopy() {
        return getPreferenceStore().getBoolean(PluginConstants.IS_PLUGIN_MONITORING);
    }

	public int getMaxNumberOfItems() {
        return getPreferenceStore().getInt(PluginConstants.MAX_NUMBER_OF_ITEMS);
    }
}
