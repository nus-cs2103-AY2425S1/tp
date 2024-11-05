package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * View details of a contact.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views details of a contact.\n"
                    + "Parameters: "
                    + PREFIX_NAME
                    + "NAME\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " "
                    + PREFIX_NAME + "JohnDoe";

    public static final String VIEW_ACKNOWLEDGMENT = "Viewing contact ";
    public static final String CLOSE_VIEW_ACKNOWLEDGMENT = "Closing view of contact ";
    private static final ViewCommand closeView = new ViewCommand();
    private static final CommandResult closeViewResult = new CommandResult(CLOSE_VIEW_ACKNOWLEDGMENT, null);

    private final Name personName;
    private final boolean isClose;

    /**
     * @param name Name of the contact.
     */
    public ViewCommand(Name name) {
        this.personName = name;
        isClose = false;
    }

    private ViewCommand() {
        personName = null;
        this.isClose = true;
    }

    public static ViewCommand closeView() {
        return closeView;
    }

    public static CommandResult getCloseViewResult() {
        return closeViewResult;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (isClose) {
            return closeViewResult;
        }

        requireNonNull(personName);
        if (!model.hasName(personName)) {
            throw new CommandException("Person " + personName + " not in address book");
        }

        Person person =
                model.getAddressBook().getPersonList().stream()
                        .filter(p -> p.getName().equalIgnoreCase(personName))
                        .findFirst().orElseThrow();

        return new CommandResult(VIEW_ACKNOWLEDGMENT, person);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ViewCommand other) {
            if (personName == null || other.personName == null) {
                return isClose == other.isClose && other.personName == personName;
            } else {
                return isClose == other.isClose && other.personName.equals(personName);
            }
        }
        return false;
    }

}
