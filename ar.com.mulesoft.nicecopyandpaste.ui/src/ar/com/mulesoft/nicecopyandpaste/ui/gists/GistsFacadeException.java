package ar.com.mulesoft.nicecopyandpaste.ui.gists;

/**
 * Custom exception to wrap all the exceptions in the GistFacade.
 * 
 * @see GistsFacade
 * 
 * @author ldebello
 */
public class GistsFacadeException extends RuntimeException {

	private static final long serialVersionUID = 1302692482993289968L;

	public GistsFacadeException(String message, Exception e) {
		super(message, e);
	}
}
