package seedu.address.testutil;

import seedu.address.logic.commands.GroupsCommand;

/**
 * A utility class for Group.
 */
public class GroupsUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String groupsCommand() {
        return GroupsCommand.COMMAND_WORD + " g/Meow" + " s/";
    }

}
