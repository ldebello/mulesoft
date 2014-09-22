package ar.com.mulesoft.nicecopyandpaste.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ar.com.mulesoft.nicecopyandpaste.ui.managers.ClipboardManager;

/**
 * Handler to clear the clipboard history.
 *
 * @author ldebello
 *
 */
public class ClearClipboardHistoryHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ClipboardManager.getInstance().clear();
		return null;
	}
}