package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.Messages.MESSAGE_NO_STUDENT_FOUND;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;

/**
 * Adds subject(s) to a student in the management system.
 */
public class AddSubjectCommand extends Command {

    public static final String COMMAND_WORD = "addsubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds subject(s) to the student with this student id. \n"
            + "Parameters: StudentID (S followed by 5-digit number) "
            + PREFIX_SUBJECT + "SUBJECT \n"
            + "Example: " + COMMAND_WORD + " S12345 "
            + PREFIX_SUBJECT + "Math ";


    public static final String MESSAGE_SUCCESS = "Added %1$s : %2$s to %3$s";
    public static final String MESSAGE_DUPLICATE_SUBJECT = "Student is already taking one of those subjects. "
            + "Please check the student details and try again";

    private final StudentId studentId;
    private final Set<Subject> toAddSubjects;
    private Person student;

    /**
     * Creates an AddSubjectCommand to add the class(es) to Person with {@code Ic}
     */
    public AddSubjectCommand(StudentId studentId, Set<Subject> toAddSubjects) {
        requireNonNull(studentId);
        requireNonNull(toAddSubjects);
        this.studentId = studentId;
        this.toAddSubjects = toAddSubjects;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        if (!model.hasPersonWithStudentId(studentId)) {
            throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
        }

        student = model.getPersonWithStudentId(studentId);

        if (model.personDuplicateClass(toAddSubjects, student)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUBJECT);
        }

        model.addSubjectsToPerson(toAddSubjects, student);

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentId.toString(),
                student.getName().toString(), toAddSubjects.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubjectCommand)) {
            return false;
        }

        AddSubjectCommand otherAddSubjectCommand = (AddSubjectCommand) other;
        return studentId.equals(otherAddSubjectCommand.studentId)
                && toAddSubjects.equals(otherAddSubjectCommand.toAddSubjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("toAddSubjects", toAddSubjects)
                .toString();
    }
}
