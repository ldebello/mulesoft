package ar.com.mulesoft.nicecopyandpaste.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;

import ar.com.mulesoft.nicecopyandpaste.ui.Activator;
import ar.com.mulesoft.nicecopyandpaste.ui.gists.GistsFacade;

/**
 * Wizard to create a new gists.
 * 
 * @author ldebello
 */
public class AddGistWizard extends Wizard {

	private String fileName;
	private String gist;
	
	private AddGistPage page;
	
	public AddGistWizard() {	
	}
	
	public AddGistWizard(String fileName, String gist) {
		this.fileName = fileName;
		this.gist = gist;
	}
	
	public void addPages() {
		page = new AddGistPage(fileName, gist);
		addPage(page);
	}

	@Override
	public boolean performFinish() {		
		gist = page.getGist();
		final String description = page.getDescription();
		
		final Map<String, String> data = new HashMap<String, String>();
		data.put(fileName, gist);

		Job job = new Job("Creating Gist") {
			protected IStatus run(IProgressMonitor monitor) {
				GistsFacade.getInstance().publishGist(Activator.getDefault(), description, data);
				
				// dispatchNotificationEvent();
		           
				monitor.done();
				
				return Status.OK_STATUS;
			}

			/*
			private void dispatchNotificationEvent() {
				BundleContext ctx = FrameworkUtil.getBundle(AddGistWizard.class).getBundleContext();
		        ServiceReference<EventAdmin> ref = ctx.getServiceReference(EventAdmin.class);
		        EventAdmin eventAdmin = ctx.getService(ref);
		        Map<String,Object> properties = new HashMap<String, Object>();

		        Event event = new Event("viewCommunication/AddGistCompleted", properties);
		        eventAdmin.postEvent(event);
			}
			*/
		};
		job.setUser(false);
		job.schedule();
			
		return Boolean.TRUE;
	}
}