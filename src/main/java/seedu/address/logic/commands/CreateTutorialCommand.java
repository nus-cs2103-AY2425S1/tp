package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutorial.Tutorial;

/**
 * Creates/Adds a new tutorial in the address book.
 */
public class CreateTutorialCommand extends Command {

    public static final String COMMAND_WORD = "createtut";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new tutorial. \n"
            + "Parameters: "
            + PREFIX_TUTORIAL + "TUTORIAL \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL + "English";
    public static final String MESSAGE_SUCCESS_TUTORIAL = "New tutorial added.\n%1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in EduVault";
    private final Logger logger = LogsCenter.getLogger(CreateTutorialCommand.class);
    private final Tutorial toAdd;

    /**
     * Creates an CreateTutorialCommand to add the specified {@code Tutorial}
     */
    public CreateTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toAdd = tutorial;
    }

    /**
     * Executes the command to create a new tutorial. If the tutorial already exists,
     * an error will be thrown.
     *
     * @param model {@code Model} which the command operates on.
     * @return CommandResult which indicates the success of creating tutorial.
     * @throws CommandException if the tutorial already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info(" - Running execute(Model model) in " + CreateTutorialCommand.class);
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, CreateTutorialCommand.class));
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        model.createTutorial(toAdd);
        logger.info(" - Tutorial successfully created: " + toAdd.getSubject());
        return new CommandResult(String.format(MESSAGE_SUCCESS_TUTORIAL, Messages.formatTutorial(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateTutorialCommand)) {
            return false;
        }

        CreateTutorialCommand otherCreateTutorialCommand = (CreateTutorialCommand) other;
        return this.toAdd.equals(otherCreateTutorialCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Create Tutorial: ", toAdd)
                .toString();
    }
}
