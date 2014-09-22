package ar.com.mulesoft.nicecopyandpaste.ui.gists;

import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import ar.com.mulesoft.nicecopyandpaste.ui.Activator;
import ar.com.mulesoft.nicecopyandpaste.ui.nls.Messages;
import ar.com.mulesoft.nicecopyandpaste.ui.util.TableViewerHelper;

/**
 * A rectangular grid that contains the Id, Description and HTML URL for every gist.
 * 
 * @author ldebello
 */
public class GistsView extends ViewPart {

	private GistsFilter filter;
	private TableViewer tableViewer = null;
	
	public GistsView() {
	}

	private static final String GIST_BROWSER_ID = "NiceCopyPaste";
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		Label lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText(Messages.GistsView_LblDescription);
		
		final Text txtDescription = new Text(parent, SWT.BORDER);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				
		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		
		filter = new GistsFilter();
	    	    
		tableViewer = new TableViewer(composite, SWT.VIRTUAL | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableViewerHelper.addFixedColumn(tableViewer, layout, Messages.GistsView_ColumnId, 140);
		TableViewerHelper.addWeightColumn(tableViewer, layout, Messages.GistsView_ColumnDescription, 2);
		TableViewerHelper.addWeightColumn(tableViewer, layout, Messages.GistsView_ColumnURL, 3);
		
		tableViewer.setContentProvider(new GistsContentProvider());
		tableViewer.setLabelProvider(new GistsLabelProvider());		
		tableViewer.addDoubleClickListener(new GistsDoubleClickListener());
		tableViewer.addFilter(filter);

		txtDescription.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setDescriptionToFilter(txtDescription.getText());
				tableViewer.refresh();
			}
		});
		
		refreshGrid();
	}
	
	public void refreshGrid() {
		Job job = new Job("Refreshing Gists") {
			protected IStatus run(IProgressMonitor monitor) {
				final List<Gist> gists = GistsFacade.getInstance().getGists(Activator.getDefault());
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {			
					public void run() {
						if (tableViewer.getControl().isDisposed()) {
							return;
						}				    
						tableViewer.setInput(gists);				
					}
				});		
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.schedule();
	}

	@Override
	public void setFocus() {
	}

	private static class GistsContentProvider implements IStructuredContentProvider{

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {	
			return ((List<Gist>) inputElement).toArray();
		}
	}
	
	private static class GistsLabelProvider implements ITableLabelProvider {
		
		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Gist gist = (Gist) element;
			String result = null;
			switch(columnIndex){
			case 0:
				result = gist.getId();
				break;
			case 1:
				result = gist.getDescription();
				break;
			case 2:
				result = gist.getHtmlUrl();
				break;			
			}
			return result;
		}		
	}
	
	private static class GistsDoubleClickListener implements IDoubleClickListener {

		public void doubleClick(DoubleClickEvent event) {
			IStructuredSelection sel = (IStructuredSelection) event.getSelection();
			Gist gist = (Gist) sel.getFirstElement();
			if(gist != null){		
				try {
					IWebBrowser browser = PlatformUI.getWorkbench().getBrowserSupport().createBrowser(GIST_BROWSER_ID);
					browser.openURL(new URL(gist.getHtmlUrl()));
				} catch (Exception e) {
					IStatus browserErrorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), null);
					StatusManager.getManager().handle(browserErrorStatus, StatusManager.SHOW);
				}				
			}			
		}		
	}
	
	private static class GistsFilter extends ViewerFilter {

		private String descriptionToFilter;

		public void setDescriptionToFilter(String description) {
			this.descriptionToFilter = ".*" + description + ".*";
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			Gist gist = (Gist) element;
			return (descriptionToFilter == null || descriptionToFilter.length() == 0) ? true : gist.getDescription().matches(descriptionToFilter);
		}
	}
}
