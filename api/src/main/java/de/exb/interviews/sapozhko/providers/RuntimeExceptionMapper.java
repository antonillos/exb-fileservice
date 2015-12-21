package de.exb.interviews.sapozhko.providers;

import de.exb.interviews.sapozhko.views.ErrorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    final static Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {

        Response response500 = Response
                .serverError()
                .entity(new ErrorView(exception, "500.ftl"))
                .build();

        // Assuming that any WebApplicationException thrown in application are deliberate and already logged
        if (exception instanceof WebApplicationException) {
            return handleWebApplicationException(exception, response500);
        }

        LOGGER.error("Uncaught exception in application", exception);

        return response500;
    }

    private Response handleWebApplicationException(RuntimeException exception, Response response) {

        WebApplicationException webAppException = (WebApplicationException) exception;
        int statusCode = webAppException.getResponse().getStatus();

        switch(statusCode) {
            case 401: return Response.status(statusCode).entity(new ErrorView(new Exception(exception.getMessage()), "401.ftl")).build();
            case 403: return Response.status(statusCode).entity(new ErrorView(new Exception(exception.getMessage()), "403.ftl")).build();
            case 404: return Response.status(statusCode).entity(new ErrorView(new Exception(exception.getMessage()), "404.ftl")).build();
        }

        LOGGER.info("No template for WebApplicationException status " + statusCode);

        return response;
    }
}