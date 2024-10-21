package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialClass;

/**
 * Adds a student to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the tutorial book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_STUDENTID + "STUDENT_ID "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Samson "
            + PREFIX_STUDENTID + "1001 "
            + PREFIX_TUTORIALCLASS + "1001 ";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";
    public static final String MESSAGE_DUPLICATE_STUDENTID = "This student ID already exists in the address book: ";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "The tutorial ID provided doesn't exist! \nTutorial ID: ";

    private final Student toAdd;
    private final TutorialClass tutorialClass;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddCommand(Student student, TutorialClass tutorialClass) {
        requireNonNull(student);
        toAdd = student;
        this.tutorialClass = tutorialClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        if (model.hasStudentWithId(toAdd.getStudentId())) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENTID + toAdd.getStudentId());
        }

        if (!model.hasTutorial(tutorialClass)) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND + tutorialClass);
        }

        model.assignStudent(toAdd, tutorialClass);
        model.addStudent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
