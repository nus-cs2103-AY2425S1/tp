package tahub.contacts.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tahub.contacts.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tahub.contacts.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.core.index.Index;
import tahub.contacts.logic.Messages;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PersonDeleteCommand}.
 */
public class PersonDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UniqueCourseList(),
            new StudentCourseAssociationList());

    @Test
    public void execute_validMatricNumberUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonDeleteCommand personDeleteCommand = new PersonDeleteCommand(personToDelete.getMatricNumber());

        String expectedMessage = String.format(PersonDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getCourseList(),
                model.getScaList());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(personDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMatricNumberUnfilteredList_throwsCommandException() {
        MatriculationNumber invalidMatricNumber = new MatriculationNumber("A0000000X");
        PersonDeleteCommand personDeleteCommand = new PersonDeleteCommand(invalidMatricNumber);

        assertCommandFailure(personDeleteCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_validMatricNumberFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonDeleteCommand personDeleteCommand = new PersonDeleteCommand(personToDelete.getMatricNumber());

        String expectedMessage = String.format(PersonDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getCourseList(),
                model.getScaList());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(personDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMatricNumberFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MatriculationNumber invalidMatricNumber = new MatriculationNumber("A0000000X");
        PersonDeleteCommand personDeleteCommand = new PersonDeleteCommand(invalidMatricNumber);

        assertCommandFailure(personDeleteCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        PersonDeleteCommand deleteFirstCommand = new PersonDeleteCommand(firstPerson.getMatricNumber());
        PersonDeleteCommand deleteSecondCommand = new PersonDeleteCommand(secondPerson.getMatricNumber());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        PersonDeleteCommand deleteFirstCommandCopy = new PersonDeleteCommand(firstPerson.getMatricNumber());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Person personToDelete = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        PersonDeleteCommand personDeleteCommand = new PersonDeleteCommand(personToDelete.getMatricNumber());
        String expected = PersonDeleteCommand.class.getCanonicalName()
                + "{matriculationNumber=" + personToDelete.getMatricNumber() + "}";
        assertEquals(expected, personDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
