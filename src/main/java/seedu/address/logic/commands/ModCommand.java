package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModuleName;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ModCommand extends Command{

    public static final String COMMAND_WORD = "mod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the module of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "m/ [MOD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "m/ CS1101S";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Module: %2$s";

    private final Index index;
    private final ModuleName moduleName;

    /**
     * @param index of the person in the filtered person list to edit the module of
     * @param moduleName of the person to be updated to
     */
    public ModCommand(Index index, ModuleName moduleName) {
        requireAllNonNull(index, moduleName);

        this.index = index;
        this.moduleName = moduleName;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), moduleName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModCommand)) {
            return false;
        }

        ModCommand e = (ModCommand) other;
        return index.equals(e.index)
                && moduleName.equals(e.moduleName);
    }
}
