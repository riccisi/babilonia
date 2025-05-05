package it.riccisi.babilonia.api.error;

public record ErrorResponse(
    String errorCode,
    String message,
    String traceId
) {}
