package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutorial.Tutorial;

public class CreateTutorialCommand extends Command {

    public static final String COMMAND_WORD = "createtut";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new tutorial. "
            + "Parameters: "
            + PREFIX_SUBJECT + "English";
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        model.addTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS_TUTORIAL, Messages.formatTutorial(toAdd)));
    }
}
