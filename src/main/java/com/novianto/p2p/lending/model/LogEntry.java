package com.novianto.p2p.lending.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry implements Serializable {

    @Id
    private String id;

    @NotNull
    private LocalDateTime timestamp;

    @NotBlank
    private String level;

    @NotBlank
    private String logger;

    @NotBlank
    private String message;

    @NotBlank
    private String thread;

    private String exception;

    public static class Builder {
        private String id;
        private LocalDateTime timestamp;
        private String level;
        private String logger;
        private String message;
        private String thread;
        private String exception;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder logger(String logger) {
            this.logger = logger;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder thread(String thread) {
            this.thread = thread;
            return this;
        }

        public Builder exception(String exception) {
            this.exception = exception;
            return this;
        }

        public LogEntry build() {
            LogEntry logEntry = new LogEntry();
            logEntry.id = this.id;
            logEntry.timestamp = this.timestamp;
            logEntry.level = this.level;
            logEntry.logger = this.logger;
            logEntry.message = this.message;
            logEntry.thread = this.thread;
            logEntry.exception = this.exception;
            return logEntry;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
