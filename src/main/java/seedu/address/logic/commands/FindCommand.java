package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.IncomePredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PriorityPredicate;

/**
 * Filters and lists all persons in address book according to the given filter parameters.
 * Keyword matching for names and addresses is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons in the address book according to the "
            + "given parameters. Matching is case-insensitive. A combination of the following parameters can be used.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "START_OF_NAME] "
            + "[" + PREFIX_ADDRESS + "PART_OF_ADDRESS] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_INCOME + "INCOME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jo "
            + PREFIX_ADDRESS + "Clementi | Serangoon "
            + PREFIX_PRIORITY + "HIGH "
            + PREFIX_INCOME + "1000\n"
            + "Note that the corresponding prefix must be used everytime a new filter is given.";

    private final List<String> names;
    private final List<String> addresses;
    private final List<String> priorities;
    private final List<String> incomes;

    /**
     * Creates a FindCommand to filter the address book by the given lists of
     * {@code names}, {@code addresses}, and {@code priorities}
     *
     */
    public FindCommand(List<String> names, List<String> addresses, List<String> priorities, List<String> incomes) {
        this.names = names;
        this.addresses = addresses;
        this.priorities = priorities;
        this.incomes = incomes;
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

        if (!this.incomes.isEmpty()) {
            finalPredicate = finalPredicate.and(new IncomePredicate(incomes));
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
                && priorities.equals(otherFindCommand.priorities)
                && incomes.equals(otherFindCommand.incomes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("names", names)
                .add("addresses", addresses)
                .add("priorities", priorities)
                .add("incomes", incomes)
                .toString();
    }
}
