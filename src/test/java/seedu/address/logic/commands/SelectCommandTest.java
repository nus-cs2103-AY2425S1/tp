package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
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
        List<Index> indexes = List.of(Index.fromZeroBased(0), Index.fromZeroBased(3));
        SelectCommand selectCommand = new SelectCommand(indexes);
        expectedModel.updateFilteredPersonList(prepareSelectPredicate(indexes));
        String expectedMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                formatSelectedPersons(prepareSelectedPersons(indexes)));
        assertCommandSuccess(selectCommand, model,
                expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_oneMatch() {
        List<Index> indexes = List.of(Index.fromZeroBased(2));
        SelectCommand selectCommand = new SelectCommand(indexes);
        expectedModel.updateFilteredPersonList(prepareSelectPredicate(indexes));
        String expectedMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                formatSelectedPersons(prepareSelectedPersons(indexes)));
        assertCommandSuccess(selectCommand, model,
                expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchIndexOutOfBound_thenThrowExceptions() {
        String invalidIndexes = String.valueOf(Index.fromZeroBased(getTypicalPersons().size() + 1).getOneBased());
        String expectedMessage = String.format(MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX, invalidIndexes);
        List<Index> indexes = List.of(Index.fromZeroBased(getTypicalPersons().size() + 1));
        SelectCommand selectCommand = new SelectCommand(indexes);
        assertCommandFailure(selectCommand, model, expectedMessage);
        assertThrows(CommandException.class, () -> selectCommand.execute(model));
    }

    @Test
    public void whenIndexIsOutOfBounds_thenThrowsException() {}

    @Test
    public void formatIndexesMethod_multipleIndexes_returnsFormattedString() throws Exception {
        // Prepare test data, using indexes 0, 2, 7
        List<Index> indexes = List.of(Index.fromZeroBased(0),
                Index.fromZeroBased(2), Index.fromZeroBased(7));

        Method method = SelectCommand.class.getDeclaredMethod("formatIndexes", List.class);
        method.setAccessible(true);

        String result = (String) method.invoke(null, indexes);
        String expectedString = "1, 3, 8";
        assertEquals(expectedString, result);
    }

    @Test
    public void testFormatIndexesMethod_emptyIndexes_returnsEmptyString() throws Exception {
        List<Index> indexes = List.of();

        Method method = SelectCommand.class.getDeclaredMethod("formatIndexes", List.class);
        method.setAccessible(true);

        String result = (String) method.invoke(null, indexes);
        String expectedString = "";
        assertEquals(expectedString, result);
    }

    @Test
    public void testFormatSelectedPersons_multiplePersons_returnsFormattedString() throws Exception {
        // Prepare test data, using 2, 7th person from the typical person list
        List<Index> indexes = List.of(Index.fromZeroBased(2), Index.fromZeroBased(7));
        List<Person> persons = prepareSelectedPersons(indexes);

        Method method = SelectCommand.class.getDeclaredMethod("formatSelectedPersons", List.class);
        method.setAccessible(true);

        StringBuilder expectedString = new StringBuilder();

        // Iterate over each person in the list
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Name name = person.getName();
            expectedString.append(name.fullName);

            // If it's not the last person, append a comma and space
            if (i < persons.size() - 1) {
                expectedString.append(", ");
            }
        }

        String result = (String) method.invoke(null, persons);
        assertEquals(expectedString.toString(), result);
    }

    @Test
    public void testFormatSelectedPersons_emptyPersonsList_returnsEmptyString() throws Exception {
        // Prepare test data, using an empty person list
        List<Index> indexes = List.of();
        List<Person> persons = prepareSelectedPersons(indexes);

        Method method = SelectCommand.class.getDeclaredMethod("formatSelectedPersons", List.class);
        method.setAccessible(true);

        String result = (String) method.invoke(null, persons);
        String expectedString = "none";
        assertEquals(expectedString, result);
    }

    @Test
    public void toStringMethod() {
        List<Index> indexes = List.of(Index.fromZeroBased(0));
        SelectCommand command = new SelectCommand(indexes);
        String expected = SelectCommand.class.getCanonicalName() + "{indexes=" + indexes + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Prepares a list of selected persons based on the provided list of indexes.
     *
     * @param indexes The list of indexes representing persons to be selected.
     * @return A list of {@code Person} objects corresponding to the specified indexes in the typical persons list.
     * @throws IndexOutOfBoundsException if any index is out of bounds of the typical persons list.
     */
    public List<Person> prepareSelectedPersons(List<Index> indexes) {
        List<Person> persons = new ArrayList<>();

        for (Index index : indexes) {
            persons.add(getTypicalPersons().get(index.getZeroBased()));
        }

        return persons;
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

    /**
     * Formats a list of selected persons' names into a comma-separated string for display.
     *
     * @param persons The list of selected persons.
     * @return A comma-separated string of selected persons' names, or "none" if the list is empty.
     */
    private String formatSelectedPersons(List<Person> persons) {
        return persons.stream()
                .map(person -> person.getName().toString()) // Convert Name object to String
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none"); // Fallback if no persons are selected
    }
}
