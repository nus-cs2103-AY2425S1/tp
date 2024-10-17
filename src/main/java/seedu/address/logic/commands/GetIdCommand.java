package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Gets the id of a patient.
 */
public class GetIdCommand extends Command {
    public static final String COMMAND_WORD = "getId";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": gets the id "
            + "of a patient. "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "[NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Philips";
    private final NameContainsKeywordsPredicate predicate;
    public GetIdCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(String.format(Messages.MESSAGE_GET_ID,
                model.getFilteredPersonList().get(0).getId().getIdValue()));
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
