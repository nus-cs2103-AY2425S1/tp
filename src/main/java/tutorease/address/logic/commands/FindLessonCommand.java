package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.logic.Messages;
import tutorease.address.model.Model;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;

/**
 * Finds and lists all lessons in address book whose student's name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindLessonCommand extends LessonCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = LessonCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Finds all lessons whose students' names "
            + "match the specified names (case-insensitive) and displays them in the lesson panel. \n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + LessonCommand.COMMAND_WORD + " " + COMMAND_WORD + " alice bob charlie";

    // Get the logger specific to FindLessonCommand
    private static final Logger logger = LogsCenter.getLogger(FindLessonCommand.class);

    private final LessonContainsNamesPredicate predicate;

    public FindLessonCommand(LessonContainsNamesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Log the start of the command execution at INFO level
        logger.info("Executing FindLessonCommand with predicate: " + predicate);

        model.updateFilteredLessonList(predicate);
        if (model.getFilteredLessonList().isEmpty()) {
            // Log that no lessons are found at INFO level
            logger.info("No lessons found for the given predicate.");
            return new CommandResult(Messages.MESSAGE_NO_LESSONS_FOUND);
        }

        // Log the number of lessons found at INFO level
        logger.info("Found " + model.getFilteredLessonList().size() + " lessons.");

        return new CommandResult(
                String.format(Messages.MESSAGE_LESSONS_LISTED_OVERVIEW,
                        model.getFilteredLessonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindLessonCommand)) {
            return false;
        }

        FindLessonCommand otherFindLessonCommand = (FindLessonCommand) other;
        return predicate.equals(otherFindLessonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
