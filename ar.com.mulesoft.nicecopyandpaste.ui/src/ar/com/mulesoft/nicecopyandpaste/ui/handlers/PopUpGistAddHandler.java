package ar.com.mulesoft.nicecopyandpaste.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import ar.com.mulesoft.nicecopyandpaste.ui.wizards.AddGistWizard;

/**
 * Handler to publish the selected code in a new gist. <p>
 * It shows a wizard in order to customize the selected text and the description
 * for this gist.
 *
 * @author ldebello
 */
public class PopUpGistAddHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorPart editorInput = page.getActiveEditor();
		
		
		ITextEditor editor = (ITextEditor) editorInput;
		ISelection currentSelection = editor.getSelectionProvider().getSelection();		
		TextSelection selectedText = (TextSelection) currentSelection;
				
		Wizard wizard = new AddGistWizard(editorInput.getTitle(), selectedText.getText());
		Shell shell = workbenchWindow.getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();

		return null;
	}
}