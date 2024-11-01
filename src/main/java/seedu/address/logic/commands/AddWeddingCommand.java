package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * List out all the wedding tags
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "add-wed";

    public static final String COMMAND_FUNCTION = COMMAND_WORD + ": Adds a wedding to the address book. ";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: "
            + PREFIX_WEDDING_NAME + "NAME & NAME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEDDING_NAME + "James Hauw & Rachel Loh "
            + PREFIX_VENUE + "Pan Pacific Hotel "
            + PREFIX_DATE + "11/03/2025 ";

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

        // Check if the wedding involves the same person twice aka marrying oneself
        String[] names = toAdd.getName().split("&");
        if (names.length == 2 && names[0].trim().equalsIgnoreCase(names[1].trim())) {
            throw new CommandException("A wedding cannot involve marrying oneself");
        }

        // Check if a wedding with the same two people already exists, regardless of the order
        Set<String> toAddNamesSet = new HashSet<>(Arrays.asList(names[0].trim().toLowerCase(),
                                                                names[1].trim().toLowerCase()));

        boolean hasDuplicate = model.getWeddingBook().getWeddingList().stream()
                .map(existingWedding -> {
                    String[] existingNames = existingWedding.getName().split("&");
                    if (existingNames.length != 2) {
                        return null;
                    }
                    return new HashSet<>(Arrays.asList(existingNames[0].trim().toLowerCase(),
                            existingNames[1].trim().toLowerCase()));
                })
                .anyMatch(existingNamesSet -> toAddNamesSet.equals(existingNamesSet));

        if (hasDuplicate) {
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
