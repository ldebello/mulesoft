package ar.com.mulesoft.nicecopyandpaste.ui.gists;

public interface GitHubAdapter {
	public String getLogin();
	
	public String getPassword();
	
	public void handleException(Exception exception);
}
