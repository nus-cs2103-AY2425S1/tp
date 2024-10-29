package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.ConfirmationHandler;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.DuplicateWarningWindow;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BIRTHDAY + "BIRTHDAY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_BIRTHDAY + "11 09 2001 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "Person by the name of:\n %s\n already exists in the address book. Are you sure you want to proceed?";

    private final Person toAdd;

    private ConfirmationHandler confirmationHandler;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
        this.confirmationHandler = new DefaultConfirmationHandler();
    }

    /**
     * Constructor for a {@code AddCommand} object for unit tests
     * @param person
     * @param confirmationHandler
     */
    public AddCommand(Person person, ConfirmationHandler confirmationHandler) {
        requireNonNull(person);
        toAdd = person;
        this.confirmationHandler = confirmationHandler;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasPerson(toAdd)) {
            // note to self: currently duplicate person is case sensitive
            // i.e. Alex Yeoh is different from alex yeoh
            boolean isConfirmed = confirmationHandler.confirm(toAdd);
            if (!isConfirmed) {
                throw new CommandException(Messages.MESSAGE_USER_CANCEL);
            }
        }
        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }

    /**
     * Nested class for testing purposes
     */
    public static class DefaultConfirmationHandler implements ConfirmationHandler {
        /**
         * Bypasses UI popup for testing purposes
         * @param person The duplicated person to be added
         * @return Whether the addition proceeds or not
         */
        public boolean confirm(Person person) {
            DuplicateWarningWindow duplicateWarningWindow = new DuplicateWarningWindow();
            duplicateWarningWindow.show(person);
            return duplicateWarningWindow.isConfirmed();
        }
    }
}
