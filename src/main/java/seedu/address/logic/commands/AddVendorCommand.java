package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Vendor;

/**
 * Adds a person to the address book.
 */
public class AddVendorCommand extends Command {

    public static final String COMMAND_WORD = "add_vendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a vendor to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_COMPANY + "COMPANY "
            + "[" + PREFIX_BUDGET + "BUDGET] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Mall, #02-25 "
            + PREFIX_COMPANY + "John's Florist "
            + PREFIX_BUDGET + "1000 "
            + PREFIX_TAG + "florist "
            + PREFIX_TAG + "responsible";

    public static final String MESSAGE_SUCCESS = "New vendor added: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in the address book";

    private final Vendor toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddVendorCommand(Vendor vendor) {
        requireNonNull(vendor);
        toAdd = vendor;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddVendorCommand)) {
            return false;
        }

        AddVendorCommand otherAddCommand = (AddVendorCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
