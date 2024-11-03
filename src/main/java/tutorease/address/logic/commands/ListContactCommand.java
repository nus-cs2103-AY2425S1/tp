package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import tutorease.address.model.Model;

/**
 * Lists all contacts in the address book.
 */
public class ListContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all contacts";
    public static final String MESSAGE_NO_CONTACTS_FOUND = "No contacts found.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACTS_FOUND); // catch the case of no contacts found
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

