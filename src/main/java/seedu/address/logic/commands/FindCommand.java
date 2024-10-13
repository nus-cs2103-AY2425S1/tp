package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityPredicate;

/**
 * Filters and lists all persons in address book according to the given filter parameters.
 * Keyword matching for names and addresses is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons in the address book according to the "
            + "following parameters. A combination of them can be used. Note that to specify multiple addresses, the "
            + "prefix \"" + PREFIX_ADDRESS + "\" should be used for each new address.\n"
            + "Parameters: "
            + PREFIX_NAME + "START OF NAME "
            + PREFIX_ADDRESS + "PART OF ADDRESS "
            + PREFIX_PRIORITY + "PRIORITY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jo "
            + PREFIX_ADDRESS + "Clementi ";

    private final List<String> names;
    private final List<String> addresses;
    private final List<String> priorities;

    /**
     * Creates a FindCommand to filter the address book by the given lists of
     * {@code names}, {@code addresses}, and {@code priorities}
     *
     */
    public FindCommand(List<String> names, List<String> addresses, List<String> priorities) {
        this.names = names;
        this.addresses = addresses;
        this.priorities = priorities;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> finalPredicate = person -> true; // default starting predicate

        if (!this.names.isEmpty()) {
            finalPredicate = finalPredicate.and(new NameContainsKeywordsPredicate(names));
        }

        if (!this.addresses.isEmpty()) {
            finalPredicate = finalPredicate.and(new AddressContainsKeywordsPredicate(addresses));
        }

        if (!this.priorities.isEmpty()) {
            finalPredicate = finalPredicate.and(new PriorityPredicate(priorities));
        }

        model.updateFilteredPersonList(finalPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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
        return names.equals(otherFindCommand.names)
                && addresses.equals(otherFindCommand.addresses)
                && priorities.equals(otherFindCommand.priorities);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("names", names)
                .add("addresses", addresses)
                .add("priorities", priorities)
                .toString();
    }
}
