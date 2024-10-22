package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Adds a student to the address book.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "adds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "CONTACT_NUMBER "
            + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TUTORIAL_GROUP + "G01 "
            + PREFIX_STUDENT_NUMBER + "A1234567X";

    public static final String MESSAGE_SUCCESS = "Student \"%1$s\" has been added to your student contacts";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";

    private final Student toAdd;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addStudent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatStudentName(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddStudentCommand
                && toAdd.equals(((AddStudentCommand) other).toAdd));
    }
}
