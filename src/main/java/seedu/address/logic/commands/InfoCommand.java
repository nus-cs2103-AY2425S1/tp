package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.DetailedPersonCardWindow;

/**
 * Opens a new window displaying detailed information of a selected person.
 */
public class InfoCommand extends Command {
    public static final String COMMAND_WORD = "info";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays detailed information of a person.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_INFO_PERSON_SUCCESS = "Displaying info of Person: ";
    private final Index targetIndex;
    public InfoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToShowInfo = lastShownList.get(targetIndex.getZeroBased());
        DetailedPersonCardWindow.setPerson(personToShowInfo);
        return new CommandResult(String.format(MESSAGE_INFO_PERSON_SUCCESS + personToShowInfo.getName()), false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoCommand)) {
            return false;
        }

        InfoCommand otherInfoCommand = (InfoCommand) other;
        return targetIndex.equals(otherInfoCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
