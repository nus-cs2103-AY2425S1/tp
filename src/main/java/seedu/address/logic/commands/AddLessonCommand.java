package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Adds a Lesson to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a lesson taught by the tutor to the tutee identified by their respective index numbers "
            + "in the displayed persons list.\n"
            + "Parameters: TUTORINDEX TUTEEINDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3";

    public static final String MESSAGE_ADD_LESSON_SUCCESS = "Added Lesson: %1$s";

    public static final String MESSAGE_INVALID_TUTOR_INDEX = "The person index provided is not a Tutor";

    public static final String MESSAGE_INVALID_TUTEE_INDEX = "The person index provided is not a Tutee";

    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book";

    private final Index tutorIndex;

    private final Index tuteeIndex;

    /**
     * @param tutorIndex Index of the tutor
     * @param tuteeIndex Index of the tutee
     */
    public AddLessonCommand(Index tutorIndex, Index tuteeIndex) {
        this.tutorIndex = tutorIndex;
        this.tuteeIndex = tuteeIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (tutorIndex.getZeroBased() >= lastShownList.size() || tuteeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person tutorToAdd = lastShownList.get(tutorIndex.getZeroBased());
        Person tuteeToAdd = lastShownList.get(tuteeIndex.getZeroBased());

        if (!(tutorToAdd instanceof Tutor)) {
            throw new CommandException(MESSAGE_INVALID_TUTOR_INDEX);
        } else if (!(tuteeToAdd instanceof Tutee)) {
            throw new CommandException(MESSAGE_INVALID_TUTEE_INDEX);
        }

        Lesson lesson = new Lesson((Tutor) tutorToAdd, (Tutee) tuteeToAdd);

        if (model.hasLesson(lesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(lesson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_LESSON_SUCCESS, Messages.format(lesson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand otherAddLessonCommand)) {
            return false;
        }

        return Objects.equals(tutorIndex, otherAddLessonCommand.tutorIndex)
                && Objects.equals(tuteeIndex, otherAddLessonCommand.tuteeIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorIndex", tutorIndex)
                .add("tuteeIndex", tuteeIndex)
                .toString();
    }
}
