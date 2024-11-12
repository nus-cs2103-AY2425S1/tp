package seedu.address.logic.commands.searchmode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearExcludedCommandTest {
    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute() {
        model.addPerson(ALICE);
        model.excludePerson(ALICE);
        ClearExcludedCommand clearExcludedCommand = new ClearExcludedCommand();
        clearExcludedCommand.execute(model, model.getEventManager());
        assertTrue(model.getExcludedPersons().isEmpty());
    }

    @Test
    public void execute_empty() {
        ClearExcludedCommand clearExcludedCommand = new ClearExcludedCommand();

        assertEquals(ClearExcludedCommand.MESSAGE_EMPTY,
                clearExcludedCommand.execute(model, model.getEventManager()).getFeedbackToUser());
    }

    @Test
    public void equals() {
        ClearExcludedCommand clearExcludedCommand = new ClearExcludedCommand();

        // same object -> returns true
        assertTrue(clearExcludedCommand.equals(clearExcludedCommand));

        // different types -> returns false
        assertFalse(clearExcludedCommand.equals(1));

        // null -> returns false
        assertFalse(clearExcludedCommand.equals(null));
    }
}
