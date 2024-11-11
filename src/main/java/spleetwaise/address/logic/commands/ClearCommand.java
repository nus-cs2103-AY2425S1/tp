package spleetwaise.address.logic.commands;

import spleetwaise.address.model.AddressBook;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.model.TransactionBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        CommonModelManager model = CommonModelManager.getInstance();
        model.setAddressBook(new AddressBook());
        model.setTransactionBook(new TransactionBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
