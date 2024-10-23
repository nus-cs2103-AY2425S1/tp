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
    private final boolean isModifiedSinceLastArrowKey;

    /**
     * Constructs a CommandGetterResult with provided {@code stringToDisplay} and {@code isModifiedHistoricalCommand}.
     */
    public CommandGetterResult(String stringToDisplay, boolean isModifiedSinceLastArrowKey) {
        this.stringToDisplay = stringToDisplay;
        this.isModifiedSinceLastArrowKey = isModifiedSinceLastArrowKey;
    }
    public CommandGetterResult updateStringToDisplay(String newStringToDisplay) {
        return new CommandGetterResult(newStringToDisplay, isModifiedSinceLastArrowKey);
    }

    public CommandGetterResult updateIsModified(boolean isModifiedSinceLastArrowKey) {
        return new CommandGetterResult(stringToDisplay, isModifiedSinceLastArrowKey);
    }

    public String getStringToDisplay() {
        return stringToDisplay;
    }
    public boolean getIsModifiedSinceLastArrowKey() {
        return isModifiedSinceLastArrowKey;
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
                && isModifiedSinceLastArrowKey == otherResult.isModifiedSinceLastArrowKey;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("stringToDisplay", stringToDisplay)
                .add("isModifiedSinceLastArrowKey", isModifiedSinceLastArrowKey)
                .toString();
    }
}
