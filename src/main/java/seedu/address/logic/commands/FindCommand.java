package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.PredicateAdapter;

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
    private final List<Predicate<Person>> personPredicates;
    private final List<Predicate<Participation>> participationPredicates;

    /**
     * Constructs a {@code FindCommand} with specified predicates for filtering {@code Person}
     * and {@code Participation} objects.
     *
     * <p>This command uses the provided {@code personPredicates} to filter {@code Person} objects
     * and the {@code participationPredicates} to filter {@code Participation} records related with those persons.</p>
     *
     * @param personPredicates A list of {@code Predicate<Person>} for filtering persons based on specific conditions.
     *                         Must not be {@code null}.
     * @param participationPredicates A list of {@code Predicate<Participation>} for filtering participation records
     *                                associated with each person. Must not be {@code null}.
     * @throws NullPointerException if {@code personPredicates} or {@code participationPredicates} is {@code null}.
     */
    public FindCommand(List<Predicate<Person>> personPredicates,
                       List<Predicate<Participation>> participationPredicates) {
        requireAllNonNull(personPredicates, participationPredicates);
        this.personPredicates = new ArrayList<>(personPredicates);
        this.participationPredicates = new ArrayList<>(participationPredicates);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // PredicateAdapter converts Predicate<Participation> to Predicate<Person>
        Predicate<Person> predicateForPersonFromParticipation = new PredicateAdapter(
                participationPredicates,
                model.getParticipationList());
        this.personPredicates.add(predicateForPersonFromParticipation);
        Predicate<Person> combinedPredicate = this.personPredicates.stream().reduce(x -> true, Predicate::and);

        model.updateFilteredPersonList(combinedPredicate);
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
        return this.personPredicates.equals(otherFindCommand.personPredicates)
                && this.participationPredicates.equals(otherFindCommand.participationPredicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person predicates", personPredicates)
                .add("participation predicates", participationPredicates)
                .toString();
    }
}
