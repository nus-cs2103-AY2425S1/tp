package spleetwaise.transaction.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.logic.commands.ClearCommand;
import spleetwaise.transaction.logic.commands.EditCommand;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.testutil.TransactionUtil;
import spleetwaise.transaction.testutil.TypicalTransactions;


public class TransactionBookParserTest {

    private final TransactionBookModel tbModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook());
    private final AddressBookModel abModel = new AddressBookModelManager(
            TypicalPersons.getTypicalAddressBook());
    private final TransactionBookParser parser = new TransactionBookParser();


    @BeforeEach
    void setUp() {
        CommonModelManager.initialise(abModel, tbModel);
    }

    @Test
    public void parseCommand_add() throws Exception {
        AddressBookModelManager aBModel = new AddressBookModelManager();
        aBModel.addPerson(TypicalPersons.ALICE);
        CommonModelManager.initialise(aBModel, null);
        AddCommand command = (AddCommand) parser.parseCommand(TransactionUtil.getAddCommand());
        assertNotNull(command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_unknown() throws Exception {
        Command command = parser.parseCommand("420YoloSwag");
        assertNull(command);
    }

    @Test
    public void parseCommand_unrecognisedInput_returnsNull() throws ParseException {
        assertNull(parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_returnsNull() throws ParseException {
        assertNull(parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_editTxnCommand() throws ParseException {
        EditCommand command = (EditCommand) parser.parseCommand("editTxn 1 amt/10");
        assertNotNull(command);
    }
}
