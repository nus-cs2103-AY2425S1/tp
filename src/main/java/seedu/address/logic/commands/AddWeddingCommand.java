package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

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
    private final Wedding toAdd;

    /**
     *  Creates an AddCommand to add the specified {@code Wedding}
     * @param wedding
     */
    public AddWeddingCommand(Wedding wedding) {
        requireNonNull(wedding);
        this.toAdd = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
