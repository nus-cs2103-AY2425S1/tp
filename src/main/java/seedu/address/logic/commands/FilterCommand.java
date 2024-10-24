package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.skill.SkillsContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;
import seedu.address.ui.DisplayType;

/**
 * Finds and lists all persons in address book has all the skills as the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons that has any of the skills or tags as "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [t/TAG]... [s/SKILL]...\n"
            + "Example: " + COMMAND_WORD + " t/swe s/frontend s/backend";

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final SkillsContainsKeywordsPredicate skillsPredicate;
    private final TagsContainsKeywordsPredicate tagsPredicate;

    /**
     * Takes in a {@code SkillsContainsKeywordsPredicate} and {@code TagsContainsKeywordsPredicate}.
     * Tthe logical OR of the two predicates will be used to filter {@code Person} objects in the addressbook.
     */
    public FilterCommand(SkillsContainsKeywordsPredicate skillsPredicate,
            TagsContainsKeywordsPredicate tagsPredicate) {
        this.skillsPredicate = skillsPredicate;
        this.tagsPredicate = tagsPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logical OR of the two predicates
        Predicate<Person> predicate = skillsPredicate.or(tagsPredicate);
        model.updateFilteredPersonList(predicate);

        // Filtered successsfully
        logger.fine(COMMAND_WORD + " by\n" + skillsPredicate + "\n" + tagsPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                DisplayType.PERSON_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return skillsPredicate.equals(otherFilterCommand.skillsPredicate)
                && tagsPredicate.equals(otherFilterCommand.tagsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("skillsPredicate", skillsPredicate)
                .add("tagsPredicate", tagsPredicate)
                .toString();
    }
}
