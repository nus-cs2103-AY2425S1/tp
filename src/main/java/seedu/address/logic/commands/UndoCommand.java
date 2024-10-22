package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * A command used to undo previous undoable commands.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous undoable command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful";

    @Override
    public CommandResult execute(Model model) {
        CommandStack commandStack = CommandStack.getInstance();
        if (commandStack.isEmpty()) {
            return new CommandResult("There are no commands to undo");
        }
        Command command = commandStack.pop();
        boolean isUndoable = command.undo(model);
        if (!isUndoable) {
            return new CommandResult("The previous command is not undoable");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UndoCommand;
    }
}
