package de.exb.interviews.sapozhko.resources;

import de.exb.interviews.sapozhko.auth.ApiClient;
import de.exb.interviews.sapozhko.core.File;
import de.exb.interviews.sapozhko.service.FileServiceException;
import de.exb.interviews.sapozhko.session.Session;
import de.exb.interviews.sapozhko.service.FileService;
import de.exb.interviews.sapozhko.session.SessionsStore;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.BooleanParam;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
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
            session = new Session(apiClient.getId());
            sessionsStore.get().put(apiClient.getId(), session);
        }
        return Response.ok(session).build();
    }

    @GET
    public Response exists(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId, @QueryParam("path") @NotNull final String aPath) throws IOException {
        checkSession(apiClient, aSessionId);

        boolean exists = fileService.exists(aSessionId, createPath(aPath));
        return (exists) ? Response.status(Response.Status.FOUND).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("size")
    public Response size(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId, @QueryParam("path") @NotNull final String aPath) throws IOException {
        checkSession(apiClient, aSessionId);

        if (fileService.exists(aSessionId, createPath(aPath))) {
            File file = new File(aPath);
            file.setSize(fileService.getSize(aSessionId, createPath(aPath)));
            Response.status(Response.Status.FOUND).entity(file).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createNewFile(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId, @QueryParam("path") @NotNull final String aPath) throws IOException {
        checkSession(apiClient, aSessionId);

        fileService.createNewFile(aSessionId, createPath(aPath));
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    public Response delete(@Auth final ApiClient apiClient, @HeaderParam("session") @NotNull final String aSessionId, @QueryParam("path") @NotNull final String aPath, @QueryParam("recursive") @DefaultValue("true") final Boolean recursive) throws IOException {
        checkSession(apiClient, aSessionId);

        fileService.delete(aSessionId, createPath(aPath), recursive);
        return Response.status(Response.Status.OK).build();
    }

    private URL createPath(@QueryParam("path") @NotNull String aPath) throws IOException {
        return new URL("file://" + aPath);
    }

    private void checkSession(@Auth ApiClient apiClient, @HeaderParam("session") @NotNull String aSessionId) {
        final Session session;
        if ((session = sessionsStore.get().get(apiClient.getId())) == null || !session.getSession().equals(aSessionId)) {
            throw new ForbiddenException();
        }
    }

}
