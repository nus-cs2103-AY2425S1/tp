package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: d/DESCRIPTION p/PERSON\n"
            + "Example: " + COMMAND_WORD + " d/Buy medication p/John Doe";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_NONEXISTENT_PERSON = "The person '%1$s' does not exist in the system!";

    private final String taskDescription;
    private final Name personName;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task} and associate it with {@code Person}.
     */
    public AddTaskCommand(String taskDescription, Name personName) {
        requireNonNull(taskDescription);
        requireNonNull(personName);
        this.taskDescription = taskDescription;
        this.personName = personName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Validate if the person (patient) exists in the system
        Person patient = findPersonByName(personName, model);
        if (patient == null) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_PERSON, personName.fullName));
        }

        // Create the task with the valid patient
        Task taskToAdd = new Task(patient, taskDescription);

        // Check for duplicate task
        if (model.hasTask(taskToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        // Add the task to the model
        model.addTask(taskToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd.getDescription()));
    }

    /**
     * Helper method to find a person by name in the model.
     * @param name The name of the person to search for.
     * @return The Person object if found, or null if not found.
     */
    private Person findPersonByName(Name name, Model model) {
        for (Person person : model.getFilteredPersonList()) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherAddTaskCommand = (AddTaskCommand) other;
        return taskDescription.equals(otherAddTaskCommand.taskDescription)
                && personName.equals(otherAddTaskCommand.personName);
    }
}
