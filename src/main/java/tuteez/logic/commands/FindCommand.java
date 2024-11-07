package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_DAY;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.model.Model;
import tuteez.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, addresses, tags, "
            + "lessons days contain any of the specified keywords (case-insensitive) or whose lesson times overlaps "
            + "with the inputted time range.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME_KEYWORDS...] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS...] "
            + "[" + PREFIX_TAG + "TAG_KEYWORDS...] "
            + "[" + PREFIX_LESSON_DAY + "LESSON_DAY_KEYWORDS...] "
            + "[" + PREFIX_LESSON_TIME + "LESSON_TIME_KEYWORDS...]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice charlie " + PREFIX_ADDRESS + "jurong";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final Predicate<Person> combinedPredicate;

    /**
     * Creates a FindCommand to find persons with the specified {@code NameContainsKeywordsPredicate}.
     */
    public FindCommand(Predicate<Person> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(combinedPredicate);
        int size = model.getFilteredPersonList().size();

        String logMessage = String.format("Find command execution completed. Found %d matching %s",
                size, size == 1 ? "result" : "results");
        logger.info(logMessage);
        if (size == 0) {
            logger.info("No matching results found.");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return combinedPredicate.equals(otherFindCommand.combinedPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", combinedPredicate)
                .toString();
    }

    @Override
    public int hashCode() {
        return combinedPredicate.hashCode();
    }
}
