package ar.com.mulesoft.nicecopyandpaste.ui.gists;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.OAuthService;


/**
 * This class is used as a single entry point to connect with GistService 
 * using a GitHubAdapter to decouple the view layer.
 * 
 * @author ldebello
 */
public final class GistsFacade {

	private static final String GIST_AUTHORIZATION_NOTE = "NiceCopyPaste";

	private OAuthService oauthService;

	private Authorization authorization;

	private GistsFacade() {
	}

	public static GistsFacade getInstance() {
		return GistsFacadeHolder.INSTANCE;
	}

	private static class GistsFacadeHolder {
		private static final GistsFacade INSTANCE = new GistsFacade();
	}

	public List<Gist> getGists(GitHubAdapter adapter) {
		try {
			initializeAuthorization(adapter);
			GistService gistService = new GistService();
			gistService.getClient().setOAuth2Token(authorization.getToken());
			
			return gistService.getGists(adapter.getLogin());
		} catch (Exception e) {
			adapter.handleException(new GistsFacadeException("Error getting gists - Please check Username and Password", e));
		}
		return Collections.emptyList();
	}
	
	public void deleteAuthorizations(GitHubAdapter adapter) throws Exception {
		try {
			initializeAuthorization(adapter);
			List<Authorization> authorizations = oauthService.getAuthorizations();
			for (Authorization authorization : authorizations) {
				String note = authorization.getNote();
				if (note != null && note.startsWith(GIST_AUTHORIZATION_NOTE)) {
					oauthService.deleteAuthorization(authorization.getId());
				}
			}
		} catch (Exception e) {
			adapter.handleException(new GistsFacadeException("Error deleting previous authorizations - Please check Username and Password", e));
		}		
	}
	
	public void publishGist(GitHubAdapter adapter, String gistDescription, Map<String, String> data) {
		try {
			initializeAuthorization(adapter);
			
			GistService gistService = new GistService();
			gistService.getClient().setOAuth2Token(authorization.getToken());
			
			Gist gist = new Gist();
			gist.setPublic(true);
			gist.setDescription(gistDescription);
			
			Map<String, GistFile> gistFiles = new HashMap<String, GistFile>();
			
			for (Entry<String, String> entry : data.entrySet()) {
				GistFile gistFile = new GistFile();
				gistFile.setFilename(entry.getKey());
				gistFile.setContent(entry.getValue());				
				gistFiles.put(gistFile.getFilename(), gistFile);
			}
			
			gist.setFiles(gistFiles);
			
			gist = gistService.createGist(gist);			
		} catch (Exception e) {
			adapter.handleException(new GistsFacadeException("Error creating a new gist - Please check Username and Password", e));
		}
	}
	
	private void initializeAuthorization(GitHubAdapter adapter) throws IOException {
		oauthService = getOAuthService(adapter);		
		authorization = getAuthorization(oauthService);
	}
	
	private OAuthService getOAuthService(GitHubAdapter adapter) {
		if (oauthService == null) {
			oauthService = new OAuthService();			
		}
		oauthService.getClient().setCredentials(adapter.getLogin(), adapter.getPassword());
		return oauthService;
	}

	private Authorization getAuthorization(OAuthService oauthService) throws IOException {		
		if (authorization == null) {
			authorization = new Authorization();
			authorization.setScopes(Arrays.asList("gist"));
			authorization.setNote(GIST_AUTHORIZATION_NOTE + "#" + new Date());
			authorization = oauthService.createAuthorization(authorization);
		}
		return authorization;
	}	
}