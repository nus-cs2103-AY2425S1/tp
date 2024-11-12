package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.PredicateGroup;

/**
 * Finds and lists all persons in address book whose field(s) satisfy all criteria provided. Criteria are given in a
 * {@code PredicateGroup}.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields satisfies "
            + "all criteria provided displays them as a list with index numbers.\nEach criterion consists of "
            + "specified keywords (case-insensitive) that a field (as denoted by the prefix) must satisfy.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME ...]\n"
            + " [" + PREFIX_EMAIL + "EMAIL ...]\n"
            + " [" + PREFIX_GENDER + "GENDER ...]\n"
            + " [" + PREFIX_AGE + "AGE ...]\n"
            + " [" + PREFIX_DETAIL + "DETAIL ...]\n"
            + " [" + PREFIX_STUDY_GROUP_TAG + "STUDY-GROUP-TAG ...]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GENDER + "M "
            + PREFIX_STUDY_GROUP_TAG + "Control 1A";

    public static final String MESSAGE_NO_CRITERIA = "Provide at least one criteria to find!";
    public static final String MESSAGE_EMPTY_CRITERIA = "Criteria cannot be empty: %s";

    private final PredicateGroup predicates;

    /**
     * Constructs a FindCommand to find persons whose fields satisfy the given criteria.
     *
     * @param predicates The group of criteria to match against each person's fields.
     */
    public FindCommand(PredicateGroup predicates) {
        this.predicates = predicates;
    }

    /**
     * Executes the find command, updating the list of persons in the model to display only those
     * whose fields match all criteria in the specified PredicateGroup.
     *
     * @param model The model containing the list of persons.
     * @return CommandResult containing the result message after finding matching persons.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicates);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Compares this FindCommand to another object.
     *
     * @param other The object to be compared with this FindCommand.
     * @return true if the object is an instance of FindCommand with the same predicates, false otherwise.
     */
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
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
