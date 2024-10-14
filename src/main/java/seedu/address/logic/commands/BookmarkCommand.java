package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class BookmarkCommand extends Command {

    public static final String COMMAND_WORD = "bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": bookmarks the company based on the index"
            + "in the list of contacts.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    public BookmarkCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute (Model model) throws CommandException {
        return new CommandResult("bookmark");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BookmarkCommand)) {
            return false;
        }

        BookmarkCommand e = (BookmarkCommand) other;
        return index.equals(e.index);
    }
}
