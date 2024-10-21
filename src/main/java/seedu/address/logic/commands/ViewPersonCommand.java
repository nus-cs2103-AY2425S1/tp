package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesKeywordsPredicate;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Views the specific information of a person in address book whose name is the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewPersonCommand extends ViewCommand {

    public static final String COMMAND_FIELD = "person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the person whose name is "
            + "the specified keywords (case-insensitive) and displays him/her.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " David Li";

    private final NameMatchesKeywordsPredicate predicate;

    public ViewPersonCommand(NameMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false, false, CommandTabChange.PERSON, CommandDetailChange.DETAILED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPersonCommand)) {
            return false;
        }

        ViewPersonCommand otherViewPersonCommand = (ViewPersonCommand) other;
        return predicate.equals(otherViewPersonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
