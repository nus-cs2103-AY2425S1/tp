package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.model.AddressBook;
import seedu.academyassist.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "AcademyAssist has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
