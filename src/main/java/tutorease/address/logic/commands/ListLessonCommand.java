package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;

/**
 * Lists all lessons in the lesson list.
 */
public class ListLessonCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all lessons.";
    public static final String MESSAGE_NO_LESSONS_FOUND = "No lessons found.";
    private static Logger logger = LogsCenter.getLogger(ListLessonCommand.class);
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing ListLessonCommand");
        requireNonNull(model);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return getCommandResult(model);
    }

    private static CommandResult getCommandResult(Model model) {
        if (model.filteredLessonListIsEmpty()) {
            logger.log(Level.INFO, "No lessons found for the given predicate.");
            return new CommandResult(MESSAGE_NO_LESSONS_FOUND);
        }
        logger.log(Level.INFO, "Found " + model.getFilteredPersonListSize() + " lessons.");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
