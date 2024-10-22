package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, validWedding.getName()),
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
        Wedding wedding1 = new Wedding("Wedding1", "12/12/2024");
        Wedding wedding2 = new Wedding("Wedding2", "13/12/2024");

        AddWeddingCommand addWeddingCommand1 = new AddWeddingCommand(wedding1);
        AddWeddingCommand addWeddingCommand2 = new AddWeddingCommand(wedding2);

        // Same object -> returns true
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1));

        // Same values -> returns true
        AddWeddingCommand addWeddingCommand1Copy = new AddWeddingCommand(wedding1);
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1Copy));

        // Different types -> returns false
        assertTrue(!addWeddingCommand1.equals(1));

        // Different wedding -> returns false
        assertTrue(!addWeddingCommand1.equals(addWeddingCommand2));
    }
}
