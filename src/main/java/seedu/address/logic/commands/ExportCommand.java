package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Exports a filtered list of goods
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports goods shown on the screen to filteredGoods.csv";

    public static final String MESSAGE_SUCCESS = "Filtered goods file created/updated";

    /**
     * Creates an ExportFilterGoodsCommand to export filtered goods
     */
    public ExportCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setExportFilterGoodsToTrue();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
