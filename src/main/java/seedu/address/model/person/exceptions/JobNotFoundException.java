package seedu.address.model.person.exceptions;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException(String message) {
    super(message);
  }
}
