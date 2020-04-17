package cat.aubricoc.xolis.server.model;

import cat.aubricoc.xolis.core.utils.Callback;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class HttpError {

    private String field;
    private String message;
    private Callback<Void> retry;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Callback<Void> getRetry() {
        return retry;
    }

    public void setRetry(Callback<Void> retry) {
        this.retry = retry;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
