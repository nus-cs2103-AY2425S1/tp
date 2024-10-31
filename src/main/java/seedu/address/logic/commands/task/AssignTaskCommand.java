package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ADD_TASK_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.task.Task;


/**
 * Assigns one or more tasks to a person in the person list.
 * The person must be a Vendor, as only vendors can be assigned tasks.
 */
public class AssignTaskCommand extends Command {

    public static final String COMMAND_WORD = "assign-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or multiple tasks to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "TASK_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + "1";

    public static final String MESSAGE_DUPLICATE_TASK = "Task '%s' is already assigned to %s";
    private final Index personIndex;
    private final Set<Index> taskIndexes;

    /**
     * Constructs a {@code TaskCommand} to assign an existing task to a person.
     *
     * @param perosnIndex The index of the person in the person list.
     * @param taskIndexes The indexes of the tasks in the task list.
     */
    public AssignTaskCommand(Index perosnIndex, Set<Index> taskIndexes) {
        requireNonNull(perosnIndex);
        requireNonNull(taskIndexes);
        this.personIndex = perosnIndex;
        this.taskIndexes = taskIndexes;
    }

    /**
     * Generates a command execution success message showing the added tasks and the person.
     *
     * @param personToEdit The person to whom the tasks were added.
     * @return A success message indicating the tasks that were added and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit, Set<Task> taskToAdd) {
        String addedTasks = taskToAdd.stream()
                .map(task -> task.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_ADD_TASK_SUCCESS, addedTasks, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Task> lastShownTaskList = model.getFilteredTaskList();


        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownPersonList.get(personIndex.getZeroBased());

        boolean isVendor = false;
        for (Vendor vendor : model.getFilteredVendorList()) {
            if (personToEdit.getName().equals(vendor.getName())) {
                isVendor = true;
                break;
            }
        }
        if (!isVendor) {
            throw new CommandException(MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK);
        }

        Set<Task> tasksToAdd = new HashSet<>();
        Set<Task> updatedTasks = new HashSet<>(personToEdit.getTasks());
        for (Index taskIndex : taskIndexes) {
            if (taskIndex.getZeroBased() >= lastShownTaskList.size()) {
                throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            Task newTask = lastShownTaskList.get(taskIndex.getZeroBased());
            if (updatedTasks.contains(newTask)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TASK,
                        newTask.toString(), personToEdit.getName().toString()));
            }
            tasksToAdd.add(newTask);
            updatedTasks.add(newTask);
        }

        Person editedPerson = PersonTaskEditorUtil.createEditedPersonWithUpdatedTasks(personToEdit, updatedTasks);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(editedPerson, tasksToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignTaskCommand)) {
            return false;
        }

        AssignTaskCommand otherCommand = (AssignTaskCommand) other;
        return personIndex.equals(otherCommand.personIndex) && taskIndexes.equals(otherCommand.taskIndexes);
    }
}
