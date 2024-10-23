package seedu.address.commons.core;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Stores the result of getting command history, and the current unfinished string.
 * Behaviour for command history is based on Microsoft Powershell.
 * Guarantees: immutable.
 */
public class CommandGetterResult {
    private final String stringToDisplay;
    private final String originalTypedString;

    /**
     * Constructs a CommandGetterResult with provided {@code stringToDisplay} and {@code originalTypedString}.
     * @param stringToDisplay
     * @param originalTypedString
     */
    public CommandGetterResult(String stringToDisplay, String originalTypedString) {
        this.stringToDisplay = stringToDisplay;
        this.originalTypedString = originalTypedString;
    }
    public CommandGetterResult updateStringToDisplay(String newStringToDisplay) {
        return new CommandGetterResult(newStringToDisplay, originalTypedString);
    }
    public CommandGetterResult updateOriginalTypedString(String newOriginalTypedString) {
        return new CommandGetterResult(stringToDisplay, newOriginalTypedString);
    }

    public String getStringToDisplay() {
        return stringToDisplay;
    }
    public String getOriginalTypedString() {
        return originalTypedString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CommandGetterResult)) {
            return false;
        }
        CommandGetterResult otherResult = (CommandGetterResult) other;
        return Objects.equals(stringToDisplay, otherResult.stringToDisplay)
                && Objects.equals(originalTypedString, otherResult.originalTypedString);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("stringToDisplay", stringToDisplay)
                .add("originalTypedString", originalTypedString)
                .toString();
    }
}
