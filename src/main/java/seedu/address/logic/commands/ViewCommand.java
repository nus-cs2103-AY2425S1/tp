package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command to view the details of a person identified by their index in the displayed list.
 * This command allows users to see detailed information about a specific person in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewed Person: %1$s";

    private final Index targetIndex;

    /**
     * Creates a new ViewCommand to view the person at the specified {@code targetIndex}.
     *
     * @param targetIndex The index of the person to view in the filtered person list
     * @throws NullPointerException if {@code targetIndex} is null
     */
    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the view command to show the person at the specified index.
     *
     * @param model The model containing the person data
     * @return A CommandResult containing the viewed person's information
     * @throws CommandException if the index is invalid or out of bounds
     * @throws NullPointerException if {@code model} is null
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, personToView),
                false, false, true, personToView);
    }

    /**
     * Compares this ViewCommand to another object for equality.
     *
     * @param other The object to compare to
     * @return true if the other object is also a ViewCommand targeting the same index
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewCommand
                && targetIndex.equals(((ViewCommand) other).targetIndex));
    }
}
