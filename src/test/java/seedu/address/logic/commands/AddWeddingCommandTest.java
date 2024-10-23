package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.wedding.Wedding;

public class AddWeddingCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_newWedding_success() throws Exception {
        Wedding validWedding = WEDDING_ONE;
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(validWedding);

        CommandResult commandResult = addWeddingCommand.execute(model);

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, Messages.formatWedding(validWedding)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        Wedding duplicateWedding = WEDDING_ONE;
        model.addWedding(duplicateWedding);

        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(duplicateWedding);

        assertThrows(CommandException.class, AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () ->
                addWeddingCommand.execute(model));
    }

    @Test
    public void equals() {

        AddWeddingCommand addWeddingCommand1 = new AddWeddingCommand(WEDDING_ONE);
        AddWeddingCommand addWeddingCommand2 = new AddWeddingCommand(WEDDING_TWO);

        // Same object -> returns true
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1));

        // Same values -> returns true
        AddWeddingCommand addWeddingCommand1Copy = new AddWeddingCommand(WEDDING_ONE);
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1Copy));

        // Different types -> returns false
        assertTrue(!addWeddingCommand1.equals(1));

        // Different wedding -> returns false
        assertTrue(!addWeddingCommand1.equals(addWeddingCommand2));
    }
}
