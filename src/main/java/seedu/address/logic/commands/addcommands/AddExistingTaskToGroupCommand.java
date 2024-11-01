package seedu.address.logic.commands.addcommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;

public class AddExistingTaskToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_etask_grp";
    public static final String COMMAND_WORD_ALIAS = "aetg";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        return null;
    }
}
