package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.GROUP_ONE;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_TEN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FOUR;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_THREE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_TWO;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */

public class TypicalGroups {

    private TypicalGroups() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and groups.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.addGroup(new Group(new GroupName(TEAM_FIVE)));
        ab.addGroup(new Group(new GroupName(TEAM_FOUR)));
        ab.addGroup(new Group(new GroupName(TEAM_THREE)));
        ab.addGroup(new Group(new GroupName(TEAM_TWO)));
        ab.addGroup(new Group(new GroupName(GROUP_ONE)));
        ab.addGroup(new Group(new GroupName(TEAM_ONE)));
        ab.addGroup(new Group(new GroupName(GROUP_TEN)));
        return ab;
    }
}
