package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Exports addressbook.json into user's local device.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_EXPORT_ACKNOWLEDGEMENT = "Exporting addressbook.json under file path ...";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_EXPORT_ACKNOWLEDGEMENT);
    }

}

