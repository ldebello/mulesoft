package ar.com.mulesoft.nicecopyandpaste.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ar.com.mulesoft.nicecopyandpaste.ui.Activator;
import ar.com.mulesoft.nicecopyandpaste.ui.constants.PluginConstants;
import ar.com.mulesoft.nicecopyandpaste.ui.nls.Messages;

/**
 * This page manages the plug-in preferences
 *
 * @author ldebello
 *
 */
public class PluginPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public PluginPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.PluginPreferencePage_Description);
	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor isPluginActive = new BooleanFieldEditor(PluginConstants.IS_PLUGIN_MONITORING, Messages.PluginPreferencePage_IsPluginMonitoring, getFieldEditorParent());
		addField(isPluginActive);
		
		StringFieldEditor userName = new StringFieldEditor(PluginConstants.GIT_HUB_USER_NAME, Messages.PluginPreferencePage_GitHubUserName, getFieldEditorParent());
		addField(userName);
		
		StringFieldEditor password = new StringFieldEditor(
				PluginConstants.GIT_HUB_PASSWORD,
				Messages.PluginPreferencePage_GitHubPassword,
				getFieldEditorParent()) {

			@Override
			protected void doFillIntoGrid(Composite parent, int numColumns) {
				super.doFillIntoGrid(parent, numColumns);

				getTextControl().setEchoChar('*');
			}
		};
		
		addField(password);
		
		IntegerFieldEditor maximumNumberOfItems = new IntegerFieldEditor(PluginConstants.MAX_NUMBER_OF_ITEMS, Messages.PluginPreferencePage_MaximumNumberOfItems, getFieldEditorParent());
		addField(maximumNumberOfItems);
	}

}
