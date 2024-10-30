package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.MoreInfoWindow;

/**
 * Represents a command that opens a window to display more information
 * about a specific client in the application.
 * <p>
 * This command is triggered using the command word {@code moreinfo}
 * followed by the client's name. For example, {@code moreinfo n/Amy}.
 * </p>
 *
 * <h2>Command Format</h2>
 * <pre>
 * moreinfo n/&lt;client_name&gt;
 * </pre>
 *
 * <h2>Attributes</h2>
 * <ul>
 *     <li>{@code COMMAND_WORD} - The command word to trigger this command.</li>
 *     <li>{@code MESSAGE_USAGE} - The usage message that describes how to use the command.</li>
 *     <li>{@code SHOWING_MORE_INFO_MESSAGE} - The message shown to the user when the command is executed.</li>
 * </ul>
 *
 * <h2>Example</h2>
 * <pre>
 * moreinfo n/Amy
 * </pre>
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
}
