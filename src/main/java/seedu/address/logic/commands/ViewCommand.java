package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.predicates.NricMatchesPredicate;

/**
 * Views and lists the person in the address book whose NRIC matches the specified NRIC.
 * NRIC matching is case insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the person whose NRIC matches the specified "
            + "NRIC and displays the details.\n"
            + "Parameters: " + PREFIX_NRIC + "NRIC (Nine characters, first and last alphabet, others digits)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC + "S1234567D";

    private final NricMatchesPredicate predicate;

    public ViewCommand(String nric) {
        this.predicate = new NricMatchesPredicate(nric);
    }



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        } else {
            Nric nric = model.getFilteredPersonList().get(0).getNric();
            return new CommandResult(String.format(Messages.MESSAGE_VIEW, nric), false, true, false);
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
