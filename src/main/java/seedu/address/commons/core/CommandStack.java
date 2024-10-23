package seedu.address.commons.core;

import static seedu.address.commons.util.CollectionUtil.isCollectionEqual;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains command history data and command history max size.
 */
public class CommandStack implements Serializable {
    private static final int DEFAULT_COMMAND_ARRAY_LIST_MAX_SIZE = 2000;
    /**
     * 0-th index of {@code commandArrayList} is treated as the earliest recorded executed command.
     * This is a consequence of {@code addCommand} adding new command strings to the back of {@code commandArrayList}.
     */
    private final ArrayList<String> commandArrayList;
    private final int commandArrayListMaxSize;
    /**
     * Represents the index of the currently displayed element.
     * If the value exceeds the index of the final index of commandArrayList, {@code CommandGetterResult}
     * functions will clamp the value between {@code 0} and {@code commandArrayList.size()} inclusive.
     */
    @JsonIgnore
    private int commandArrayIndex; //

    /**
     * Constructs a {@code CommandStack} with default max size, and empty history.
     */
    public CommandStack() {
        this.commandArrayList = new ArrayList<>();
        this.commandArrayListMaxSize = DEFAULT_COMMAND_ARRAY_LIST_MAX_SIZE;
        this.commandArrayIndex = DEFAULT_COMMAND_ARRAY_LIST_MAX_SIZE;
    }

    /**
     * Creates a {@code CommandStack} with specified max size, and specified history.
     */
    public CommandStack(ArrayList<String> commandArrayList, int commandArrayListMaxSize) {
        this.commandArrayList = commandArrayList;
        this.commandArrayListMaxSize = commandArrayListMaxSize;
        this.commandArrayIndex = commandArrayListMaxSize;
    }
    public ArrayList<String> getCommandArrayList() {
        return commandArrayList;
    }
    public int getCommandArrayListMaxSize() {
        return commandArrayListMaxSize;
    }

    private void forceArrayIndexWithinBounds() {
        commandArrayIndex = Math.min(commandArrayList.size(), Math.max(0, commandArrayIndex));
    }

    public CommandGetterResult getEarlierCommandGetterResult(CommandGetterResult commandGetterResult) {
        if (commandGetterResult.getIsModifiedSinceLastArrowKey()) {
            commandArrayIndex = commandArrayListMaxSize; // set to latest
        }
        forceArrayIndexWithinBounds();
        if (commandArrayList.isEmpty()) {
            return commandGetterResult
                    .updateStringToDisplay("")
                    .updateIsModified(false);
        }
        if (commandArrayIndex > 0) {
            commandArrayIndex--;
        }
        return commandGetterResult
                .updateStringToDisplay(commandArrayList.get(commandArrayIndex))
                .updateIsModified(false);
    }
    public CommandGetterResult getLaterCommandGetterResult(CommandGetterResult commandGetterResult) {
        if (commandGetterResult.getIsModifiedSinceLastArrowKey()) {
            return commandGetterResult;
        }
        forceArrayIndexWithinBounds();
        if (commandArrayList.isEmpty()) {
            return commandGetterResult
                    .updateStringToDisplay("")
                    .updateIsModified(false);
        }
        if (commandArrayIndex < commandArrayList.size()) {
            commandArrayIndex++;
        }
        if (commandArrayIndex >= commandArrayList.size()) {
            return commandGetterResult
                    .updateStringToDisplay("")
                    .updateIsModified(false);
        }
        return commandGetterResult
                .updateStringToDisplay(commandArrayList.get(commandArrayIndex))
                .updateIsModified(false);
    }
    /**
     * Adds an executed command string to the front of {@code commandDeque}.
     * If size of the command stack exceeds {@code commandStackMaxSize},
     * removes the earliest added command string in history.
     */
    public CommandStack addCommand(String commandString) {
        assert(commandArrayList.size() <= commandArrayListMaxSize);
        commandArrayList.add(commandString);
        if (commandArrayList.size() == commandArrayListMaxSize + 1) {
            commandArrayList.remove(0);
        }
        return new CommandStack(this.commandArrayList, this.commandArrayListMaxSize);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CommandStack)) {
            return false;
        }
        CommandStack otherCommandStack = (CommandStack) other;
        return isCollectionEqual(commandArrayList, otherCommandStack.commandArrayList)
                && commandArrayListMaxSize == otherCommandStack.commandArrayListMaxSize
                && commandArrayIndex == otherCommandStack.commandArrayIndex;
    }
    @Override
    public int hashCode() {
        return Objects.hash(commandArrayList, commandArrayListMaxSize, commandArrayIndex);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandArrayList", commandArrayList)
                .add("commandArrayListMaxSize", commandArrayListMaxSize)
                .add("commandArrayIndex", commandArrayIndex)
                .toString();
    }

}
