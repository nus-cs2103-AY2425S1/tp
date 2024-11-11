package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
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
            + " " + COMMAND_WORD + ": Deletes a lesson from the lesson list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + LessonCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Lesson removed successfully: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The lesson index provided is invalid. "
            + "Please key in an index that is on the lesson panel";

    private static final Logger logger = LogsCenter.getLogger(DeleteLessonCommand.class);
    private final Index targetIndex;

    /**
     * Creates an DeleteLessonCommand to delete the lesson at a specified index.
     *
     * @param targetIndex The index of the lesson to be deleted. Must not be null.
     */
    public DeleteLessonCommand(Index targetIndex) {
        if (targetIndex == null) {
            throw new NullPointerException("Index cannot be null");
        }
        assert targetIndex != null : "targetIndex cannot be null";
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing DeleteLessonCommand");
        requireNonNull(model);

        int listIndex = targetIndex.getZeroBased();

        if (listIndex >= model.getFilteredLessonListSize()) {
            logger.log(Level.WARNING, "Invalid target index: {0}", targetIndex.getZeroBased());
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        assert listIndex < model.getFilteredLessonListSize() : "listIndex cannot be out of range";
        Lesson lesson = model.getFilteredLesson(listIndex);
        model.deleteLesson(lesson);
        logger.log(Level.INFO, String.format(MESSAGE_SUCCESS, lesson));
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
