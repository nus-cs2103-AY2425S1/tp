package seedu.address.testutil;

import javafx.collections.FXCollections;
import seedu.address.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group GOONERS = new Group("Gooners",
            FXCollections.observableArrayList(TypicalPersons.ALICE, TypicalPersons.BOB));

    public static final Group GROUP_A = new Group("GroupA",
            FXCollections.observableArrayList(TypicalPersons.ALICE));

    public static final Group GROUP_B = new Group("GroupB",
            FXCollections.observableArrayList(TypicalPersons.BOB));

    private TypicalGroups() {} // prevents instantiation


}
