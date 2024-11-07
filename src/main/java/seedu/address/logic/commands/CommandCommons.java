package seedu.address.logic.commands;

import java.util.Set;
import java.util.function.Supplier;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;

/**
 * Contains common values used in different command classes, including default command values.
 */
public final class CommandCommons {
    public static final String DEFAULT_TIER = "";
    public static final String DEFAULT_REMARK = "NA";
    public static final String DEFAULT_STATUS = "NA";
    public static final Client EMPTY_CLIENT = null;

    /**
     * A functional interface similar to {@link Supplier}, but allows for a checked exception, {@code ParseException}
     * to be thrown.
     * @param <T> The return type of the method supplied by this supplier.
     */
    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws ParseException;
    }

    /**
     * Used to parse a field in a command, and if there are problems with the field, add the resulting error message
     * to a {@link Set}, to be displayed to the user
     * @param <T> The attribute type to be parsed
     * @param parserMethod A lambda expression that contains a method, that when run can either return the parsed value
     *                     or throws a {@code ParseException}
     * @param errors A {@link Set} that contains the error messages
     * @return The parsed value or null
     */
    public static <T> T parseField(ThrowingSupplier<? extends T> parserMethod, Set<String> errors) {
        try {
            return parserMethod.get();
        } catch (ParseException e) {
            errors.add(e.getMessage());
            return null;
        }
    }

    public static String getErrorMessage(Set<String> errors) {
        StringBuilder accumulatedErrors = new StringBuilder();
        errors.forEach(error -> accumulatedErrors.append(error + "\n"));
        return accumulatedErrors.toString().trim();
    }

}
