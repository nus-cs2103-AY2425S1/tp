package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find students matching the given "
            + "keywords across specified fields.\n"
            + "1. Name, address, and tutorial searches support multiple word inputs; "
            + "tag and tutorial searches support duplicate prefixes; "
            + "other fields accept only a single word and single prefix.\n"
            + "2. Exact matching is required for tutorial field. "
            + "with a tutorial named mathematics.\n3. Payment field will require a true/false input. "
            + "matches all students who have paid fees.\n4. Attendance field require a date range input with the format"
            + " of dd/MM/yyyy:dd/MM/yyyy.\n5. Otherwise for other fields, "
            + "partial matching is allowed.\n"
            + "6. Search is case-insensitive, and multiple conditions are combined with logical AND.\n"
            + "7. Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PAYMENT + "PAYMENT (true/false)] "
            + "[" + PREFIX_ATTENDANCE + "DATE_RANGE (in the format of dd/MM/yyyy:dd/MM/yyyy)] "
            + "[" + PREFIX_TUTORIAL + "TUTORIAL] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TAG + "Scholar "
            + PREFIX_TAG + "SessionB "
            + PREFIX_ATTENDANCE + "24/10/2024:27/10/2024";
    private final List<Predicate<Person>> personPredicates;
    private final List<Predicate<Participation>> participationPredicates;
    private final Logger logger = LogsCenter.getLogger(FindCommand.class);

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
        logger.info(" - Running execute(Model model) in " + FindCommand.class);
        requireNonNull(model);

        // PredicateAdapter converts Predicate<Participation> to Predicate<Person>
        Predicate<Person> predicateForPersonFromParticipation = new PredicateAdapter(
                participationPredicates,
                model.getParticipationList());
        this.personPredicates.add(predicateForPersonFromParticipation);
        Predicate<Person> combinedPredicate = this.personPredicates.stream().reduce(x -> true, Predicate::and);

        model.updateFilteredPersonList(combinedPredicate);
        logger.info(" - Successfully updated filtered student list");
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
                .add("student predicates", personPredicates)
                .add("participation predicates", participationPredicates)
                .toString();
    }
}
