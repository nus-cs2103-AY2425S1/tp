package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS_CONTACT;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Adds a contact to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_FUNCTION = COMMAND_WORD
            + ": Adds a contact to the address book. ";
    public static final String MESSAGE_COMMAND_FORMAT = styleCommand(COMMAND_WORD + WHITESPACE// "Fields: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TELEGRAM_HANDLE + "TELEGRAM "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_STUDENT_STATUS + "STUDENT_STATUS "
            + PREFIX_ROLE + "ROLE [...] "
            + "[" + PREFIX_NICKNAME + "NICKNAME]");
    public static final String MESSAGE_COMMAND_EXAMPLE = "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TELEGRAM_HANDLE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_STUDENT_STATUS + "undergraduate 3 "
            + PREFIX_ROLE + "Admin "
            + PREFIX_ROLE + "President "
            + PREFIX_NICKNAME + "altName";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    private final Contact toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Contact}
     */
    public AddCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(String.format(Messages.MESSAGE_DUPLICATE_CONTACT, "Contact to add",
                    "the contact to add"));
        }

        if (model.hasDuplicateFields(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_FIELDS_CONTACT,
                    "If the details of the contact for adding is correct, please rectify the existing "
                            + "contacts details before adding the new contact."));
        }

        model.addContact(toAdd);
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
}
