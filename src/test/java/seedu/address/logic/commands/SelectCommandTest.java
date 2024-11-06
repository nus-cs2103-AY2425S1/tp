package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.SelectPredicate;


/**
 * Contains integration test (interaction with the model) for {@code SelectCommand}
 */
public class SelectCommandTest {
    //TypicalAddressBook contains ALICE, BENSON, CARL, CARLMEIER, DANIEL, ELLE, FIONA, GEORGE
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<Index> firstIndexList = List.of(Index.fromZeroBased(0));
        List<Index> secondIndexList = List.of(Index.fromZeroBased(0), Index.fromZeroBased(1));

        SelectCommand firstCommand = new SelectCommand(firstIndexList);
        SelectCommand secondCommand = new SelectCommand(secondIndexList);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same input/indexList -> returns true
        assertTrue(firstCommand.equals(new SelectCommand(firstIndexList)));

        // different input/indexList -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals("A STRING"));

        // null -> returns false
        assertFalse(firstCommand.equals(null));
    }

    @Test
    public void execute_multipleMatch() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        List<Index> indexes = List.of(Index.fromZeroBased(0), Index.fromZeroBased(3));
        SelectCommand selectCommand = new SelectCommand(indexes);
        expectedModel.updateFilteredPersonList(prepareSelectPredicate(indexes));
        assertCommandSuccess(selectCommand, model,
                expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_oneMatch() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<Index> indexes = List.of(Index.fromZeroBased(2));
        SelectCommand selectCommand = new SelectCommand(indexes);
        expectedModel.updateFilteredPersonList(prepareSelectPredicate(indexes));
        assertCommandSuccess(selectCommand, model,
                expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchIndexOutOfBound_thenThrowExceptions() {
        String expectedMessage = MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX;
        List<Index> indexes = List.of(Index.fromZeroBased(getTypicalPersons().size() + 1));
        SelectCommand selectCommand = new SelectCommand(indexes);
        assertCommandFailure(selectCommand, model, expectedMessage);
        assertThrows(CommandException.class, () -> selectCommand.execute(model));
    }

    @Test
    public void whenIndexIsOutOfBounds_thenThrowsException() {}



    @Test
    public void toStringMethod() {
        List<Index> indexes = List.of(Index.fromZeroBased(0));
        SelectCommand command = new SelectCommand(indexes);
        String expected = SelectCommand.class.getCanonicalName() + "{indexes=" + indexes + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Prepares a {@code SelectPredicate} based on the list of specified {@code indexes}.
     *
     * @param indexes A list of {@code Index} objects representing positions in the typical persons list.
     *                Each index is expected to be within the bounds of the list.
     * @return A {@code SelectPredicate} containing the persons at the specified indexes.
     */
    public SelectPredicate prepareSelectPredicate(List<Index> indexes) {
        List<Person> persons = new ArrayList<>();

        for (Index index: indexes) {
            persons.add(getTypicalPersons().get(index.getZeroBased()));
        }

        return new SelectPredicate(persons);
    }
}
