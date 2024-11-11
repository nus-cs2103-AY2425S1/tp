package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.Model;

/**
 * Lists all contacts in the address book.
 */
public class ListContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all contacts.";
    public static final String MESSAGE_NO_CONTACTS_FOUND = "No contacts found.";
    private static final Logger logger = LogsCenter.getLogger(ListContactCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.log(Level.INFO, "Executing List Contact Command");
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assert model.getFilteredPersonList() != null;
        if (model.getFilteredPersonListSize() == 0) {
            logger.log(Level.INFO, "No contacts exist in TutorEase.");
            return new CommandResult(MESSAGE_NO_CONTACTS_FOUND); // catch the case of no contacts found
        }
        logger.log(Level.INFO, "Found " + model.getFilteredPersonListSize() + " contacts.");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

