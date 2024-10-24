package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Supplier;

/**
 * Adds a supplier to the address book.
 */
public class AddSupplierCommand extends Command {

    public static final String COMMAND_WORD = "addSupplier";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PREFERENCE + "PREFERENCE "
            + PREFIX_INGREDIENTS_SUPPLIED + "INGREDIENTS SUPPLIED "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Supplier ABC "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "supplierabc@example.com "
            + PREFIX_ADDRESS + "123, Clementi Rd, #04-01 "
            + PREFIX_PREFERENCE + "Organic "
            + PREFIX_INGREDIENTS_SUPPLIED + "Flour, Sugar, Butter "
            + PREFIX_TAG + "wholesale";

    public static final String MESSAGE_SUCCESS = "New supplier added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in the address book";

    private final Supplier toAdd;

    /**
     * Creates an AddSupplierCommand to add the specified {@code Supplier}
     */
    public AddSupplierCommand(Supplier supplier) {
        requireNonNull(supplier);
        toAdd = supplier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSupplierCommand)) {
            return false;
        }

        AddSupplierCommand otherAddSupplierCommand = (AddSupplierCommand) other;
        return toAdd.equals(otherAddSupplierCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

