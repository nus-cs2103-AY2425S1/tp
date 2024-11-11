package seedu.ddd.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.CommandResult;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Adds a contact to the address book.
 */
public class AddContactCommand extends AddCommand {
    public static final String COMMAND_DESCRIPTION_TEMPLATE = COMMAND_WORD + " %1$s: adds a %2$s.";
    public static final String COMMAND_USAGE_TEMPLATE = "usage: " + COMMAND_WORD + " %1$s "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            // For service field in vendor
            + "%2$s"
            + "[" + PREFIX_TAG + "TAG ...]";

    public static final String CLIENT_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " "
            + FLAG_CLIENT + " "
            + PREFIX_NAME + "Jane Doe "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "jd@gmail.com "
            + PREFIX_ADDRESS + "Blk 123 St 4 "
            + PREFIX_TAG + "budget ";
    public static final String VENDOR_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " "
            + FLAG_VENDOR + " "
            + PREFIX_NAME + "ABC Catering "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "abc@abc.com "
            + PREFIX_ADDRESS + "Blk 567 St 8 "
            + PREFIX_SERVICE + "catering "
            + PREFIX_TAG + "vegan "
            + PREFIX_TAG + "budget";

    public static final String MESSAGE_USAGE =
            String.format(COMMAND_DESCRIPTION_TEMPLATE, FLAG_CLIENT + " | " + FLAG_VENDOR, "contact") + "\n"
            + String.format(COMMAND_USAGE_TEMPLATE,
                "{" + FLAG_CLIENT + " | " + FLAG_VENDOR + " " + PREFIX_SERVICE + "SERVICE}", "") + "\n"
            + CLIENT_EXAMPLE_USAGE + "\n"
            + VENDOR_EXAMPLE_USAGE;
    public static final String VENDOR_MESSAGE_USAGE =
            String.format(COMMAND_DESCRIPTION_TEMPLATE, FLAG_VENDOR, "vendor") + "\n"
            + String.format(COMMAND_USAGE_TEMPLATE, FLAG_VENDOR, PREFIX_SERVICE + "SERVICE ") + "\n"
            + VENDOR_EXAMPLE_USAGE;
    public static final String CLIENT_MESSAGE_USAGE =
            String.format(COMMAND_DESCRIPTION_TEMPLATE, FLAG_CLIENT, "client") + "\n"
            + String.format(COMMAND_USAGE_TEMPLATE, FLAG_CLIENT, "") + "\n"
            + CLIENT_EXAMPLE_USAGE;

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    public static final String INVALID_CONTACT_TYPE =
            "This contact is not a vendor or client, and is of an invalid type";

    private final Contact toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Contact}
     */
    public AddContactCommand(Contact contact) {
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
        if (!(other instanceof AddContactCommand)) {
            return false;
        }

        AddContactCommand otherAddContactCommand = (AddContactCommand) other;
        return toAdd.equals(otherAddContactCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
