package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ActualClearCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.parser.ActualClearCommandParser.MESSAGE_FAILURE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ActualClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ActualClearCommandParserTest {


    @Test
    public void clearConfirmation_input() throws ParseException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ActualClearCommand returnedCommand = new ActualClearCommandParser().parse("YES");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_SUCCESS));
        returnedCommand = new ActualClearCommandParser().parse("   yEs");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_SUCCESS));

        returnedCommand = new ActualClearCommandParser().parse(" Y  ");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_SUCCESS));

    }

    @Test
    public void nonClearConfirmation_input() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ActualClearCommand returnedCommand = new ActualClearCommandParser().parse("np");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_FAILURE));

        returnedCommand = new ActualClearCommandParser().parse("");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_FAILURE));

        returnedCommand = new ActualClearCommandParser().parse("        ");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_FAILURE));

        returnedCommand = new ActualClearCommandParser().parse("**&#****");
        assertEquals(returnedCommand.execute(model), new CommandResult(MESSAGE_FAILURE));

    }
}
