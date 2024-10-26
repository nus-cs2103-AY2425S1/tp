package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_CLEAR;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private Model oldModel;


    @Override
    public CommandResult execute(Model model) {
        oldModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), model.getUserPrefs());
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        model.setAddressBook(this.oldModel.getAddressBook());
        model.setUserPrefs(this.oldModel.getUserPrefs());
        pastCommands.remove();
        return MESSAGE_UNDO_CLEAR;
    }
}
