package ar.com.mulesoft.nicecopyandpaste.ui.exceptions;

public class PluginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PluginException(String message, Exception e) {
		super(message, e);
	}
}
