package bizbook.logic.commands.exporter.exceptions;

/**
 * Represents an error that is caused because something is wrong with the loaded file.
 */
public class InvalidFileException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidFileException} with the specified detail {@code message}.
     */
    public InvalidFileException(String message) {
        super(message);
    }
}
