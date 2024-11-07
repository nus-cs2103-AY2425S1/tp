package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_CLIENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_EMPLOYEES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.InspectWindow;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_PERSON = "Listed all persons";

    public static final String MESSAGE_SUCCESS_DELIVERY = "Listed all deliveries";

    public static final String MESSAGE_SUCCESS_SHOW_CLIENTS = "Listed all clients";

    public static final String MESSAGE_SUCCESS_SHOW_EMPLOYEES = "Listed all employees";

    private String target;

    public ListCommand(String target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (target.equalsIgnoreCase("all")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
            boolean isInspect = AddressBookParser.getInspect();
            Person inspectedPerson;
            String message;
            if (isInspect) {
                inspectedPerson = InspectWindow.getInspectedPerson();
                message = MESSAGE_SUCCESS_DELIVERY;
            } else {
                inspectedPerson = null;
                message = MESSAGE_SUCCESS_PERSON;
            }
            return new CommandResult(message, inspectedPerson, false, false, isInspect, !isInspect);
        }

        if (target.equals("employees")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ONLY_EMPLOYEES);
            return new CommandResult(
                    MESSAGE_SUCCESS_SHOW_EMPLOYEES, null, false, false, false, true);
        }

        if (target.equals("clients")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ONLY_CLIENTS);
            return new CommandResult(
                    MESSAGE_SUCCESS_SHOW_CLIENTS, null, false, false, false, true);
        }
        throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
    }
}
