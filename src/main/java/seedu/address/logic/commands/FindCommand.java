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
 * Finds and lists all persons in address book whose field(s) satisfy all
 * criteria provided. Criteria are given in a {@code PredicateGroup}.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields satisfies "
            + "all criteria provided displays them as a list with index numbers.\nEach criterion consists of "
            + "specified keywords (case-insensitive) that a field (as denoted by the prefix) must satisfy.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME ...] "
            + "[" + PREFIX_EMAIL + "EMAIL ...] "
            + "[" + PREFIX_GENDER + "GENDER ...] "
            + "[" + PREFIX_AGE + "AGE ...] "
            + "[" + PREFIX_DETAIL + "DETAIL ...]"
            + "[" + PREFIX_STUDY_GROUP_TAG + "STUDY_GROUP_TAG ...]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie";

    public static final String MESSAGE_NO_CRITERIA = "Provide at least one criteria to find!";

    private final PredicateGroup predicates;

    public FindCommand(PredicateGroup predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicates);
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
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
