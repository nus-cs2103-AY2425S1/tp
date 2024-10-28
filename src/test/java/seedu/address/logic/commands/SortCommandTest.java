package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICEY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalClientHub(), new UserPrefs());
        expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
    }

    @Test
    public void test_execute() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 8);

        NameComparator comparator = new NameComparator();
        SortCommand command = new SortCommand();
        expectedModel.updateSortedPersonList(comparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ALICEY, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getDisplayPersons());
    }


    @Test
    public void equals() {

        SortCommand sortFirstCommand = new SortCommand();
        SortCommand sortSecondCommand = new SortCommand();

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // same comparator -> returns true
        assertTrue(sortFirstCommand.equals(sortSecondCommand));
    }

    @Test
    public void toStringMethod() {
        NameComparator comparator = new NameComparator();
        SortCommand sortCommand = new SortCommand();
        String expected = SortCommand.class.getCanonicalName() + "{comparator=" + comparator + "}";
        assertEquals(expected, sortCommand.toString());
    }

}
