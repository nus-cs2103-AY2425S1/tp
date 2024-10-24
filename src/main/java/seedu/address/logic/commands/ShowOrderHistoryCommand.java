package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Show the order history of a customer
 */
public class ShowOrderHistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": show order(s) history of a customer,"
            + "\nParameters: [NAME OF PERSON] "
            + "\nExample: " + COMMAND_WORD + " Alex Yeoh";

    public static final String MESSAGE_SUCCESS = "Orders history: \n";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person does not exist: %1$s";

    private final Name name;

    public ShowOrderHistoryCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person p = model.findPersonByName(this.name);

        if (p == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, this.name.toString()));
        }

        return new CommandResult(MESSAGE_SUCCESS + p.getOrderTracker().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowOrderHistoryCommand)) {
            return false;
        }

        ShowOrderHistoryCommand e = (ShowOrderHistoryCommand) other;
        return this.name.equals(e.name);
    }
}
