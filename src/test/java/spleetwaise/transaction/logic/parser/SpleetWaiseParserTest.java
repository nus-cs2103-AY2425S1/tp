package spleetwaise.transaction.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.testutil.TransactionUtil;


public class SpleetWaiseParserTest {

    private final SpleetWaiseParser parser = new SpleetWaiseParser();

    @Test
    public void parseCommand_add() throws Exception {
        AddCommand command = (AddCommand) parser.parseCommand(TransactionUtil.getAddCommand());
        assert (command != null);
    }

    @Test
    public void parseCommand_unrecognisedInput_returnsNull() throws ParseException {
        assertNull(parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_returnsNull() throws ParseException {
        assertNull(parser.parseCommand("unknownCommand"));
    }
}
