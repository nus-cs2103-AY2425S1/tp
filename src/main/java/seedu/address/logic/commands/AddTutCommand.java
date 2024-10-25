package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUT_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tut.Tutorial;

/**
 * Adds a tutorial to the address book.
 */
public class AddTutCommand extends Command {

    public static final String COMMAND_WORD = "addTut";
    public static final String MESSAGE_SUCCESS = "New tutorial added: %1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in the address book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial to the address book. "
            + "Parameters: "
            + PREFIX_TUT_NAME + "TUTORIAL_NAME "
            + PREFIX_TUT_ID + "id\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUT_NAME + "CS1010 "
            + PREFIX_TUT_ID + "T1001";
    private final Tutorial toAdd;

    /**
     * Creates an AddTutCommand to add the specified {@code Tut}
     */
    public AddTutCommand(Tutorial tutorial) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTutCommand)) {
            return false;
        }

        AddTutCommand otherAddTutCommand = (AddTutCommand) other;
        return toAdd.equals(otherAddTutCommand.toAdd);
    }

    @Override
    public String toString() {
        return String.format("%s{toAdd=%s}", AddTutCommand.class.getCanonicalName(), toAdd);
    }
}
