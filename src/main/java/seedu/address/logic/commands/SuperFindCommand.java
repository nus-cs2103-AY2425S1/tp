package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Represents a command for finding persons in the CampusConnect based on specified keywords.
 * <p>
 * This command can filter persons according to their names, phone numbers, emails, or tags,
 * allowing users to search through the address book efficiently. It consolidates the functionality
 * of various find commands under a single command class, enabling complex search queries with
 * multiple keywords prefixed by their respective categories.
 * </p>
 *
 * <p>
 * The command utilizes a {@code ContainsKeywordsPredicate} to determine which persons match the
 * specified criteria. If no matching persons are found, a message is returned indicating the
 * failure to find any persons that match the search.
 * </p>
 *
 * <p>
 * This command follows the command pattern and extends the {@code Command} class.
 * </p>
 */
public class SuperFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, phone numbers, emails "
            + "or tags contain any of the specified keywords (case-insensitive) and displays "
            + "them as a list with indices.\n"
            + "Parameters: PREFIX/KEYWORD [PREFIX/MORE_KEYWORDS]...\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + PREFIX_NAME + "alice " + PREFIX_PHONE + "80021234 " + PREFIX_TAG + "CS1101S";

    public static final String MESSAGE_NO_PERSONS_FOUND = "No persons found!";

    private final ContainsKeywordsPredicate predicate;

    /**
     * Constructs a {@code SuperFindCommand} with the specified predicate.
     *
     * @param predicate The predicate to filter the list of persons.
     */
    public SuperFindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = requireNonNull(predicate, "Predicate cannot be null");
    }

    /**
     * Retrieves the predicate for this command.
     *
     * @return The predicate used to filter persons.
     */
    protected ContainsKeywordsPredicate getPredicate() {
        return this.predicate;
    }

    /**
     * Executes the command, filtering the list of persons based on the predicate.
     *
     * @param model The model to operate on.
     * @return The result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model, "Model cannot be null");

        model.updateFilteredPersonList(this.predicate);

        // If the resulting filtered list is empty
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_PERSONS_FOUND);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The object to compare with this command.
     * @return True if the other object is the same type and has the same predicate; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // Check for reference equality
        }

        if (!(other instanceof SuperFindCommand)) {
            return false; // Ensure the other object is of the same type
        }

        SuperFindCommand otherCommand = (SuperFindCommand) other;
        return this.predicate.equals(otherCommand.predicate); // Compare predicates
    }

    /**
     * Returns a string representation of this command.
     *
     * @return A string representation of the command state.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.predicate)
                .toString();
    }
}
