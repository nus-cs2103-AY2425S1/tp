package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.eventtory.commons.util.ToStringBuilder;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Adds a vendor to EventTory.
 */
public class CreateVendorCommand extends CreateCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a vendor to EventTory.\n"
            + "Parameters: "
            + PREFIX_VENDOR + "<empty> "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_VENDOR + " "
            + PREFIX_NAME + "Adam's Bakery "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_DESCRIPTION + "Pastries and cakes, bake in a day "
            + PREFIX_TAG + "pastry "
            + PREFIX_TAG + "fast";

    public static final String MESSAGE_SUCCESS = "New vendor added: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in EventTory";

    private final Vendor toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Vendor}
     */
    public CreateVendorCommand(Vendor vendor) {
        requireNonNull(vendor);
        toAdd = vendor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVendor(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.addVendor(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateVendorCommand)) {
            return false;
        }

        CreateVendorCommand otherAddCommand = (CreateVendorCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
