package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * View details of a contact.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String COMMAND_WORD_SHORT_FORM = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM
            + ": Views details of a contact.\n"
            + "Parameters: "
            + PREFIX_NAME
            + "NAME\n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + PREFIX_NAME + "John Doe\n"
            + "Example: "
            + COMMAND_WORD_SHORT_FORM
            + " "
            + PREFIX_NAME + "John Doe";

    public static final String VIEW_ACKNOWLEDGMENT = "Viewing contact";
    public static final String CLOSE_VIEW_ACKNOWLEDGMENT = "Closing view of contact";
    private static final ViewCommand closeView = new ViewCommand();
    private static final CommandResult closeViewResult = new CommandResult(CLOSE_VIEW_ACKNOWLEDGMENT, null, true);

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

        return createCommandResult(model);
    }

    private CommandResult createCommandResult(Model model) {
        ObjectProperty<Person> person = new SimpleObjectProperty<>(model.getPerson(personName).orElseThrow());
        ListChangeListener<Person> listener = change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    person.set(null);
                    model.getPerson(personName).ifPresentOrElse(
                            person::set, () -> {});

                }
            }
        };
        model.getAddressBook().getPersonList().addListener(listener);
        return new CommandResult(VIEW_ACKNOWLEDGMENT, person, false);
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
