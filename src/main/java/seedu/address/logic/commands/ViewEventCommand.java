package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameMatchesKeywordsPredicate;

/**
 * Views the specific information of an event in address book whose name is the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewEventCommand extends ViewCommand {
    public static final String COMMAND_FIELD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the event whose name is "
            + "the specified keywords (case-sensitive) and displays it.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " Awards show";

    private final EventNameMatchesKeywordsPredicate predicate;

    public ViewEventCommand(EventNameMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredEventList().size()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewContactCommand)) {
            return false;
        }

        ViewEventCommand otherFindCommand = (ViewEventCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
