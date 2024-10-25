package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the Student ID used in the displayed person list,\n"
            + "or deletes a module from the person's module list.\n"
            + "Parameters: "
            + "ID\n"
            + "or: "
            + "ID MODULE_KEYWORD"
            + "Example: " + COMMAND_WORD + " "
            + "12345678"
            + "or: " + COMMAND_WORD + " "
            + "12345678 m/CS2103T";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    public static final String MESSAGE_PERSON_NOT_FOUND = "No student is found with Student ID: %1$s";
    public static final String MESSAGE_MODULE_NOT_FOUND = "No module is found for this student: %1$s";
    private final StudentId studentId;
    private final Module module;

    /**
     * Creates a DeleteCommand to delete the person identified by the specified {@code StudentId}.
     */
    public DeleteCommand(StudentId studentId) {
        requireNonNull(studentId);
        this.studentId = studentId;
        this.module = null;
    }

    /**
     * Creates a DeleteCommand to delete the module by the specified {@code Module}.
     */
    public DeleteCommand(StudentId studentId, Module module) {
        requireNonNull(studentId);
        requireNonNull(module);
        this.studentId = studentId;
        this.module = module;
    }

    /**
     * Executes the delete command and removes a person identified by the given studentID.
     *
     * @param model the model that contains the data of persons
     * @return a CommandResult that shows the outcome of the command
     * @throws CommandException if the studentID is invalid or not found
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person toDelete = null;

        for (Person person : lastShownList) {
            if (person.getStudentId().equals(studentId)) {
                toDelete = person;
                break;
            }
        }

        if (toDelete == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentId));
        }

        if (module != null) {
            if (!toDelete.hasModule(module)) {
                throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, toDelete.getStudentId()));
            }
            model.deleteModule(toDelete, module);
            return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, module.toString()));
        }

        model.deletePerson(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(toDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return studentId.equals(otherDeleteCommand.studentId)
                && (module == null
                        ? otherDeleteCommand.module == null
                        : module.equals(otherDeleteCommand.module));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", studentId)
                .add("module", module)
                .toString();
    }
}
