package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;

/**
 * Adds a customer to the address book.
 */
public class AddCustomerCommand extends Command {

    public static final String COMMAND_WORD = "addCustomer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_INFORMATION + "INFORMATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_INFORMATION + "Allergic to Milk "
            + PREFIX_TAG + "loyal";

    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PHONE = "A contact in the address book already has this phone number. "
            + "Please use a different phone number.";
    public static final String MESSAGE_INVALID_TAG = "The tag 'Supplier' is not allowed for customers.";

    private final Customer toAdd;

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}
     */
    public AddCustomerCommand(Customer customer) {
        requireNonNull(customer);
        toAdd = customer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check for duplicate phone number across all contacts
        for (Person person : model.getFilteredPersonList()) {
            if (person.getPhone().equals(toAdd.getPhone())) {
                throw new CommandException(MESSAGE_DUPLICATE_PHONE);
            }
        }
        // Check for the "Supplier" tag and throw an exception if present
        if (toAdd.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase("Supplier"))) {
            throw new CommandException(MESSAGE_INVALID_TAG);
        }

        // Add the new customer if no duplicates are found
        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCustomerCommand)) {
            return false;
        }

        AddCustomerCommand otherAddCustomerCommand = (AddCustomerCommand) other;
        return toAdd.equals(otherAddCustomerCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}