package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Provide a quick overview of every command even if user is not connected to internet.
 */
public class OfflineHelpCommand extends Command {

    public static final String COMMAND_WORD = "offlinehelp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Provides a quick overview of all commands.\n"
            + "There should be no parameters!\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_OVERVIEW = """
            Add: add n/NAME id/STUDENT_ID[nid/NUS_NET_ID] [m/MAJOR] [y/YEAR] [g/GROUP]
            Edit: edit INDEX [n/NAME] [id/STUDENT_ID] [nid/NUS_NET_ID][[m/MAJOR] [y/YEAR] [g/GROUP]
            Comment: comment INDEX c/COMMENTFind: find [n/ NAME_KEYWORDS] [id/ STUDENT_IDS]
            Show group: show GROUP_INDEX
            Delete: delete INDEX
            List: list INDEX
            Clear: clear INDEX
            Help: help""";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_OVERVIEW);
    }
}
