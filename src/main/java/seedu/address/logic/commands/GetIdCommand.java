package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and returns the id of the person in address book whose
 * name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class GetIdCommand extends Command {
    public static final String COMMAND_WORD = "getId";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": gets the id "
            + "of a patient based on the name provided. "
            + COMMAND_WORD + "[KEYWORDS...]\n"
            + "Example: " + COMMAND_WORD
            + "John Philips";
    private final NameContainsKeywordsPredicate predicate;
    public GetIdCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(Messages.MESSAGE_INVALID_NAME);
        }

        return new CommandResult(String.format(Messages.MESSAGE_GET_ID,
                model.getFilteredPersonList().get(0).getId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GetIdCommand)) {
            return false;
        }

        GetIdCommand otherFindCommand = (GetIdCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
