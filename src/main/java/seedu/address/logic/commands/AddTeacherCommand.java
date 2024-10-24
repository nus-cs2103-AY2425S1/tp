package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Teacher;

/**
 * Adds a teacher to the address book.
 */
public class AddTeacherCommand extends Command {

    public static final String COMMAND_WORD = "teacher";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a teacher to the address book. "
        + "Parameters: "
        + PREFIX_NAME + " NAME "
        + PREFIX_GENDER + " GENDER "
        + PREFIX_PHONE + " PHONE "
        + PREFIX_EMAIL + " EMAIL "
        + PREFIX_ADDRESS + " ADDRESS "
        + PREFIX_SUBJECT + " SUBJECT "
        + PREFIX_CLASSES + " CLASSES "
        + "\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + " John Doe "
        + PREFIX_GENDER + " male "
        + PREFIX_PHONE + " 98765432 "
        + PREFIX_EMAIL + " johnd@example.com "
        + PREFIX_ADDRESS + " 311, Clementi Ave 2, #02-25 "
        + PREFIX_SUBJECT + " Physics "
        + PREFIX_CLASSES + " 7A,7B ";

    public static final String MESSAGE_SUCCESS = "New teacher added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEACHER = "This teacher already exists in the address book";

    private final Teacher toAdd;

    /**
     * Creates an AddTeacherCommand to add the specified {@code Teacher}
     */
    public AddTeacherCommand(Teacher teacher) {
        requireNonNull(teacher);
        toAdd = teacher;
    }

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEACHER);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTeacherCommand)) {
            return false;
        }

        AddTeacherCommand otherAddTeacherCommand = (AddTeacherCommand) other;
        return toAdd.equals(otherAddTeacherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
