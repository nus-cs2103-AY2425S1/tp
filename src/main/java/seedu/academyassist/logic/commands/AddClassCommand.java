package seedu.academyassist.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Subject;

/**
 * Adds a class to a student in the management system.
 */
public class AddClassCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a class to the student with this ic number. "
            + "Parameters: \n- "
            + PREFIX_IC + "IC \n- "
            + PREFIX_SUBJECT + "Subject \n- "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IC + "S1234567A "
            + PREFIX_SUBJECT + "Math ";


    public static final String MESSAGE_SUCCESS = "Added %1$s : %2$s to %3$s";
    public static final String MESSAGE_DUPLICATE_CLASS = "Student is already taking that subject. "
            + "Please check the student details and try again";
    public static final String MESSAGE_NO_STUDENT_FOUND = "No student found with specified ic";


    private final Ic toAddIc;
    private final Subject toAddSubject;
    private Person student;

    /**
     * Creates an AddClassCommand to add the class to Person with {@code Ic}
     */
    public AddClassCommand(Ic toAddIc, Subject toAddSubject) {
        requireNonNull(toAddIc);
        this.toAddIc = toAddIc;
        this.toAddSubject = toAddSubject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        if (!model.hasPersonWithIc(toAddIc)) {
            throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
        }

        student = model.getPersonWithIc(toAddIc);

        if (model.personDuplicateClass(toAddSubject, student)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASS);
        }

        model.addSubjectToPerson(toAddSubject, student);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddIc.toString(),
                student.getName().toString(), toAddSubject.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClassCommand)) {
            return false;
        }

        AddClassCommand otherAddClassCommand = (AddClassCommand) other;
        return toAddIc.equals(otherAddClassCommand.toAddIc) && toAddSubject.equals(otherAddClassCommand.toAddSubject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddIc", toAddIc)
                .add("toAddSubject", toAddSubject)
                .toString();
    }
}
