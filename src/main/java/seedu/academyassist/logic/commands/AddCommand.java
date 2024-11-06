package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_YEARGROUP;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;

/**
 * Adds a person to the management system.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the system.\n"
            + "Parameters: \n"
            + PREFIX_NAME + "NAME  e.g. John Smith\n"
            + PREFIX_PHONE + "PHONE  e.g. 98382673\n"
            + PREFIX_IC + "NRIC  e.g. S9238263A\n"
            + PREFIX_YEARGROUP + "YEARGROUP  e.g. 1\n"
            + PREFIX_EMAIL + "EMAIL  e.g. john.smith@example.com\n"
            + PREFIX_ADDRESS + "ADDRESS  e.g. 311, Clementi Ave 2, #02-25\n"
            + PREFIX_SUBJECT + "SUBJECT  e.g. English, s/Science" + "\n";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPersonWithIc(toAdd.getIc())) {
            throw new CommandException(MESSAGE_DUPLICATE_IC);
        }

        model.incrementIdGeneratedCount();
        Person toAddWithId = toAdd.assignStudentId(model.getIdGeneratedCount());
        model.addPerson(toAddWithId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAddWithId)));
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
