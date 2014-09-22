package ar.com.mulesoft.nicecopyandpaste.ui.gists;

/**
 * A simple adapter to be implemented by the class 
 * in charge of exchanging data with the GistFacade.
 * 
 * @author ldebello
 */
public interface GitHubAdapter {
	public String getLogin();
	
	public String getPassword();
	
	public void handleException(Exception exception);
}
