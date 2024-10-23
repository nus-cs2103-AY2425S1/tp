package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a wedding to the address book.
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Wedding1 " + PREFIX_DATE + "12/12/2024";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";

    private final Wedding toAdd;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}
     */
    public AddWeddingCommand(Wedding wedding) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatWedding(toAdd)));
    }

    public Wedding getWedding() {
        return toAdd;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddWeddingCommand
                && toAdd.equals(((AddWeddingCommand) other).toAdd));
    }
}
