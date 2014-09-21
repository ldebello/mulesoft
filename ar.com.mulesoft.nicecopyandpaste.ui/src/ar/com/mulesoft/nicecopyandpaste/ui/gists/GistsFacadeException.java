package ar.com.mulesoft.nicecopyandpaste.ui.gists;

public class GistsFacadeException extends RuntimeException {

	private static final long serialVersionUID = 1302692482993289968L;

	public GistsFacadeException(String message, Exception e) {
		super(message, e);
	}
}
