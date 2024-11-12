package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.TutorEase;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private static Logger logger = LogsCenter.getLogger(ClearCommand.class);


    @Override
    public CommandResult execute(Model model) {
        logger.log(Level.INFO, "Executing ClearCommand");
        requireNonNull(model);
        model.setTutorEase(new TutorEase());
        model.setLessonSchedule(new LessonSchedule());
        logger.log(Level.INFO, MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
