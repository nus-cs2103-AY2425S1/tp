package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Makes the address book ready for clearing and prompts for confirmation to clear.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CHECK = "Type confirm to clear listed participants \n"
                                                + "Type anything else to cancel clearing";
    private static boolean isClear;


    public static boolean getIsClear() {
        return ClearCommand.isClear;
    }

    public static void setIsClear(boolean isClear) {
        ClearCommand.isClear = isClear;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ClearCommand.setIsClear(true);
        return new CommandResult(MESSAGE_CHECK);
    }
}
