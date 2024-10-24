package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;


/**
 * Adds a wedding to the Address Book.
 */
public class AddwCommand extends Command {
    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "WEDDING'S NAME "
            + PREFIX_CLIENT + "CLIENT"
            + PREFIX_DATE + "DATE "
            + PREFIX_VENUE + "VENUE ";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";
    private final Wedding toAdd;

    /**
     * Creates an AddwCommand to add the specified {@code Wedding}
     */
    public AddwCommand(Wedding wedding) {
        requireNonNull(wedding);
        toAdd = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.addWedding(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddwCommand)) {
            return false;
        }

        AddwCommand otherAddwCommand = (AddwCommand) other;
        return toAdd.equals(otherAddwCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddw", toAdd)
                .toString();
    }
}
