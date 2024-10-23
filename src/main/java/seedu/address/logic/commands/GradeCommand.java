package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Assigns a course-specific grade to a student.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a course-specific grade to a student. "
            + "Parameters: "
            + "ID "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GRADE + "GRADE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "12345678 "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_GRADE + "A+ ";

    public static final String MESSAGE_SUCCESS = "New grade added for %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist in the address book";
    public static final String MESSAGE_MODULE_NOT_FOUND = "The student does not take the module %1$s";

    private final Module module;
    private final StudentId studentId;
    private final Grade grade;

    /**
     * Creates a GradeCommand to assign a grade to the specified {@code Module}
     */
    public GradeCommand(StudentId studentId, Module module, Grade grade) {
        requireNonNull(studentId);
        requireNonNull(module);
        requireNonNull(grade);
        this.module = module;
        this.studentId = studentId;
        this.grade = grade;
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
        if (!modules.contains(module)) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, module.value));
        }

        Person updatedPerson = person.setModuleGrade(module, grade);
        model.setPerson(person, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, module));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand otherGradeCommand = (GradeCommand) other;
        return module.equals(otherGradeCommand.module);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("module", module)
                .add("toAdd", grade)
                .toString();
    }
}
