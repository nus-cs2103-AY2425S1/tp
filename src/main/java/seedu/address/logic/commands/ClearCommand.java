package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_CLEAR;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private Model oldModel;
    private Predicate<Person> oldPredicate;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        oldModel = new ModelManager(model.getAddressBook(), model.getAppointmentList(), model.getUserPrefs());
        oldPredicate = model.getFilteredPersonListPredicate()::test;
        model.setAddressBook(new AddressBook());
        model.setAppointmentList(new ArrayList<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        model.setAddressBook(oldModel.getAddressBook());
        model.setAppointmentList(oldModel.getAppointmentList());
        model.setUserPrefs(oldModel.getUserPrefs());
        model.updateFilteredPersonList(oldPredicate);
        pastCommands.remove();
        return MESSAGE_UNDO_CLEAR;
    }
}
