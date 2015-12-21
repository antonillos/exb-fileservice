package de.exb.interviews.sapozhko.resources;

import de.exb.interviews.sapozhko.auth.ApiClient;
import de.exb.interviews.sapozhko.core.File;
import de.exb.interviews.sapozhko.service.FileService;
import de.exb.interviews.sapozhko.session.Session;
import de.exb.interviews.sapozhko.session.SessionsStore;
import io.dropwizard.auth.Auth;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Path("/api/v1/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileServiceResource {

	@Inject
	private FileService fileService;

	@Inject
	private SessionsStore sessionsStore;

	@POST
	@Path("session")
	public Response session(@Auth final ApiClient apiClient) {
		Session session;
		if ((session = sessionsStore.get().get(apiClient.getId())) == null) {
			session = Session.newInstance().setIdentity(apiClient.getId());
			sessionsStore.get().put(session.getIdentity(), session);
		}
		return Response.ok(session).build();
	}

	@GET
	public Response exists(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId,
			@QueryParam("path") @NotNull final String aPath) throws IOException {
		checkSession(apiClient, aSessionId);

		boolean exists = fileService.exists(aSessionId, createPath(aPath));
		return (exists) ?
				Response.status(Response.Status.FOUND).entity(getFile(aSessionId, aPath)).build() :
				Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("size")
	public Response size(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId,
			@QueryParam("path") @NotNull final String aPath) throws IOException {
		checkSession(apiClient, aSessionId);

		boolean exists = fileService.exists(aSessionId, createPath(aPath));
		return (exists) ?
				Response.status(Response.Status.FOUND).entity(getFile(aSessionId, aPath)).build() :
				Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	public Response createNewFile(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId,
			@QueryParam("path") @NotNull final String aPath) throws IOException {
		checkSession(apiClient, aSessionId);

		fileService.createNewFile(aSessionId, createPath(aPath));
		return Response.status(Response.Status.CREATED).entity(getFile(aSessionId, aPath)).build();
	}

	@DELETE
	public Response delete(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId,
			@QueryParam("path") @NotNull final String aPath, @QueryParam("recursive") @DefaultValue("true") final Boolean recursive)
			throws IOException {
		checkSession(apiClient, aSessionId);

		fileService.delete(aSessionId, createPath(aPath), recursive);
		return Response.status(Response.Status.GONE).build();
	}

	private URL createPath(@QueryParam("path") @NotNull String aPath) throws IOException {
		return new URL("file://" + aPath);
	}

	private File getFile(@HeaderParam("session") @NotNull String aSessionId, @QueryParam("path") @NotNull String aPath) throws IOException {
		return File.newInstance().setPath(aPath).setName(fileService.getName(aSessionId, createPath(aPath)))
				.setSize(fileService.getSize(aSessionId, createPath(aPath)));
	}

	private void checkSession(@Auth ApiClient apiClient, @HeaderParam("session") @NotNull String aSessionId) {
		final Session session;
		if ((session = sessionsStore.get().get(apiClient.getId())) == null || !session.getSession().equals(aSessionId)) {
			throw new ForbiddenException();
		}
	}

}
