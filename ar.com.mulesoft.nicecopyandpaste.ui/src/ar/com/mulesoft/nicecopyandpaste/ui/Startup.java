package ar.com.mulesoft.nicecopyandpaste.ui;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.commands.ICommandService;

import ar.com.mulesoft.nicecopyandpaste.ui.managers.ClipboardManager;

/**
 * This class initialize all the necessary configuration and 
 * adds a listener to monitoring the cut and copy actions.
 * 
 * @author ldebello
 * 
 */
public class Startup implements IStartup {
	
	private static final String CUT_ACTION = ActionFactory.CUT.getCommandId();
	private static final String COPY_ACTION = ActionFactory.COPY.getCommandId();
	

	public void earlyStartup() {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getAdapter(ICommandService.class);
				
		if (commandService != null) {
			commandService.addExecutionListener(new CommandListener());
		}
	}

	private static final class CommandListener implements IExecutionListener {

		@Override
		public void notHandled(String commandId, NotHandledException exception) {
		}

		@Override
		public void postExecuteFailure(String commandId, ExecutionException exception) {			
		}

		@Override
		public void postExecuteSuccess(String commandId, Object returnValue) {
			if (Activator.getDefault().isPluginMonitoringCutAndCopy()) {
				if (CUT_ACTION.equals(commandId) || COPY_ACTION.equals(commandId)) {
					Clipboard clipboard = new Clipboard(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay());
					
					String contents = (String) clipboard.getContents(TextTransfer.getInstance());
					
					if (contents != null) {
	                    ClipboardManager.getInstance().add(contents);
	                }
				}
			}			
		}

		@Override
		public void preExecute(String commandId, ExecutionEvent event) {	
		}		
	}
}