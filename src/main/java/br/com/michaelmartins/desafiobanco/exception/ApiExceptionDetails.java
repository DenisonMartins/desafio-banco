package br.com.michaelmartins.desafiobanco.exception;

import java.time.Instant;

public class ApiExceptionDetails {

    private final Instant timestamp = Instant.now();
    private Integer status;
    private String title;
    private String message;

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public static final class ApiExceptionDetailsBuilder {
        private Integer status;
        private String title;
        private String description;

        private ApiExceptionDetailsBuilder() {
        }

        public static ApiExceptionDetailsBuilder newBuilder() {
            return new ApiExceptionDetailsBuilder();
        }

        public ApiExceptionDetailsBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ApiExceptionDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ApiExceptionDetailsBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ApiExceptionDetails build() {
            ApiExceptionDetails apiExceptionDetails = new ApiExceptionDetails();
            apiExceptionDetails.status = this.status;
            apiExceptionDetails.title = this.title;
            apiExceptionDetails.message = this.description;
            return apiExceptionDetails;
        }
    }
}
