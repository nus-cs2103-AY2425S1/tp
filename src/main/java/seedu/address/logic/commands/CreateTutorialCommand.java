package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

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
            + PREFIX_TUTORIAL + "English \t"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL + "English";
    public static final String MESSAGE_SUCCESS_TUTORIAL = "New tutorial added: %1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in the address book";
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
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        model.createTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS_TUTORIAL, Messages.formatTutorial(toAdd)));
    }
}
