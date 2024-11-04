package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.Model;

/**
 * Clears all the internship applications.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "/clear";
    public static final String MESSAGE_SUCCESS = "HireMe has been cleared!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all the internship applications\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
