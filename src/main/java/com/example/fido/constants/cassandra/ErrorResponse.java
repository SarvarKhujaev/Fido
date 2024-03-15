package com.example.fido.constants.cassandra;

@lombok.Data
@lombok.Builder
public final class ErrorResponse {
    private String message;
    private Errors errors;
}
