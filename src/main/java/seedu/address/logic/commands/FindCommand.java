package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOT_MONTHPAID;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicateBuilder;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_CLASSID + "1 2\n"
            + "Example 3: " + COMMAND_WORD + " " + PREFIX_NAME + "alice " + PREFIX_CLASSID + "1\n"
            + "Example 4: " + COMMAND_WORD + " " + PREFIX_MONTHPAID + "2022-12\n"
            + "Example 5: " + COMMAND_WORD + " " + PREFIX_NOT_MONTHPAID + "2022-12\n";

    public static final String NO_SEARCH_FIELDS_PROVIDED = "At least one field to search by must be provided.";
    public static final String EMPTY_SEARCH_VALUE_PROVIDED = "Search value cannot be empty.";
    private final PersonPredicateBuilder personPredicateBuilder; //builder stored to facilitate testing
    private final Predicate<Person> personPredicate;

    /**
     * Creates a {@code FindCommand} which stores the {@code PersonPredicateBuilder}
     * to be used to filter.
     */
    public FindCommand(PersonPredicateBuilder personPredicateBuilder) {
        requireNonNull(personPredicateBuilder);
        this.personPredicateBuilder = new PersonPredicateBuilder(personPredicateBuilder);
        this.personPredicate = personPredicateBuilder.build();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(personPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return personPredicateBuilder.equals(otherFindCommand.personPredicateBuilder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personPredicateBuilder", personPredicateBuilder)
                .toString();
    }
}
