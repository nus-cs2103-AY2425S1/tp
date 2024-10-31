package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_DELIMITER;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Deletes lessons identified by indices in TAHub.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deletelesson";
    public static final CommandType COMMAND_TYPE = CommandType.DELETELESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson(s) identified by the index number(s) used in the displayed lesson list.\n"
            + "Parameters: INDEX (must be a positive integer) [;INDEX...]\n"
            + "Example: " + COMMAND_WORD + " 1" + DEFAULT_DELIMITER + "2";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson(s):\n%1$s";

    private final Set<Index> targetIndices;

    public DeleteLessonCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    /**
     * Returns Command Type DELETELESSON
     *
     * @return Command Type DELETELESSON
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lessons = model.getFilteredLessonList();

        boolean throwException = false;
        ArrayList<Index> outOfBounds = new ArrayList<>();

        for (Index item : targetIndices) {
            if (item.getZeroBased() >= lessons.size()) {
                throwException = true;
                outOfBounds.add(item);
            }
        }

        if (throwException) {
            String formattedOutOfBoundIndices = outOfBounds.stream()
                    .map(index -> String.valueOf(index.getOneBased()))
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
        }

        List<Lesson> deletedLessons = targetIndices.stream()
                .map(targetIndex -> lessons.get(targetIndex.getZeroBased()))
                .toList();

        deletedLessons.forEach(model::deleteLesson);

        String formattedDeletedLessons = deletedLessons.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        return new CommandResult(
                String.format(MESSAGE_DELETE_LESSON_SUCCESS, formattedDeletedLessons),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteLessonCommand
                && targetIndices.equals(((DeleteLessonCommand) other).targetIndices));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
