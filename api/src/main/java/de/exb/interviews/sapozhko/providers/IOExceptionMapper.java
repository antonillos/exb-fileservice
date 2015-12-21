package de.exb.interviews.sapozhko.providers;

import de.exb.interviews.sapozhko.views.ErrorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class IOExceptionMapper implements ExceptionMapper<IOException> {

    final static Logger LOGGER = LoggerFactory.getLogger(IOExceptionMapper.class);

    @Override
    public Response toResponse(IOException exception) {

        Response response500 = Response
                .serverError()
                .entity(new ErrorView(new Exception(exception.getMessage()), "500.ftl"))
                .build();

        LOGGER.error("Uncaught IOException in application: {}", exception.getMessage());

        return response500;
    }
}