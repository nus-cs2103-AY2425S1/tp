package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Assign a student to a tutorial by creating an tutorial object.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a student to a tutorial"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + PREFIX_TUTORIAL + "TUTORIAL "
            + "Example: " + COMMAND_WORD + " "
            + "2"
            + PREFIX_TUTORIAL + "Physics";
    public static final String MESSAGE_SUCCESS = "%1$s(student) added to %2$s(tutorial)";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the tutorial";

    private final Person student;
    private final Tutorial tutorial;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AssignCommand(Person person, Tutorial tutorial) {
        requireNonNull(person);
        this.student = person;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        seedu.address.model.participation.Participation p = new seedu.address.model.participation.Participation(student, tutorial);
        if (tutorial.hasParticipation(p)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        tutorial.addParticipation(p);
        student.addParticipation(p);
        // model.addParticipation(p);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(student)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return student.equals(otherAssignCommand.student);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", student)
                .toString();
    }
}
