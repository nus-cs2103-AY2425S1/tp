package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOT_MONTHPAID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.MonthPaidContainsKeywordsPredicate;
import seedu.address.model.person.NameAndClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NotMonthPaidContainsKeywordsPredicate;



/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
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
            + "Example 5: " + COMMAND_WORD + " " + PREFIX_NOT_MONTHPAID + "2022-12\n";;

    public static final String NO_SEARCH_FIELDS_PROVIDED = "At least one field to search by must be provided.";

    private final NameContainsKeywordsPredicate predicate;

    private final ClassIdContainsKeywordsPredicate predicateClassId;

    private final NameAndClassIdContainsKeywordsPredicate predicateNameAndClassId;

    private final MonthPaidContainsKeywordsPredicate predicateM;
    private final NotMonthPaidContainsKeywordsPredicate predicateNotM;


    /**
     * Stores the predicate to be used to filter the list of persons by name
     * @param predicate the predicate to be used to filter the list of persons
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.predicateClassId = null;
        this.predicateNameAndClassId = null;
        this.predicateM = null;
        this.predicateNotM = null;
    }

    /**
     * Stores the predicate to be used to filter the list of persons by class ID
     * @param predicate the predicate to be used to filter the list of persons
     */
    public FindCommand(ClassIdContainsKeywordsPredicate predicate) {
        this.predicateClassId = predicate;
        this.predicate = null;
        this.predicateNameAndClassId = null;
        this.predicateM = null;
        this.predicateNotM = null;
    }

    /**
     * Stores the predicate to be used to filter the list of persons by name and class ID
     * @param predicate the predicate to be used to filter the list of persons
     */
    public FindCommand(NameAndClassIdContainsKeywordsPredicate predicate) {
        this.predicateNameAndClassId = predicate;
        this.predicate = null;
        this.predicateClassId = null;
        this.predicateM = null;
        this.predicateNotM = null;
    }

    /**
     * Stores the predicate to be used to filter the list of persons by month paid
     * @param predicate
     */
    public FindCommand(MonthPaidContainsKeywordsPredicate predicate) {
        this.predicateM = predicate;
        this.predicate = null;
        this.predicateClassId = null;
        this.predicateNameAndClassId = null;
        this.predicateNotM = null;
    }

    /**
     * Stores the predicate to be used to filter the list of persons by month paid
     * @param predicate
     */
    public FindCommand(NotMonthPaidContainsKeywordsPredicate predicate) {
        this.predicateNotM = predicate;
        this.predicate = null;
        this.predicateClassId = null;
        this.predicateNameAndClassId = null;
        this.predicateM = null;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (predicate != null) {
            model.updateFilteredPersonList(predicate);
        } else if (predicateClassId != null) {
            model.updateFilteredPersonList(predicateClassId);
        } else if (predicateM != null) {
            model.updateFilteredPersonList(predicateM);
        } else if (predicateNotM != null) {
            model.updateFilteredPersonList(predicateNotM);
        } else {
            model.updateFilteredPersonList(predicateNameAndClassId);
        }

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

        if (predicate != null) {
            return predicate.equals(otherFindCommand.predicate);
        } else if (predicateClassId != null) {
            return predicateClassId.equals(otherFindCommand.predicateClassId);
        } else if (predicateM != null) {
            return predicateM.equals(otherFindCommand.predicateM);
        } else if (predicateNotM != null) {
            return predicateNotM.equals(otherFindCommand.predicateNotM);
        } else {
            assert(predicateNameAndClassId != null);
            return predicateNameAndClassId.equals(otherFindCommand.predicateNameAndClassId);
        }

    }

    @Override
    public String toString() {
        if (predicate != null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicate)
                    .toString();
        } else if (predicateClassId != null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicateClassId)
                    .toString();
        } else if (predicateM != null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicateM)
                    .toString();
        } else if (predicateNotM != null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicateNotM)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("predicate", predicateNameAndClassId)
                    .toString();
        }

    }
}
