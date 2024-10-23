package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;




/**
 * Represents a command to view details of a person.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Use either of the parameters below\n "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + PREFIX_PHONE + "PHONE \n"
            + PREFIX_ROOM_NUMBER + "ROOM_NUMBER";

    private final Predicate<Person> combinedPredicate;


    /**
     * @param combinedPredicate the search condition for personlist update
     */
    public ViewCommand(Predicate<Person> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
    }

    /**
     * @param model
     * @return a string denote success
     */
    @Override
    public CommandResult execute(Model model) {
        requireNotExecuted();
        requireNonNull(model);
        model.updateFilteredPersonList(combinedPredicate);
        isExecuted = true;
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * @param other
     * @return a boolean indicate if they are equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        // Compare the combinedPredicate using reference equality or a custom method
        // if you have a better way to compare the logic encapsulated by the predicates.
        return combinedPredicate.equals(otherViewCommand.combinedPredicate);
    }

    /**
     * @return a string represents viewCommand
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("view predicate", combinedPredicate)
                .toString();
    }
}
