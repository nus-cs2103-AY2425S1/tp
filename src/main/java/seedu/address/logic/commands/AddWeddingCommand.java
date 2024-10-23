package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;


/**
 * List out all the wedding tags
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "add-wedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_WEDDING_NAME + "NAME & NAME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DATETIME + "DATETIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEDDING_NAME + "Jonus Ho & Izzat Syazani "
            + PREFIX_VENUE + "Pasir Ris Hotel "
            + PREFIX_DATETIME + "11/11/2024 ";

    public static final String MESSAGE_SUCCESS = "New Wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";

    private final Wedding toAdd;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}
     * @param wedding
     */
    public AddWeddingCommand(Wedding wedding) {
        requireNonNull(wedding);
        this.toAdd = wedding;
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
        if (!(other instanceof AddWeddingCommand)) {
            return false;
        }

        AddWeddingCommand otherAddWeddingCommand = (AddWeddingCommand) other;
        return toAdd.equals(otherAddWeddingCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
