package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Exports a filtered list of goods
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports goods shown in view goods";

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
