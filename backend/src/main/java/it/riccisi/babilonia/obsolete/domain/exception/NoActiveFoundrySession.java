package it.riccisi.babilonia.obsolete.domain.exception;

public class NoActiveFoundrySession extends RuntimeException {

    public NoActiveFoundrySession(String instanceId) {
        super(String.format("No active foundry session found for instance ID %s", instanceId));
    }
}