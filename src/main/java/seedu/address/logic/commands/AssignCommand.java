package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

import java.util.List;

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

    private final Index index;
    private final Tutorial tutorial;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AssignCommand(Index index, Tutorial tutorial) {
        requireNonNull(index);
        this.index = index;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person student = lastShownList.get(index.getZeroBased());

        seedu.address.model.participation.Participation p = new seedu.address.model.participation.Participation(student, tutorial);
        if (tutorial.hasParticipation(p)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        tutorial.addParticipation(p);
        student.addParticipation(p);
        model.addParticipation(p);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(student), tutorial));
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
                .add("toAssign", student)
                .toString();
    }
}
