package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static seedu.address.logic.commands.CommandTestUtil.GROUP_ONE;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_TEN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FOUR;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_THREE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_TWO;

public class TypicalTasks {
    private static final Task TASK_COMPLETED = new TaskBuilder()
        .withTaskName("Finish iP")
        .withDeadline(LocalDateTime.of(2024, 9, 15, 23, 59))
        .withStatus(Status.COMPLETED)
        .build();

    private static final Task TASK_PENDING = new TaskBuilder()
        .withTaskName("Finish tP v1.3")
        .withDeadline(LocalDateTime.of(2024, 9, 17, 23, 59))
        .withStatus(Status.PENDING)
        .build();

    /**
     * Prevents instantiation
     */
    private TypicalTasks() {}

//    public static AddressBook getTypicalTasks() {
//        AddressBook ab = new AddressBook();
//        ab.addGroup(new Group(new GroupName(TEAM_FIVE)));
//        ab.addGroup(new Group(new GroupName(TEAM_FOUR)));
//        ab.addGroup(new Group(new GroupName(TEAM_THREE)));
//        ab.addGroup(new Group(new GroupName(TEAM_TWO)));
//        ab.addGroup(new Group(new GroupName(GROUP_ONE)));
//        ab.addGroup(new Group(new GroupName(TEAM_ONE)));
//        ab.addGroup(new Group(new GroupName(GROUP_TEN)));
//    }

}
