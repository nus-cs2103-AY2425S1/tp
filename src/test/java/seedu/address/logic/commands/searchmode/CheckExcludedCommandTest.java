package seedu.address.logic.commands.searchmode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CheckExcludedCommandTest {
    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute() {
        model.addPerson(ALICE);
        model.excludePerson(ALICE);
        CheckExcludedCommand checkExcludedCommand = new CheckExcludedCommand();
        assertEquals(checkExcludedCommand.execute(model, model.getEventManager()).getFeedbackToUser(),
                "Currently have 1 excluded Contacts: " + ALICE.getName().toString());
        assertTrue(model.getExcludedPersons().contains(ALICE));
    }

    @Test
    public void execute_empty() {
        CheckExcludedCommand checkExcludedCommand = new CheckExcludedCommand();
        assertEquals(CheckExcludedCommand.MESSAGE_EMPTY,
                checkExcludedCommand.execute(model, model.getEventManager()).getFeedbackToUser());
    }

    @Test
    public void execute_multiple() {
        model.addPerson(ALICE);
        model.addPerson(BOB);
        model.excludePerson(ALICE);
        model.excludePerson(BOB);
        CheckExcludedCommand checkExcludedCommand = new CheckExcludedCommand();
        assertEquals(checkExcludedCommand.execute(model, model.getEventManager()).getFeedbackToUser(),
                "Currently have 2 excluded Contacts: " + ALICE.getName().toString()
                        + ", " + BOB.getName().toString());
    }
}
