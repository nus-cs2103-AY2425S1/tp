package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().withTags("John Loh & Jean Tan").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        Name currentName = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName();
        Name newName = editedPerson.getName();
        EditCommand editCommand = new EditCommand(currentName, newName, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        Name currentName = lastPerson.getName();
        Name newName = editedPerson.getName();
        EditCommand editCommand = new EditCommand(currentName, newName, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name currentName = personToEdit.getName();
        EditCommand editCommand = new EditCommand(currentName, null, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        Name currentName = personInFilteredList.getName();
        Name newName = editedPerson.getName();
        EditCommand editCommand = new EditCommand(currentName, newName,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        Name currentName = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getName();
        EditCommand editCommand = new EditCommand(currentName, firstPerson.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Name currentName = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName();
        EditCommand editCommand = new EditCommand(currentName, personInList.getName(),
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new Name("Alice"), new Name("Alice"), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new Name("Alice"), new Name("Alice"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearAddressBookCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new Name("Bob"), new Name("Alice"), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new Name("Alice"), new Name("Alice"), DESC_BOB)));
    }

    @Test
    public void createEditedPerson_nullPerson_throwsAssertionError() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        Method createEditedPersonMethod = EditCommand.class.getDeclaredMethod("createEditedPerson",
                Person.class, Name.class, EditPersonDescriptor.class);
        createEditedPersonMethod.setAccessible(true);
        assertThrows(InvocationTargetException.class, () ->
                createEditedPersonMethod.invoke(null, null, new Name(VALID_NAME_BOB), descriptor));
    }

    @Test
    public void equals_newNameComparison_success() {
        EditCommand commandWithNewName = new EditCommand(new Name("Alice"), new Name("Bob"), DESC_AMY);
        EditCommand commandWithSameNewName = new EditCommand(new Name("Alice"), new Name("Bob"), DESC_AMY);
        assertTrue(commandWithNewName.equals(commandWithSameNewName));
    }

    @Test
    public void equals_jobComparison_success() {
        EditPersonDescriptor descriptorWithJob = new EditPersonDescriptorBuilder().withJob(VALID_JOB_BOB).build();
        EditPersonDescriptor sameDescriptorWithJob = new EditPersonDescriptorBuilder().withJob(VALID_JOB_BOB).build();
        assertTrue(descriptorWithJob.equals(sameDescriptorWithJob));
    }

    @Test
    public void equals_differentNewName_returnsFalse() {
        EditCommand commandWithNewName = new EditCommand(new Name("Alice"), new Name("Bob"), DESC_AMY);
        EditCommand commandWithDifferentNewName = new EditCommand(new Name("Alice"), new Name("Charlie"), DESC_AMY);
        assertFalse(commandWithNewName.equals(commandWithDifferentNewName));
    }

    @Test
    public void equals_differentJob_returnsFalse() {
        EditPersonDescriptor descriptorWithJob = new EditPersonDescriptorBuilder().withJob(VALID_JOB_BOB).build();
        EditPersonDescriptor differentDescriptorWithJob = new EditPersonDescriptorBuilder()
                .withJob("Different Job").build();
        assertFalse(descriptorWithJob.equals(differentDescriptorWithJob));
    }

    @Test
    public void toStringMethod() {
        Name currentName = new Name("Alice");
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(currentName, null, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{currentName=" + currentName + ", newName=null, "
                + "editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
