package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.tutorial.Tutorial;

/**
 * Closes a tutorial in the system by unenrolling all students and marking the tutorial as closed.
 */
public class CloseTutorialCommand extends Command {

    public static final String COMMAND_WORD = "closetut";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Closes the tutorial identified by the tutorial name.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL + "TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL + "Chemistry";

    public static final String MESSAGE_CLOSE_TUTORIAL_SUCCESS = "Successfully closed tutorial.\n%1$s";
    private final Logger logger = LogsCenter.getLogger(CloseTutorialCommand.class);

    private final Tutorial toCloseTutorial;

    /**
     * Constructs a {@code CloseTutorialCommand} with the specified tutorial to close.
     *
     * @param tutorial The tutorial to close.
     */
    public CloseTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toCloseTutorial = tutorial;
    }

    /**
     * Executes the command to close a tutorial. If the tutorial exists, all related participation will be deleted,
     * through looping through the entire participation list, and the tutorial will be marked as closed.
     *
     * @param model {@code Model} which the command operates on.
     * @return CommandResult which indicates the success of the tutorial closure.
     * @throws CommandException if the tutorial does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info(" - Running execute(Model model) in " + CloseTutorialCommand.class);
        requireNonNull(model);

        if (!model.hasTutorial(toCloseTutorial)) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, CloseTutorialCommand.class));
            throw new CommandException(String.format(MESSAGE_TUTORIAL_NOT_FOUND, toCloseTutorial.getSubject()));
        }

        List<Tutorial> fullTutorialList = model.getTutorialList();
        Tutorial tutorialToCloseFromList = fullTutorialList.stream()
                .filter(eachTutorial -> eachTutorial.equals(toCloseTutorial))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TUTORIAL_NOT_FOUND, toCloseTutorial.getSubject())));

        List<Participation> allParticipationsCopy = new ArrayList<>(model.getParticipationList());
        for (Participation eachParticipation: allParticipationsCopy) {
            if (eachParticipation.getTutorial().equals(tutorialToCloseFromList)) {
                model.deleteParticipation(eachParticipation);
            }
        }
        model.deleteTutorial(tutorialToCloseFromList);
        logger.info(" - Tutorial successfully closed: " + toCloseTutorial.getSubject());

        return new CommandResult(String.format(
                MESSAGE_CLOSE_TUTORIAL_SUCCESS,
                Messages.formatTutorial(tutorialToCloseFromList))
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CloseTutorialCommand)) {
            return false;
        }

        CloseTutorialCommand otherCloseTutorialCommand = (CloseTutorialCommand) other;
        return this.toCloseTutorial.equals(otherCloseTutorialCommand.toCloseTutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Close Tutorial: ", toCloseTutorial)
                .toString();
    }
}
