package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.shortcut.ShortCut;
import seedu.address.model.tag.Tag;

/**
 * AddShortCutCommand adds a shortcut to applying tags
 */
public class AddShortCutCommand extends Command {
    public static final String COMMAND_WORD = "addShortCut";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a shortcut when tagging.\n"
            + "Usage: addShortCut al/v tn/Vegan";
    public static final String MESSAGE_SUCCESS = "New Shortcut added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "ShortCut with the same alias already exists";
    public static final String MESSAGE_DUPLICATE_FULLTAGNAME = "ShortCut with the same tag name already exists";
    private ShortCut toAdd;

    /**
     * Creates a shortcut command to add the specified {@code Shortcut}
     */
    public AddShortCutCommand(ShortCut shortcut) {
        this.toAdd = shortcut;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddShortCutCommand)) {
            return false;
        }

        AddShortCutCommand otherShortTagCommand = (AddShortCutCommand) other;
        return toAdd.equals(otherShortTagCommand.toAdd);
    }

    @Override
    public String toString() {
        return toAdd.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasAlias(toAdd.getAlias())) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }
        if (model.hasFullTagName(toAdd.getFullTagName())) {
            throw new CommandException(MESSAGE_DUPLICATE_FULLTAGNAME);
        }
        model.addShortCut(toAdd);
        Tag.updateShortCutMappings(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
