package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a wedding to the address book.
 */
public class CreateWeddingCommand extends Command {

    public static final String COMMAND_WORD = "create-wedding";

    public static final String COMMAND_KEYWORD = "cw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a wedding in the address book. "
            + "Wedding names are case insensitive.\n"
            + "Parameters: "
            + PREFIX_WEDDING + "WEDDING\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEDDING + "Cheryl's Wedding";

    private final Wedding weddingToAdd;

    /**
     * Creates a CreateWeddingCommand to add the specified {@code Wedding} to the Wedlinker
     * @param wedding The {@code Wedding} to be added to the Wedlinker
     */
    public CreateWeddingCommand(Wedding wedding) {
        requireNonNull(wedding);
        weddingToAdd = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(weddingToAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WEDDING);
        }

        model.addWedding(weddingToAdd);
        return new CommandResult(String.format(Messages.MESSAGE_CREATE_WEDDING_SUCCESS, Messages.format(weddingToAdd)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // null case handled by instanceof
        if (!(obj instanceof CreateWeddingCommand)) {
            return false;
        }

        CreateWeddingCommand otherCreateWeddingCommand = (CreateWeddingCommand) obj;
        return weddingToAdd.equals(otherCreateWeddingCommand.weddingToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddingToAdd", weddingToAdd)
                .toString();
    }
}
