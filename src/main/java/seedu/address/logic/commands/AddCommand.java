package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS_CONTACT;
import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_DESCRIPTION_INPUT;
import static seedu.address.logic.Messages.MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX_GENERAL;
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
            + ": Adds a contact to the address book.";
    public static final String MESSAGE_COMMAND_FORMAT = styleCommand(COMMAND_WORD + WHITESPACE
            + PREFIX_NAME + "FULL_NAME" + WHITESPACE
            + PREFIX_TELEGRAM_HANDLE + "TELEGRAM" + WHITESPACE
            + PREFIX_EMAIL + "EMAIL" + WHITESPACE
            + PREFIX_STUDENT_STATUS + "STUDENT_STATUS" + WHITESPACE
            + PREFIX_ROLE + "ROLE [...]" + WHITESPACE
            + "[" + PREFIX_NICKNAME + "NICKNAME]");
    public static final String MESSAGE_COMMAND_EXAMPLE = "Example: "
            + COMMAND_WORD + WHITESPACE
            + PREFIX_NAME + "John Doe" + WHITESPACE
            + PREFIX_TELEGRAM_HANDLE + "johnny_9876_haha" + WHITESPACE
            + PREFIX_EMAIL + "johnd@example.com" + WHITESPACE
            + PREFIX_STUDENT_STATUS + "undergraduate 3" + WHITESPACE
            + PREFIX_ROLE + "Admin" + WHITESPACE
            + PREFIX_ROLE + "President" + WHITESPACE
            + PREFIX_NICKNAME + "altName";
    public static final String MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT =
            COMMAND_FORMAT_PREAMBLE + WHITESPACE + AddCommand.MESSAGE_COMMAND_FORMAT + "\n"
                    + String.format(MESSAGE_HELP_PROMPT, HelpCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD);

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_MISSING_DESCRIPTION =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(MESSAGE_MISSING_DESCRIPTION_INPUT, "add")
                    + LINE_BREAK + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);
    public static final String MESSAGE_DUPLICATE_CONTACT =
            String.format(Messages.MESSAGE_DUPLICATE_CONTACT, "Contact to add", "the contact to add");
    public static final String MESSAGE_DUPLICATE_FIELD_CONTACT =
            String.format(MESSAGE_DUPLICATE_FIELDS_CONTACT,
                    "If the details of the contact for adding is correct, please rectify the existing "
                            + "contacts details before adding the new contact.");
    public static final String MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX_GENERAL,
                            styleCommand(COMMAND_WORD))
                            + LINE_BREAK + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);

    public static final String MESSAGE_MISSING_PREFIXES =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, "%s" + WHITESPACE + "mandatory prefix(es) is/are missing."
                    + LINE_BREAK + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);

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
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        if (model.hasDuplicateFields(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FIELD_CONTACT);
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
