package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FIVE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INVALID_INDEX_TASK;
import static seedu.address.testutil.TypicalTasks.TASK_PENDING;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditTaskAllGroupCommand;
import seedu.address.logic.commands.editcommands.EditTaskAllGroupCommand.EditTaskDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

public class EditTaskAllGroupCommandTest {

    private Model model;
    private Task taskToEdit;
    private Task editedTask;
    private EditTaskDescriptor editTaskDescriptor;
    private Group group;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        group = new Group(new GroupName(TEAM_FIVE));
        taskToEdit = TASK_PENDING;
        model.addTask(taskToEdit);
        group.addTask(taskToEdit);
        model.addGroup(group);
        editedTask = new Task(new TaskName("Revised Task"), new Deadline(LocalDateTime.of(2025, 12, 31, 23, 59)));

        editTaskDescriptor = new EditTaskDescriptor();
        editTaskDescriptor.setTaskName(new TaskName("Revised Task"));
        editTaskDescriptor.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 31, 23, 59)));
    }

    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {
        EditTaskAllGroupCommand command = new EditTaskAllGroupCommand(INDEX_FIRST_TASK, editTaskDescriptor);
        CommandResult result = command.execute(model);
        assertEquals(String.format(EditTaskAllGroupCommand.MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask),
            Messages.format(group)), result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        EditTaskAllGroupCommand command = new EditTaskAllGroupCommand(INVALID_INDEX_TASK, editTaskDescriptor);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_DISPLAYED_INDEX, ()->command.execute(model));
    }

    @Test
    public void equals() {
        EditTaskDescriptor descriptorOne = new EditTaskDescriptor();
        descriptorOne.setTaskName(new TaskName("Task A"));
        descriptorOne.setDeadline(new Deadline(LocalDateTime.of(2024, 10, 10, 10, 10)));

        EditTaskDescriptor descriptorTwo = new EditTaskDescriptor();
        descriptorTwo.setTaskName(new TaskName("Task B"));
        descriptorTwo.setDeadline(new Deadline(LocalDateTime.of(2025, 11, 11, 11, 11)));

        EditTaskAllGroupCommand commandOne = new EditTaskAllGroupCommand(INDEX_FIRST_TASK, descriptorOne);
        EditTaskAllGroupCommand commandTwo = new EditTaskAllGroupCommand(INVALID_INDEX_TASK, descriptorTwo);
        EditTaskAllGroupCommand commandOneCopy = new EditTaskAllGroupCommand(INDEX_FIRST_TASK, descriptorOne);
        assertTrue(commandOne.equals(commandOne));
        assertTrue(commandOne.equals(commandOneCopy));
        assertFalse(commandTwo.equals(commandOne));
        assertFalse(commandOne.equals(null));
        assertFalse(commandOne.equals(2));
    }
}
