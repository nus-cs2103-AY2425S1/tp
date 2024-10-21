package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.WeddingBuilder;

public class AddWeddingCommandTest {

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingCommand(null));
    }
    @Test
    public void equals() {
        Wedding weddingOne = new WeddingBuilder().withWeddingName("John Loh & Jean Tan").build();
        Wedding weddingTwo = new WeddingBuilder().withWeddingName("Alice Tan & Bob Lee").build();
        AddWeddingCommand addWeddingOneCommand = new AddWeddingCommand(weddingOne);
        AddWeddingCommand addWeddingTwoCommand = new AddWeddingCommand(weddingTwo);

        // same object -> returns true
        assertTrue(addWeddingOneCommand.equals(addWeddingOneCommand));

        // same values -> returns true
        AddWeddingCommand addWeddingOneCommandCopy = new AddWeddingCommand(weddingOne);
        assertTrue(addWeddingOneCommand.equals(addWeddingOneCommandCopy));

        // different types -> returns false
        assertFalse(addWeddingOneCommand.equals(1));

        // null -> returns false
        assertFalse(addWeddingOneCommand.equals(null));

        // different wedding -> returns false
        assertFalse(addWeddingOneCommand.equals(addWeddingTwoCommand));
    }

    @Test
    public void toStringMethod() {
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(WEDDING_ONE);
        String expected = AddWeddingCommand.class.getCanonicalName() + "{toAdd=" + WEDDING_ONE + "}";
        assertEquals(expected, addWeddingCommand.toString());
    }
}

