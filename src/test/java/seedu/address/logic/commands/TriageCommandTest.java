package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRIAGE_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Triage;
import seedu.address.testutil.PersonBuilder;

public class TriageCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        Person samplePerson = new PersonBuilder().withNric(VALID_NRIC_AMY).build();
        model.addPerson(samplePerson);
    }

    @Test
    public void execute_triageSuccess_updatesTriageLevel() throws Exception {
        Nric nric = new Nric(VALID_NRIC_AMY);
        Triage triage = new Triage(VALID_TRIAGE_AMY);
        TriageCommand triageCommand = new TriageCommand(nric, triage);

        Model expectedModel = new ModelManager();
        Person triagedPerson = new PersonBuilder().withNric(VALID_NRIC_AMY).withTriage(VALID_TRIAGE_AMY).build();
        expectedModel.addPerson(triagedPerson);

        String expectedMessage = String.format(TriageCommand.MESSAGE_ADD_TRIAGE_SUCCESS,
                Messages.format(triagedPerson));

        CommandResult result = triageCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Nric invalidNric = new Nric("S9999999Z");
        Triage triage = new Triage("3");
        TriageCommand triageCommand = new TriageCommand(invalidNric, triage);

        assertThrows(CommandException.class, () -> triageCommand.execute(model));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Nric nric = new Nric(VALID_NRIC_AMY);
        Triage triage = new Triage(VALID_TRIAGE_AMY);
        TriageCommand triageCommand = new TriageCommand(nric, triage);

        assertTrue(triageCommand.equals(triageCommand));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        Nric nric = new Nric(VALID_NRIC_AMY);
        Triage triage = new Triage(VALID_TRIAGE_AMY);
        TriageCommand triageCommand = new TriageCommand(nric, triage);

        assertFalse(triageCommand.equals(new Object()));
    }

    @Test
    public void equals_null_returnsFalse() {
        Nric nric = new Nric(VALID_NRIC_AMY);
        Triage triage = new Triage(VALID_TRIAGE_AMY);
        TriageCommand triageCommand = new TriageCommand(nric, triage);

        assertFalse(triageCommand.equals(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Nric nric = new Nric(VALID_NRIC_AMY);
        Triage triage = new Triage(VALID_TRIAGE_AMY);
        TriageCommand triageCommand1 = new TriageCommand(nric, triage);
        TriageCommand triageCommand2 = new TriageCommand(nric, triage);

        assertTrue(triageCommand1.equals(triageCommand2));
    }
}
