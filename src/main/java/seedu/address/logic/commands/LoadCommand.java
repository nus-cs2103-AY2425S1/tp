package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * This command is to load the model with a file at the path
 * */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_PATH + "FileName\n"
            + "The file name should be a path of an json file containing an address book "
            + "and not contain any slash `/` \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "mybook.json";
    public static final String MESSAGE_SUCCESS = "Loading file from: %1$s";

    private Path loadPath;

    public LoadCommand(Path loadPath) {
        this.loadPath = loadPath;
    }

    public Path getLoadPath() {
        return loadPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(String.format(MESSAGE_SUCCESS, loadPath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoadCommand)) {
            return false;
        }

        LoadCommand otherLoadCommand = (LoadCommand) other;
        return loadPath.equals(otherLoadCommand.loadPath);
    }
}
