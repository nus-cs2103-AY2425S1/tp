package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_PERSON = "Listed all persons";

    public static final String MESSAGE_SUCCESS_DELIVERY = "Listed all deliveries";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        boolean isInspect = AddressBookParser.getInspect();
        Person inspectedPerson;
        String MESSAGE;
        if (isInspect) {
            inspectedPerson = model.getFilteredPersonList().get(Index.fromZeroBased(0).getZeroBased());
            MESSAGE = MESSAGE_SUCCESS_DELIVERY;
        }   else {
            inspectedPerson = null;
            MESSAGE = MESSAGE_SUCCESS_PERSON;
        }
        return new CommandResult(MESSAGE, inspectedPerson, false, false, isInspect, !isInspect);
    }
}
