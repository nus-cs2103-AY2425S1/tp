package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Worker;
import seedu.address.ui.InspectWindow;

/**
 * Assigns a delivery to an employee using the delivery's displayed index from the address book and the name of the
 * employee.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a delivery to an employee. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + PREFIX_NAME + "EMPLOYEE NAME";

    public static final String MESSAGE_SUCCESS = "Assigned delivery %1$s to %2$s";
    public static final String MESSAGE_NOT_IN_INSPECT = "Assign command can only be used in the inspect window!";
    public static final String MESSAGE_NOT_EMPLOYEE = "Entered person is not an employee";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "This delivery has already been assigned to this person";
    private final Index index;
    private final Name toAssign;

    /**
     * Creates an AssignCommand to assign the specified delivery using {@code Index} to {@code Worker}
     */
    public AssignCommand(Name worker, Index index) {
        requireAllNonNull(worker, index);
        this.toAssign = worker;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (AddressBookParser.getInspect()) {
            requireNonNull(model);
            Person inspectedPerson = InspectWindow.getInspectedPerson();
            List<Delivery> lastShownList = model.getFilteredDeliveryList();
            String[] nameKeywords = toAssign.fullName.split("\\s+");

            validateIndex(lastShownList.size(), index);

            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of(nameKeywords)));

            Person person = model.getFilteredPersonList().get(0);
            if (person.isClient()) {
                throw new CommandException(MESSAGE_NOT_EMPLOYEE);
            }
            Worker worker = person.getWorker();
            Delivery delivery = lastShownList.get(index.getZeroBased());

            if (worker.hasDelivery(delivery)) {
                throw new CommandException(MESSAGE_DUPLICATE_DELIVERY);
            }
            worker.addDelivery(delivery);
            return new CommandResult(String.format(MESSAGE_SUCCESS, delivery.getDeliveryId(),
                    person.getName().fullName), inspectedPerson, false, false, true,
                    false);
        } else {
            throw new CommandException(MESSAGE_NOT_IN_INSPECT);
        }
    }

    /**
     * Validates the index to ensure it is not out of bounds
     *
     * @param listSize Size of the list from which items are to be assigned.
     * @param index The index to be validated
     * @throws CommandException if any index is out of bounds
     */
    private void validateIndex(int listSize, Index index) throws CommandException {
        if (index.getOneBased() > listSize) {
            String exceptionMessage = String.format(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX,
                    Messages.formatIndexList(List.of(index)));
            throw new CommandException(exceptionMessage);
        }
    }
}
