package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PredicateContainer;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String ARGUMENT_WORD = "all";

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_PURPOSE = "Finds all employees and/or potential hires whose names "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.";
    public static final String COMMAND_PARAMETERS = FindEmployeeCommand.ARGUMENT_WORD + " for employees, "
            + FindPotentialCommand.ARGUMENT_WORD + " for potential hires, and "
            + FindCommand.ARGUMENT_WORD + " for both employees and potential hires.";
    public static final String COMMAND_KEYWORDS = PREFIX_NAME + "/[NAMES] " + PREFIX_PHONE + "/[PHONE NUMBERS] "
            + PREFIX_EMAIL + "/[EMAILS]" + PREFIX_DEPARTMENT + "/[DEPARTMENTS] " + PREFIX_ROLE + "/[ROLES]";
    public static final String MESSAGE_FORMAT = COMMAND_WORD + "PARAMETER KEYWORD [MORE_KEYWORDS]"
            + "\nParameter: " + COMMAND_PARAMETERS
            + "\nKeyword: " + COMMAND_KEYWORDS;
    public static final String MESSAGE_EXAMPLE = COMMAND_WORD + " " + ARGUMENT_WORD
            + " n/alice bob e/alice@example.com bob@example.com";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + MESSAGE_PURPOSE
            + "\nFormat: " + MESSAGE_FORMAT
            + "\nExample: " + MESSAGE_EXAMPLE;

    private final PredicateContainer predicateContainer;

    public FindCommand(PredicateContainer predicateContainer) {
        this.predicateContainer = predicateContainer;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS.and(predicateContainer.getCombinedPredicates()));
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
        return predicateContainer.equals(otherFindCommand.predicateContainer);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicateContainer)
                .toString();
    }

    public Predicate<Person> getPredicate() {
        return predicateContainer.getCombinedPredicates();
    }
    public PredicateContainer getPredicateContainer() {
        return predicateContainer;
    }
}
