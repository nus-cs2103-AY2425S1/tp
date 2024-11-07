package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;


/**
 * Checks how many Contacts are currently excluded.
 */
public class CheckExcludedCommand extends Command {
    public static final String COMMAND_WORD = "check-excluded";
    public static final String COMMAND_WORD_SHORT_FORM = "chx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks which Contacts are currently excluded.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Currently have %d excluded Contacts: ";
    public static final String MESSAGE_EMPTY = "No Contacts are currently excluded.";

    public CheckExcludedCommand() {
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        // Update the filtered person list without the exclusion predicate
        if (model.getExcludedPersons().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder sb = new StringBuilder();
        int excludedSize = model.getExcludedPersons().size();
        int count = 0;
        for (Person p : model.getExcludedPersons()) {
            sb.append(p.getName().toString());
            if (++count < excludedSize) {
                sb.append(", ");
            }
        }

        return new CommandResult(String.format(
                MESSAGE_SUCCESS, model.getExcludedPersons().size()) + sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof CheckExcludedCommand; // instanceof handles nulls
    }
}
