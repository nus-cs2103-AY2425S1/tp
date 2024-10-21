package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.nio.file.Path;

public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_PATH + "FileName\n"
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
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
