package ar.com.mulesoft.nicecopyandpaste.ui.rest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import ar.com.mulesoft.nicecopyandpaste.ui.exceptions.PluginException;
import ar.com.mulesoft.nicecopyandpaste.ui.rest.providers.GsonProvider;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GistsFacade {

	private static final String GISTS_API = "https://api.github.com";
	private WebResource resource;

	private GistsFacade() {
		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(GsonProvider.class);
		Client client = Client.create(config);

		resource = client.resource(getBaseURI());
	}

	public static GistsFacade getInstance() {
		return GistsFacadeHolder.INSTANCE;
	}

	private static class GistsFacadeHolder {
		private static final GistsFacade INSTANCE = new GistsFacade();
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(GISTS_API).build();
	}

	public static void main(String[] args) {
		
		System.out.println("Usuario valido");
		GistsFacade.getInstance().getGists("fmedlin");

		System.out.println("Busqueda por ID");
		GistsFacade.getInstance().getGistById("11190166");
		
		System.out.println("Usuario Vacio");
		GistsFacade.getInstance().getGists("ldebello");

		System.out.println("Usuario por ID Invalido");
		GistsFacade.getInstance().getGistById("9787512132");
		
		System.out.println("Usuario desconocido");
		GistsFacade.getInstance().getGists("lmnhthsd");
	}

	public void getGists(String user) {
		try {
			ClientResponse response = resource.path("users").path(user)
					.path("gists").accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			GenericType<List<JsonObject>> elements = new GenericType<List<JsonObject>>() {
			};

			if (Status.OK.getStatusCode() == response.getStatus()) {
				List<JsonObject> vinos = response.getEntity(elements);

				for (JsonObject jsonObject : vinos) {
					System.out.println(jsonObject);
				}
			}
			
		} catch (Exception e) {
			throw new PluginException(e.getMessage(), e);
		}
	}

	public void getGistById(String id) {
		try {
			ClientResponse response = resource.path("gists").path(id)
					.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			JsonObject jsonObject = response.getEntity(JsonObject.class);

			System.out.println(jsonObject);
		} catch (Exception e) {
			throw new PluginException(e.getMessage(), e);
		}
	}
}