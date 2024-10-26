package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in the address book based on given search criteria.
 * The command supports searching across various fields, such as name, attendance, and tags,
 * with case-insensitive matching of keywords.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons in the address book matching the given "
            + "keywords across specified fields. "
            + "You can search by name, address, attendance, tags, and more.\n"
            + "Name, address, and tag searches support multiple words and duplicate prefix; "
            + "other fields accept only a single word and single prefix. "
            + "Partial matching is allowed, E.g. Doing find n/dav will match a person with the name David."
            + "Search is case-insensitive, and multiple conditions are combined with logical AND.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ATTENDANCE + "DATE_RANGE (in the format of dd/MM/yy:dd/MM/yy)]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TAG + "friend "
            + PREFIX_ATTENDANCE + "24/10/2024:27/10/2024";

    private final Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code FindCommand} with a list of predicates that will be combined using logical AND,
     * using {@code reduce} method of {@code Streams} .
     *
     * @param predicates The list of predicates to apply to the search.
     * @throws NullPointerException If the provided list of predicates is {@code null}.
     */
    public FindCommand(List<Predicate<Person>> predicates) {
        requireNonNull(predicates);
        this.predicates = predicates;
        this.combinedPredicate = predicates.stream()
                .reduce(x -> true, Predicate::and); // Combine predicates with logical AND
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.combinedPredicate);
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
        return this.predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicates)
                .toString();
    }
}
