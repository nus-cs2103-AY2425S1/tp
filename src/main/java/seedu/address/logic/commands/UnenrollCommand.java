package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Removes a student from a tutorial by deleting the related participation object.
 */
public class UnenrollCommand extends Command {
    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a student from a tutorial\n"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + PREFIX_TUTORIAL + "TUTORIAL \n"
            + "Example: " + COMMAND_WORD + " "
            + "2 "
            + PREFIX_TUTORIAL + "physics";
    public static final String MESSAGE_SUCCESS = "%1$s(student) no longer enrolled in %2$s(tutorial)";
    public static final String MESSAGE_NO_SUCH_PARTICIPATION = "Cannot unenroll %s from %s, as the student is "
            + "currently not enrolled in %s";

    private static final Logger logger = LogsCenter.getLogger(UnenrollCommand.class);
    private final Index index;
    private final String subject;

    /**
     * Creates a unenroll command object
     */
    public UnenrollCommand(Index index, String subject) {
        requireAllNonNull(index, subject);
        this.index = index;
        this.subject = subject.toLowerCase();
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Input model for execute cannot be null";

        Person student = getStudentFromList(model);
        Tutorial tutorial = getTutorialFromList(model);

        assert tutorial != null : "Input tutorial for participation should not be null";
        assert student != null : "Input student for participation should not be null";

        Participation participation = new Participation(student, tutorial, new ArrayList<>());
        if (!model.hasParticipation(participation)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UnenrollCommand.class));
            throw new CommandException(String.format(MESSAGE_NO_SUCH_PARTICIPATION, student.getFullName(),
                    tutorial.getSubject(),
                    tutorial.getSubject()));
        }

        model.deleteParticipation(participation);
        return new CommandResult(String.format(MESSAGE_SUCCESS, student.getFullName(), tutorial.getSubject()));
    }

    /**
     * Gets the student object from model based on input index
     * @param model Model to retrieve the student from
     * @return student object if it is in the model
     * @throws CommandException if student object is not in the model
     */
    private Person getStudentFromList(Model model) throws CommandException {
        List<Person> lastShownStudentList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownStudentList.size()) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UnenrollCommand.class));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownStudentList.get(index.getZeroBased());
    }

    /**
     * Gets the tutorial object from model based on input subject
     * @param model Model to retrieve the tutorial object
     * @return tutorial object if it is in the model
     * @throws CommandException if tutorial object is not in the model
     */
    private Tutorial getTutorialFromList(Model model) throws CommandException {
        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        return lastShownTutorialList.stream()
                .filter(tut -> tut.getSubject().equalsIgnoreCase(subject))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UnenrollCommand.class));
                    return new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_SUBJECT);
                });
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnenrollCommand)) {
            return false;
        }

        UnenrollCommand otherUnenrollCommand = (UnenrollCommand) other;
        return index.equals(otherUnenrollCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toUnenroll", index)
                .toString();
    }
}
