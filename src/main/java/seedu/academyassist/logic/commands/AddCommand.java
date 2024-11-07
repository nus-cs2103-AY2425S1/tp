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
            + "Format: " + COMMAND_WORD + " " + PREFIX_NAME + "NAME " + PREFIX_IC + "NRIC "
            + PREFIX_YEARGROUP + "YEARGROUP " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS " + PREFIX_SUBJECT + "SUBJECT " + "[" + PREFIX_SUBJECT + "MORE_SUBJECTS]...\n"
            + "Parameters Example: \n"
            + "- NAME: Sam Tan\n"
            + "- NRIC: S1234567A\n"
            + "- YEARGROUP: 3\n"
            + "- PHONE: 12345678\n"
            + "- EMAIL: samtan@gmail.com\n"
            + "- ADDRESS: 9 Smith Street\n"
            + "- SUBJECT: Science";

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
