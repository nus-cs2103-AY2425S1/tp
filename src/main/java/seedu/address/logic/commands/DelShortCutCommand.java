package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.shortcut.ShortCut;
import seedu.address.model.tag.Tag;

/**
 * AddShortCutCommand adds a shortcut to tags
 */
public class DelShortCutCommand extends Command {
    public static final String COMMAND_WORD = "delShortCut";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a preassigned shortcut.\n"
            + "Usage: delShortCut al/v tn/Vegan";
    public static final String MESSAGE_SUCCESS = "Shortcut Deleted: %1$s";
    public static final String MESSAGE_SHORTCUT_NOT_FOUND = "Shortcut not found";
    private ShortCut toRemove;
    public DelShortCutCommand(ShortCut shortcut) {
        this.toRemove = shortcut;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DelShortCutCommand)) {
            return false;
        }

        DelShortCutCommand otherDelShortCutCommand = (DelShortCutCommand) other;
        return toRemove.equals(otherDelShortCutCommand.toRemove);
    }

    @Override
    public String toString() {
        return toRemove.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasShortCut(toRemove)) {
            throw new CommandException(MESSAGE_SHORTCUT_NOT_FOUND);
        }
        model.removeShortCut(toRemove);
        Tag.updateShortCutMappings(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
    }
}
