package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Displays to the user all commands that can be performed in the address book.
 */
public class CommandListCommand extends Command {

    public static final String COMMAND_WORD = "commands";

    public static final String MESSAGE_SUCCESS = "Listed all commands that can be performed:\n";


    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        AddressBook currentAddressBook = (AddressBook) model.getAddressBook();
        String commandList = ParserUtil.parseCommandList(currentAddressBook);
        return new CommandResult(MESSAGE_SUCCESS + commandList,
                currentAddressBook.getCommandList());
    }
}
