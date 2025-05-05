package it.riccisi.babilonia.domain.exception;

public class UserNotFoundException extends RuntimeException {

  private final String errorCode = "USER_NOT_FOUND";

  public UserNotFoundException(String userId) {
    super("User not found: " + userId);
  }

  public String getErrorCode() {
    return errorCode;
  }
}