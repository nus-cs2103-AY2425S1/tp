package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Adds a contact to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to the address book. "
            + "Parameters: -CONTACT_FLAG (either `-v` or `-c`)"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SERVICE + "SERVICE (only if adding vendor) "
            + PREFIX_DATE + "WEDDING_DATE (only if adding client) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Vendor example: " + COMMAND_WORD + " -v "
            + PREFIX_NAME + "ABC Catering "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "contact@abccatering.com "
            + PREFIX_ADDRESS + "Blk 123 Bukit Merah St 7 "
            + PREFIX_SERVICE + "catering "
            + PREFIX_TAG + "vegetarian "
            + PREFIX_TAG + "budget\n"
            + "Client example: " + COMMAND_WORD + " -c "
            + PREFIX_NAME + "Jane Doe "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "jane.doe@example.com "
            + PREFIX_ADDRESS + "Blk 231 Sembawang St 4 "
            + PREFIX_DATE + "2024-12-15 "
            + PREFIX_TAG + "budget "
            + PREFIX_TAG + "pets";

    public static final String VENDOR_MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to the address book. "
            + "Parameters: -CONTACT_FLAG (either `-v` or `-c`)"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SERVICE + "SERVICE (only if adding vendor) "
            + PREFIX_DATE + "WEDDING_DATE (only if adding client) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " -v "
            + PREFIX_NAME + "ABC Catering "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "contact@abccatering.com "
            + PREFIX_ADDRESS + "Blk 123 Bukit Merah St 7 "
            + PREFIX_SERVICE + "catering "
            + PREFIX_TAG + "vegetarian "
            + PREFIX_TAG + "budget";

    public static final String CLIENT_MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to the address book. "
            + "Parameters: -CONTACT_FLAG (either `-v` or `-c`)"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SERVICE + "SERVICE (only if adding vendor) "
            + PREFIX_DATE + "WEDDING_DATE (only if adding client) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " -c "
            + PREFIX_NAME + "Jane Doe "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "jane.doe@example.com "
            + PREFIX_ADDRESS + "Blk 231 Sembawang St 4 "
            + PREFIX_DATE + "2024-12-15 "
            + PREFIX_TAG + "budget "
            + PREFIX_TAG + "pets";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    public static final String INVALID_CONTACT_TYPE =
            "This contact is not a vendor or client, and is of an invalid type";

    private final Contact toAdd;


    /**
     * Creates an AddCommand to add the specified {@code Contact}
     */
    public AddCommand(Contact contact) {
        requireNonNull(contact);
        this.toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        if (toAdd instanceof Client) {
            model.addContact(toAdd);
        } else if (toAdd instanceof Vendor) {
            model.addContact(toAdd);
        } else {
            throw new CommandException(INVALID_CONTACT_TYPE);
        }

        // Increment ID counter only if addition of contact is successful
        AddressBook.incrementNextContactId();

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
