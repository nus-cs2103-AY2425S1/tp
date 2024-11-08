package seedu.address.logic.commands.clientcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.MoreInfoWindow;

/**
 * Represents a command that opens a window to display more information
 * about a specific client in the application.
 *
 * This command is triggered using the command word {@code moreinfo}
 * followed by the client's name. For example, {@code moreinfo n/Amy}.
 *
 * Command Format:
 * moreinfo n/client_index
 *
 * Attributes
 *     {@code COMMAND_WORD} - The command word to trigger this command.
 *     {@code MESSAGE_USAGE}  - The usage message that describes how to use the command.
 *     {@code SHOWING_MORE_INFO_MESSAGE} - The message shown to the user when the command is executed.
 * Example
 * moreinfo n/Amy
 *
 * @see Command
 */
public class MoreInfoCommand extends Command {
    public static final String COMMAND_WORD = "moreinfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "CLIENT_INDEX" + ": Open a window to more information "
            + "about the client.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SHOWING_MORE_INFO_MESSAGE = "Opened window for client's information.";

    private final Index targetIndex;

    public MoreInfoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int zeroBasedPerson = targetIndex.getZeroBased();
        if (zeroBasedPerson >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personMoreInfo = lastShownList.get(zeroBasedPerson);

        MoreInfoWindow moreInfoWindow = new MoreInfoWindow(personMoreInfo);
        moreInfoWindow.show();
        return new CommandResult(SHOWING_MORE_INFO_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MoreInfoCommand otherMoreInfoCommand)) {
            return false;
        }

        return targetIndex.equals(otherMoreInfoCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
