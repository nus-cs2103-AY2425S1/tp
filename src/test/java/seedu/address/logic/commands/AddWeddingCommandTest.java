package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.TypicalPersons;

public class AddWeddingCommandTest {

    private Model model;
    private Set<Index> contactIndexes;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        contactIndexes = new HashSet<>();
    }

    @Test
    public void execute_newWeddingWithoutContacts_success() throws Exception {
        Wedding validWedding = WEDDING_ONE;
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(new Wedding(WEDDING_ONE.getWeddingName(),
                WEDDING_ONE.getWeddingDate()), contactIndexes);

        CommandResult commandResult = addWeddingCommand.execute(model);

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, Messages.formatWedding(validWedding)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_newWeddingWithContacts_success() throws Exception {
        Person person1 = TypicalPersons.ALICE;
        Person person2 = TypicalPersons.BOB;
        model.addPerson(person1);
        model.addPerson(person2);

        contactIndexes.add(Index.fromOneBased(1));
        contactIndexes.add(Index.fromOneBased(2));

        Wedding validWedding = WEDDING_TWO;
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(new Wedding(WEDDING_TWO.getWeddingName(),
                WEDDING_TWO.getWeddingDate()), contactIndexes);

        CommandResult commandResult = addWeddingCommand.execute(model);

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, Messages.formatWedding(validWedding)),
                commandResult.getFeedbackToUser());
    }


    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        Wedding duplicateWedding = WEDDING_ONE;
        model.addWedding(duplicateWedding);

        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(new Wedding(WEDDING_ONE.getWeddingName(),
                WEDDING_ONE.getWeddingDate()), contactIndexes);

        assertThrows(CommandException.class, AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () ->
                addWeddingCommand.execute(model));
    }

    @Test
    public void execute_invalidContactIndex_throwsCommandException() {
        contactIndexes.add(Index.fromOneBased(999));
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(new Wedding(WEDDING_ONE.getWeddingName(),
                WEDDING_ONE.getWeddingDate()), contactIndexes);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                addWeddingCommand.execute(model));
    }

    @Test
    public void equals() {

        AddWeddingCommand addWeddingCommand1 = new AddWeddingCommand(new Wedding(WEDDING_ONE.getWeddingName(),
                WEDDING_ONE.getWeddingDate()), contactIndexes);
        AddWeddingCommand addWeddingCommand2 = new AddWeddingCommand(new Wedding(WEDDING_TWO.getWeddingName(),
                WEDDING_TWO.getWeddingDate()), contactIndexes);

        // Same object -> returns true
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1));

        // Same values -> returns true
        AddWeddingCommand addWeddingCommand1Copy = new AddWeddingCommand(new Wedding(WEDDING_ONE.getWeddingName(),
                WEDDING_ONE.getWeddingDate()), contactIndexes);
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1Copy));

        // Different types -> returns false
        assertTrue(!addWeddingCommand1.equals(1));

        // Different wedding -> returns false
        assertTrue(!addWeddingCommand1.equals(addWeddingCommand2));
    }
}
