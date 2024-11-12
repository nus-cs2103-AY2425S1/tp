package seedu.address.testutil;

import seedu.address.logic.commands.GroupCommand;

/**
 * A utility class for Group.
 */
public class GroupUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String groupCommand() {
        return GroupCommand.COMMAND_WORD + " g/Meow" + " s/";
    }

}
