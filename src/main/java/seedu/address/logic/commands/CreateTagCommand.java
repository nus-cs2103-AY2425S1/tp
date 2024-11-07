package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to the address book.
 */
public class CreateTagCommand extends Command {

    public static final String COMMAND_WORD = "create-tag";

    public static final String COMMAND_KEYWORD = "ctag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a tag in the address book. \n"
            + "Parameters: "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "florist";

    private final Tag toAdd;

    /**
     * Creates an CreateTagCommand to add the specified {@code Tag}
     */
    public CreateTagCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(toAdd);
        return new CommandResult(String.format(Messages.MESSAGE_CREATE_TAG_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateTagCommand)) {
            return false;
        }

        CreateTagCommand otherCreateTagCommand = (CreateTagCommand) other;
        return toAdd.equals(otherCreateTagCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
