package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

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
            + PREFIX_STUDENTID + "ID "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GRADE + "GRADE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENTID + "12345678 "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_GRADE + "A+ ";

    public static final String MESSAGE_SUCCESS = "New grade added for %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist in the address book";

    public static final String DUPLICATE_MODULE = "The module %1$s already exists for this student";

    private final Module module;
    private final StudentId studentId;
    private final Grade grade;

    /**
     * Creates an AddCommand to add the specified {@code Person}
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

        ArrayList<Module> moduleGrades = person.getModuleGrades();
        if (moduleGrades.contains(module)) {
            throw new CommandException(String.format(DUPLICATE_MODULE, module));
        }

        person.addModuleGrade(module, grade);

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
                .add("toAdd", module)
                .toString();
    }
}
