package ar.com.mulesoft.nicecopyandpaste.ui.handlers;

import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import ar.com.mulesoft.nicecopyandpaste.ui.managers.ClipboardManager;
import ar.com.mulesoft.nicecopyandpaste.ui.nls.Messages;

/**
 * This handler shows a pop-up containing every item stored in the clipboard history.
 *
 * @author ldebello
 */
public class PastePopUpHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String[] items = ClipboardManager.getInstance().getItems();
		IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbench == null || items.length == 0) {
			return null;
		}

		IWorkbenchPage page = workbench.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		StyledText styledText = (StyledText) editor.getAdapter(Control.class);


		ListDialog listOfItems = new ListDialog(workbench.getShell()) {
        	@Override
        	protected Control createDialogArea(Composite container) {
        		Composite composite = (Composite) super.createDialogArea(container);
        		final Text textArea = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);

        		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        		gridData.heightHint = convertHeightInCharsToPixels(10);
        		textArea.setLayoutData(gridData);

        		getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
        			
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) event.getSelection();
						textArea.setText(!selection.isEmpty() ? selection.getFirstElement().toString() : "");
					}
        		});
				return composite;
        	}
        };
        listOfItems.setInput(items);
        listOfItems.setHelpAvailable(false);
        listOfItems.setTitle(Messages.ListDialog_Title);
        listOfItems.setLabelProvider(new ClipLabelProvider());
        listOfItems.setContentProvider(new ArrayContentProvider());

        int returnCode = listOfItems.open();
        if (returnCode == Window.OK) {
			Object[] results = listOfItems.getResult();
			if (results.length == 0) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			for (Object item : results) {
				sb.append(String.valueOf(item));
			}
			styledText.insert(sb.toString());
        }
		return null;
	}

	private class ClipLabelProvider extends LabelProvider {

		private static final int MAX_LENGTH = 65;
		private static final String PARAGRAPG_MARK = "\u00B6";

		@Override
	    public String getText(Object element) {
	        String text = element == null ? "" : element.toString();

	        int length = text.length();
	        String ending = length < MAX_LENGTH ? "" : "...";
	        text = text.replaceAll(Pattern.quote("\n"), PARAGRAPG_MARK).substring(0, Math.min(length, MAX_LENGTH));
            return text + ending;
	    }
	}
}