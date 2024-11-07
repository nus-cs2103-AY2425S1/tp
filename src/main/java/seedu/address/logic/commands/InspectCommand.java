package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Worker;

/**
 * Inspects a contact identified using it's displayed index from the address book.
 */
public class InspectCommand extends Command {

    public static final String COMMAND_WORD = "inspect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Inspects the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INSPECT_SUCCESS = "Inspected person: %1$s";
    private final Index index;

    /**
     * @param index of the contact to be inspected
     */
    public InspectCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, index.getOneBased()));
        }

        if (index.getZeroBased() >= model.getFirstArchivedIndex().getZeroBased()) {
            throw new CommandException(Messages.MESSAGE_ARCHIVED_PERSON_DISPLAYED_INDEX);
        }

        if (AddressBookParser.getInspect()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WINDOW);
        }

        Person personToInspect = lastShownList.get(index.getZeroBased());

        if (personToInspect.isClient()) {
            model.setFilteredDeliveryList(personToInspect.getDeliveryList());
        } else {
            Worker worker = personToInspect.getWorker();
            model.setFilteredDeliveryList(worker.getAssignedDeliveryList());
        }

        return new CommandResult(generateSuccessMessage(personToInspect), personToInspect, false,
                false, true);
    }

    private String generateSuccessMessage(Person personToInspect) {
        String message = MESSAGE_INSPECT_SUCCESS;
        return String.format(message, Messages.format(personToInspect));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InspectCommand)) {
            return false;
        }

        InspectCommand i = (InspectCommand) other;
        return index.equals(i.index);
    }
}
