package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deleteLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson between a tutor and tutee, identified by their respective index numbers "
            + "in the displayed person list.\n"
            + "Parameters: TUTORINDEX TUTEEINDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    private final Index tutorIndex;

    private final Index tuteeIndex;

    /**
     * Creates a DeleteLesson Command to delete the Lesson asssociated with
     * the specified {@code Tutor} and {@code Tutee}.
     * @param tutorIndex
     * @param tuteeIndex
     */
    public DeleteLessonCommand(Index tutorIndex, Index tuteeIndex) {
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

        Tutor tutorLessonToDelete = (Tutor) lastShownList.get(tutorIndex.getZeroBased());
        Tutee tuteeLessonToDelete = (Tutee) lastShownList.get(tuteeIndex.getZeroBased());
        Lesson lessonToDelete = new Lesson(tutorLessonToDelete, tuteeLessonToDelete);

        model.deleteLesson(lessonToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, Messages.format(lessonToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        DeleteLessonCommand otherDeleteCommand = (DeleteLessonCommand) other;
        return tutorIndex.equals(otherDeleteCommand.tutorIndex)
                && tuteeIndex.equals(otherDeleteCommand.tuteeIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorIndex", tutorIndex)
                .add("tuteeIndex", tuteeIndex)
                .toString();
    }
}
