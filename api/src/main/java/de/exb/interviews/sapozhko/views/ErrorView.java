package de.exb.interviews.sapozhko.views;

import io.dropwizard.views.View;

public class ErrorView extends View {
    private final Exception exception;

    public ErrorView(Exception e, String template) {
        super(template);
        this.exception = e;

    }

    public Exception getException() {
        return exception;
    }

}
