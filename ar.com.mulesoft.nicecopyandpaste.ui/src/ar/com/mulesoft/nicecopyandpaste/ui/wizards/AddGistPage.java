package ar.com.mulesoft.nicecopyandpaste.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ar.com.mulesoft.nicecopyandpaste.ui.nls.Messages;

/**
 * Wizard page which is shown to user. 
 * 
 * @author ldebello
 */
public class AddGistPage extends WizardPage {

	private Text txtDescription;
	private Text txtGist;

	private String fileName;
	private String gist;
	
	public AddGistPage(String fileName, String gist) {
		super("AddGistPage");
		setTitle(Messages.AddGistPage_Title);
		
		this.fileName = fileName;
		this.gist = gist;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));
		setControl(container);

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText(Messages.AddGistPage_LblDescription);

		txtDescription = new Text(container, SWT.BORDER);
		txtDescription.setText(fileName);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtGist = new Text(container, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		txtGist.setText(gist);
		txtGist.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}
	
	public String getDescription() {
		return txtDescription.getText();
	}
	
	public String getGist() {
		return txtGist.getText();
	}

}