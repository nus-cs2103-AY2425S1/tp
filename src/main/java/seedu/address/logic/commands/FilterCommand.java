package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CourseContainsKeywordsPredicate;
import seedu.address.model.person.ModuleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons EduContacts whose name matches the specified keywords
 * or whose course matches the specified course.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names match "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: NAME_KEYWORD [MORE_NAME_KEYWORDS]...\n"
            + "or: MODULE_KEYWORD\n"
            + "or: COURSE_KEYWORD\n"
            + "Example NAME: " + COMMAND_WORD + " n/alice bob charlie\n"
            + "Example MODULE: " + COMMAND_WORD + " m/CS2103T\n"
            + "Example COURSE: " + COMMAND_WORD + " c/Computer Science";

    private final NameContainsKeywordsPredicate namePredicate;
    private final ModuleContainsKeywordsPredicate modulePredicate;
    private final CourseContainsKeywordsPredicate coursePredicate;

    /**
     * Constructs a {@code FilterCommand} with a {@code NameContainsKeywordsPredicate}.
     * The {@code modulePredicate} will be set to {@code null}.
     *
     * @param namePredicate the predicate to filter the list by name keywords
     */
    public FilterCommand(NameContainsKeywordsPredicate namePredicate) {
        this.coursePredicate = null;
        this.namePredicate = namePredicate;
        this.modulePredicate = null;
    }

    /**
     * Constructs a {@code FilterCommand} with a {@code ModuleContainsKeywordsPredicate}.
     * The {@code namePredicate} will be set to {@code null}.
     *
     * @param modulePredicate the predicate to filter the list by module keywords
     */
    public FilterCommand(ModuleContainsKeywordsPredicate modulePredicate) {
        this.coursePredicate = null;
        this.modulePredicate = modulePredicate;
        this.namePredicate = null;
    }

    /**
     * Constructs a {@code FilterCommand} with a {@code CourseContainsKeywordsPredicate}.
     * The {@code namePredicate} and {@code modulePredicate} will be set to {@code null}.
     *
     * @param coursePredicate the predicate to filter the list by course keywords
     */
    public FilterCommand(CourseContainsKeywordsPredicate coursePredicate) {
        this.coursePredicate = coursePredicate;
        this.namePredicate = null;
        this.modulePredicate = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (namePredicate != null) {
            model.updateFilteredPersonList(namePredicate);
        } else if (modulePredicate != null) {
            model.updateFilteredPersonList(modulePredicate);
        } else if (coursePredicate != null) {
            model.updateFilteredPersonList(coursePredicate);
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
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return (namePredicate != null && namePredicate.equals(otherFilterCommand.namePredicate))
                || (modulePredicate != null && modulePredicate.equals(otherFilterCommand.modulePredicate))
                || (coursePredicate != null && coursePredicate.equals(otherFilterCommand.coursePredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("modulePredicate", modulePredicate)
                .add("coursePredicate", coursePredicate)
                .toString();
    }
}
