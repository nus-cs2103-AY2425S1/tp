package tahub.contacts.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.commands.CommandTestUtil.DESC_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tahub.contacts.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tahub.contacts.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.core.index.Index;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.ClearCommand;
import tahub.contacts.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
import tahub.contacts.model.AddressBook;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.testutil.EditPersonDescriptorBuilder;
import tahub.contacts.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class PersonEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UniqueCourseList(),
            new StudentCourseAssociationList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = model.getFilteredPersonList().get(0);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        PersonEditCommand personEditCommand = new PersonEditCommand(editedPerson.getMatricNumber(), descriptor);

        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
                        model.getScaList());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        PersonEditCommand personEditCommand = new PersonEditCommand(lastPerson.getMatricNumber(), descriptor);

        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
                        model.getScaList());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonEditCommand personEditCommand = new PersonEditCommand(editedPerson.getMatricNumber(),
                new EditPersonDescriptor());

        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
                        model.getScaList());

        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        PersonEditCommand personEditCommand = new PersonEditCommand(editedPerson.getMatricNumber(),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
                        model.getScaList());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonMatricNumberUnfilteredList_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        MatriculationNumber invalidMatricNumber = new MatriculationNumber("A0000000X");
        PersonEditCommand personEditCommand = new PersonEditCommand(invalidMatricNumber, descriptor);

        assertCommandFailure(personEditCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }


    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonMatricNumberFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MatriculationNumber invalidMatricNumber = new MatriculationNumber("A0000000X");
        PersonEditCommand personEditCommand = new PersonEditCommand(invalidMatricNumber,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(personEditCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        final PersonEditCommand standardCommand = new PersonEditCommand(firstPerson.getMatricNumber(), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        PersonEditCommand commandWithSameValues = new PersonEditCommand(firstPerson.getMatricNumber(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different matric number -> returns false
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertFalse(standardCommand.equals(new PersonEditCommand(secondPerson.getMatricNumber(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new PersonEditCommand(firstPerson.getMatricNumber(), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Person person = model.getFilteredPersonList().get(index.getZeroBased());
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        PersonEditCommand personEditCommand = new PersonEditCommand(person.getMatricNumber(), editPersonDescriptor);
        String expected = PersonEditCommand.class.getCanonicalName()
                + "{matriculationNumber=" + person.getMatricNumber() + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, personEditCommand.toString());
    }

}
