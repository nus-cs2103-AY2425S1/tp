package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "/clear";
    public static final String MESSAGE_SUCCESS = "HireMe has been cleared!";


    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
