package it.riccisi.babilonia.domain.exception;

public class NoProjectFoundException extends RuntimeException {

  private final String errorCode = "PROJECT_NOT_FOUND";

  public NoProjectFoundException(String projectId) {
    super("Project not found: " + projectId);
  }

  public String getErrorCode() {
    return errorCode;
  }
}