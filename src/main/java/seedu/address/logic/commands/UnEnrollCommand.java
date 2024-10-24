package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Assign a student to a tutorial by creating an tutorial object.
 */
public class UnEnrollCommand extends Command {

    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a student to a tutorial"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + PREFIX_TUTORIAL + "TUTORIAL "
            + "Example: " + COMMAND_WORD + " "
            + "2"
            + PREFIX_TUTORIAL + "physics";
    public static final String MESSAGE_SUCCESS = "%1$s(student) removed from %2$s(tutorial)";
    public static final String MESSAGE_NO_SUCH_PARTICIPATION = "This person are not in this tutorial currently";

    private final Index index;
    private final String subject;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public UnEnrollCommand(Index index, String subject) {
        requireNonNull(index);
        requireNonNull(subject);
        this.index = index;
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownStudentList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person student = lastShownStudentList.get(index.getZeroBased());

        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        Optional<Tutorial> optionalTutorial = lastShownTutorialList.stream()
                .filter(tut -> tut.getSubject().equals(subject))
                .findFirst();
        if (optionalTutorial.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_SUBJECT);
        }
        Tutorial tutorial = optionalTutorial.get();

        Participation p = new seedu.address.model.participation.Participation(student, tutorial, new ArrayList<>());
        if (!(tutorial.hasParticipation(p) || student.hasParticipation(p) || model.hasParticipation(p))) {
            throw new CommandException(MESSAGE_NO_SUCH_PARTICIPATION);
        }

        tutorial.removeParticipation(p);
        student.removeParticipation(p);
        model.deleteParticipation(p);
        model.setPerson(student, student);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, student, tutorial));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnEnrollCommand)) {
            return false;
        }

        UnEnrollCommand otherUnEnrollCommand = (UnEnrollCommand) other;
        return index.equals(otherUnEnrollCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toUnenroll", index)
                .toString();
    }
}
