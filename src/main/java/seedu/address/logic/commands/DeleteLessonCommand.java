package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
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
            + "Example: " + COMMAND_WORD + " 1 3" + " " + PREFIX_SUBJECT + "Math";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    public static final String MESSAGE_INVALID_TUTOR_INDEX = "The person index provided is not a Tutor";

    public static final String MESSAGE_INVALID_TUTEE_INDEX = "The person index provided is not a Tutee";

    public static final String MESSAGE_INVALID_SUBJECT = "The tutor, tutee and the lesson to be deleted "
            + "must have the same subject";

    public static final String MESSAGE_INVALID_LESSON = "The lesson to be deleted does not exist in the address book";

    private final Index tutorIndex;

    private final Index tuteeIndex;

    private final Subject subject;

    /**
     * Creates a DeleteLesson Command to delete the Lesson asssociated with
     * the specified {@code Tutor} and {@code Tutee}.
     * @param tutorIndex
     * @param tuteeIndex
     */
    public DeleteLessonCommand(Index tutorIndex, Index tuteeIndex, Subject subject) {
        this.tutorIndex = tutorIndex;
        this.tuteeIndex = tuteeIndex;
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (tutorIndex.getZeroBased() >= lastShownList.size() || tuteeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //ToDo
        // check if the lesson to be deleted is valid by checking if the tutor and tutee are
        // associated with a Lesson with the correct subject

        Person tutorToDelete = lastShownList.get(tutorIndex.getZeroBased());
        Person tuteeToDelete = lastShownList.get(tuteeIndex.getZeroBased());

        // check if the tutor, tutee and the lesson to be added have the same subject
        if (!tutorToDelete.hasSubject(this.subject) || !tuteeToDelete.hasSubject(this.subject)) {
            throw new CommandException(MESSAGE_INVALID_SUBJECT);
        }

        if (!(tutorToDelete instanceof Tutor)) {
            throw new CommandException(MESSAGE_INVALID_TUTOR_INDEX);
        } else if (!(tuteeToDelete instanceof Tutee)) {
            throw new CommandException(MESSAGE_INVALID_TUTEE_INDEX);
        }
        Lesson lessonToDelete = new Lesson((Tutor) tutorToDelete, (Tutee) tuteeToDelete, subject);
        if (!model.hasLesson(lessonToDelete)) {
            throw new CommandException(MESSAGE_INVALID_LESSON);
        }

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
