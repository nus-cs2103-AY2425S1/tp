package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;
import seedu.address.ui.Ui.UiState;

public class TagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null, null));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person taggedPerson = model.getAddressBook().getPersonList().get(0);
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(taggedPerson).build();

        TagCommand tagCommand = new TagCommand(taggedPerson.getName(), descriptor);

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Optional<Person> personToTag = model.getAddressBook()
                .getPersonList()
                .stream()
                .filter(person -> person.getName().equals(taggedPerson.getName()))
                .findFirst();

        if (personToTag.isPresent()) {
            expectedModel.setPerson(personToTag.get(), taggedPerson);
        } else {
            throw new IllegalStateException("Person to tag not found");
        }
        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecificUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person taggedPerson = personInList
                .withName(VALID_NAME_BOB)
                .withSubjects(VALID_SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withSubjects(VALID_SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        TagCommand tagCommand = new TagCommand(lastPerson.getName(), descriptor);

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, taggedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person taggedPerson = model.getFilteredPersonList().get(0);
        TagCommand tagCommand = new TagCommand(taggedPerson.getName(), new UpdatePersonDescriptor());

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person taggedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        TagCommand tagCommand = new TagCommand(personInFilteredList.getName(),
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS,
                Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), taggedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Name invalidName = new Name("Skibidi");
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        TagCommand tagCommand = new TagCommand(invalidName, descriptor);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_STUDENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        TagCommand tagCommand = new TagCommand(new Name(VALID_NAME_BOB), DESC_BOB);

        UpdatePersonDescriptor copyDescriptor = new UpdatePersonDescriptor(DESC_BOB);
        TagCommand tagCommandWithSameValues = new TagCommand(new Name(VALID_NAME_BOB), copyDescriptor);
        assertEquals(tagCommand, tagCommandWithSameValues);

        assertEquals(tagCommand, tagCommand);

        assertNotEquals(tagCommand, null);

        assertNotEquals(tagCommand, new TagCommand(new Name(VALID_NAME_AMY), DESC_AMY));

        assertNotEquals(tagCommand, new TagCommand(new Name(VALID_NAME_BOB), DESC_AMY));
    }

    @Test
    public void toString_success() {
        Name name = new Name(VALID_NAME_BOB);
        UpdatePersonDescriptor updatePersonDescriptor = new UpdatePersonDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        TagCommand tagCommand = new TagCommand(name, updatePersonDescriptor);

        String expected =
                String.format("%s{name=%s, level=Optional[%s], subjects=Optional[[[%s]]]}",
                        TagCommand.class.getCanonicalName(), name, VALID_LEVEL_S4_NT, VALID_SUBJECT_MATH);

        assertEquals(expected, tagCommand.toString());
    }

}
