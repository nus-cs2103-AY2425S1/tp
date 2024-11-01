package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
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
 * moreinfo n/client_name
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Open a window to more information "
            + "about the client.\n"
            + "Example: " + COMMAND_WORD + " n/Amy";

    public static final String SHOWING_MORE_INFO_MESSAGE = "Opened window for client's information.";

    private final Name targetName;

    public MoreInfoCommand(Name targetName) {
        this.targetName = targetName;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personMoreInfo = model.getPersonByName(targetName);

        if (!lastShownList.contains(personMoreInfo)) {
            String closestMatch = findClosestMatch(targetName.toString(), lastShownList);

            if (closestMatch != null) {
                throw new CommandException(String.format(Messages.MESSAGE_SUGGESTION, closestMatch));
            } else {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
            }
        }

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

        return targetName.equals(otherMoreInfoCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
