package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SupplierStatus;


/**
 * Marks a supplier as active or inactive in the address book.
 */
public class MarkSupplierCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_SUPPLIER + " "
            + ": Marks the status of the supplier identified by the index number used in the displayed supplier list.\n"
            + "Parameters: INDEX (must be a positive integer) STATUS (active, inactive)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUPPLIER + " 1 active";

    public static final String MESSAGE_MARK_SUPPLIER_SUCCESS = "Marked Supplier %1$s as %2$s";

    private final Index targetIndex;
    private final SupplierStatus status;

    /**
     * Creates an MarkSupplierCommand to mark the supplier at {@code index} with new status {@code status}
     */
    public MarkSupplierCommand(Index targetIndex, SupplierStatus status) {
        this.targetIndex = targetIndex;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Person supplierToMark = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        Person markedSupplier = new Person(
                supplierToMark.getName(),
                supplierToMark.getPhone(),
                supplierToMark.getEmail(),
                supplierToMark.getCompany(),
                supplierToMark.getTags(),
                supplierToMark.getProducts(),
                status);

        model.setPerson(supplierToMark, markedSupplier);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_SUPPLIER_SUCCESS, targetIndex.getOneBased(), status));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkSupplierCommand // instanceof handles nulls
                && targetIndex.equals(((MarkSupplierCommand) other).targetIndex)
                && status.equals(((MarkSupplierCommand) other).status)); // state check
    }
}
