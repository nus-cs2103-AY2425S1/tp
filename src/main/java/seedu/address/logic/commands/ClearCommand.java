package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.internshipapplication.InternshipApplication;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "/c";
    public static final String MESSAGE_SUCCESS = "HireMe has been cleared!";


    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
