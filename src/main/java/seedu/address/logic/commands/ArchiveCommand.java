package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the address book.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Address book has been archived successfully!";
    public static final String MESSAGE_FAILURE = "Address book failed to be archived. Please try again later.";

    private final String filename;

    public ArchiveCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        try {
            model.archiveAddressBook(filename);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
