package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.lesson.Lesson;

/**
 * Deletes a lesson from the lesson list.
 */
public class DeleteLessonCommand extends LessonCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String DELETE_COMMAND_STRING_FORMAT = "DeleteLessonCommand"
            + "{targetIndex=tutorease.address.commons.core."
            + "index.Index{zeroBasedIndex=%d}}";

    public static final String MESSAGE_USAGE = LessonCommand.COMMAND_WORD
            + " " + COMMAND_WORD + ": Deletes a lesson from the lesson list. "
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_SUCCESS = "Lesson removed successfully: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The lesson index provided is invalid. "
            + "Please key in an index that is on the lesson panel";

    private final Index targetIndex;

    /**
     * Creates an DeleteLessonCommand to delete the lesson at a specified index.
     *
     * @param targetIndex     The index of the lesson to be deleted. Must not be null
     */
    public DeleteLessonCommand(Index targetIndex) {
        if (targetIndex == null) {
            throw new NullPointerException("Index cannot be null");
        }
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int listIndex = targetIndex.getZeroBased();

        if (listIndex >= model.getFilteredLessonList().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Lesson lesson = model.getFilteredLessonList().get(listIndex);
        model.deleteLesson(lesson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, lesson));
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

        DeleteLessonCommand otherDeleteLessonCommand = (DeleteLessonCommand) other;
        return targetIndex.equals(otherDeleteLessonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return String.format(DELETE_COMMAND_STRING_FORMAT, targetIndex.getZeroBased());
    }
}
