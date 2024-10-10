package seedu.address.logic.commands;
import seedu.address.model.Model;

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format. "
            + "Example: " + COMMAND_WORD + " "
            + "format/csv";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Export command not implemented yet";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from remark");
    }
}
