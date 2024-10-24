package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows detailed information about a person in the address book.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SHOW_PERSON_SUCCESS = "Displaying details for %s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays detailed information about the person identified"
            + " by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String HELP_SHOW_COMMAND = "Show Command\n"
            + "- Format: show INDEX\n"
            + "- Example: show 1\n"
            + "- Displays detailed information about the person at the specified INDEX in the list.";

    private final Index targetIndex;

    /**
     * Creates a ShowCommand to display the person at the specified {@code Index} in the PersonPane.
     *
     * @param targetIndex The index of the person to show.
     */
    public ShowCommand(Index targetIndex) {
        assert targetIndex != null : "Target index should not be null";
        this.targetIndex = targetIndex;
    }

    //@@author tayxuenye-reused
    //Written by ChatGPT
    /**
     * Executes the ShowCommand and returns the result.
     * Finds the person at the {@code targetIndex} in the filtered person list and displays their detailed information.
     *
     * @param model The {@code Model} containing the list of persons.
     * @return A CommandResult that displays the details of the person.
     * @throws CommandException If the {@code targetIndex} is out of bounds of the person list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Model should not be null";

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShow = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SHOW_PERSON_SUCCESS,
                personToShow.getName().fullName), personToShow);
    }
    //@@author
}

