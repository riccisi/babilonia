package it.riccisi.babilonia.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class NoUserConnectionFound extends RuntimeException {

    private final String errorCode = "USER_CONNECTION_NOT_FOUND";

    public NoUserConnectionFound(String username, String instanceId) {
        super("Connection not found, user: " + username + ", instance: " + instanceId);
    }

    public String getErrorCode() {
        return errorCode;
    }
}