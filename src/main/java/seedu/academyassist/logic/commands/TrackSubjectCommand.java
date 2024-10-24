package seedu.academyassist.logic.commands;

import seedu.academyassist.model.Model;

/**
 * Shows the number of students taking each subject.
 */
public class TrackSubjectCommand extends Command {

    public static final String COMMAND_WORD = "tracksubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the number of students taking each subject.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SUBJECT_TRACKER_MESSAGE = "Opened subject tracker window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SUBJECT_TRACKER_MESSAGE, false, false, true);
    }
}
