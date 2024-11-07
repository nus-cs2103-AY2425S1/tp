package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * List out all the wedding tags
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "add-wed";

    public static final String COMMAND_WORD_SHORT = "aw";

    public static final String COMMAND_FUNCTION = COMMAND_WORD_SHORT + ": Adds a wedding to the address book. ";

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
     * Creates an AddWeddingCommand to add the specified {@code Wedding}.
     * @param wedding {@code Wedding} to be added.
     */
    public AddWeddingCommand(Wedding wedding) {
        requireNonNull(wedding);
        this.toAdd = wedding;
    }

    /**
     * Executes the command and returns the result message.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If the wedding already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Normalize, trim, and capitalize the names
        String[] names = toAdd.getName().split("&");
        if (names.length == 2) {
            names[0] = StringUtil.capitaliseString(names[0].trim().toLowerCase());
            names[1] = StringUtil.capitaliseString(names[1].trim().toLowerCase());
        }

        // Check if the wedding involves the same person twice aka marrying oneself
        if (names.length == 2 && names[0].equals(names[1])) {
            throw new CommandException("A wedding cannot involve marrying oneself");
        }

        // Check if the wedding already exists in the address book
        Set<Set<String>> existingWeddingsNamesSet = getSets(model);

        Set<String> toAddNamesSet = new HashSet<>(Arrays.asList(names[0], names[1]));

        if (existingWeddingsNamesSet.contains(toAddNamesSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        // Create a new Wedding object with normalized and capitalized names
        Wedding normalizedWedding = new Wedding(new WeddingName(names[0] + " & " + names[1]),
                toAdd.getVenue(), toAdd.getDate());

        model.addWedding(normalizedWedding);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(normalizedWedding)));
    }

    /**
     * Returns a set of sets of names of existing weddings in the address book.
     * @param model {@code Model} which the command should operate on.
     * @return a set of sets of names of existing weddings in the address book.
     */
    private static Set<Set<String>> getSets(Model model) {
        Set<Set<String>> existingWeddingsNamesSet = new HashSet<>();

        for (Wedding existingWedding : model.getWeddingBook().getWeddingList()) {
            String[] existingNames = existingWedding.getName().split("&");
            if (existingNames.length == 2) {
                Set<String> existingNamesSet = new HashSet<>(Arrays.asList(existingNames[0].trim().toLowerCase(),
                        existingNames[1].trim().toLowerCase()));
                existingWeddingsNamesSet.add(existingNamesSet);
            }
        }
        return existingWeddingsNamesSet;
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
