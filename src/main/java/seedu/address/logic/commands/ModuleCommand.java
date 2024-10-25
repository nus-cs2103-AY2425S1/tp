package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Assigns a course-specific grade to a student.
 */
public class ModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to a student. "
            + "Parameters: "
            + "ID "
            + PREFIX_MODULE + "MODULE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "12345678 "
            + PREFIX_MODULE + "CS2103T ";

    public static final String MESSAGE_SUCCESS = "New module added for Student %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist in the address book";
    public static final String MESSAGE_DUPLICATE_MODULE = "The module %1$s already exists for this student";

    private final Module module;
    private final StudentId studentId;

    /**
     * Creates a ModuleCommand to add the specified {@code Module}
     */
    public ModuleCommand(StudentId studentId, Module module) {
        requireNonNull(studentId);
        requireNonNull(module);
        this.module = module;
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        Person person = null;

        for (Person p : lastShownList) {
            if (p.getStudentId().equals(studentId)) {
                person = p;
                break;
            }
        }

        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        ArrayList<Module> modules = person.getModules();
        if (modules.contains(module)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, module.value));
        }

        Person updatedPerson = person.addModule(module);
        model.setPerson(person, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentId));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCommand)) {
            return false;
        }

        ModuleCommand otherModuleCommand = (ModuleCommand) other;
        return module.equals(otherModuleCommand.module);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("toAdd", module)
                .toString();
    }
}
