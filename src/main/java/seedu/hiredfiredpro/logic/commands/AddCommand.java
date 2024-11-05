package seedu.hiredfiredpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_INTERVIEW_SCORE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.hiredfiredpro.commons.util.ToStringBuilder;
import seedu.hiredfiredpro.logic.Messages;
import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Adds a person to the hiredfiredpro.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a candidate to HiredFiredPro. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_JOB + "JOB "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_SKILLS + "SKILLS] "
            + PREFIX_INTERVIEW_SCORE + "INTERVIEW SCORE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_JOB + "Software Engineer "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_SKILLS + "Python "
            + PREFIX_INTERVIEW_SCORE + "7.5 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New candidate added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This candidate already exists in HiredFiredPro";

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

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
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
