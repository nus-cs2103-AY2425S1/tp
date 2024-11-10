package seedu.address.testutil;

import seedu.address.logic.commands.ListGroupsCommand;

/**
 * A utility class for Group.
 */
public class GroupsUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String groupsCommand() {
        return ListGroupsCommand.COMMAND_WORD + " g/Meow" + " s/";
    }

}
