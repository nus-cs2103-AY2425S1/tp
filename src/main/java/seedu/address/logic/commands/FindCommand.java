package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book matching the given criteria.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find-client";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds clients based on given parameters.\n"
            + "Parameters: n/[NAME] p/[PHONE] e/[EMAIL] a/[ADDRESS] pt/[POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int resultSize = model.getFilteredPersonList().size();
        if (resultSize == 0) {
            return new CommandResult("No clients found matching the given criteria.");
        }
        return new CommandResult("Search completed successfully.");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindCommand
                && predicate.equals(((FindCommand) other).predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
