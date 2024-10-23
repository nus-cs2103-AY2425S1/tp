package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.tagSetToString;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DANIEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GEORGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    private Set<Tag> stubTagList = new HashSet<>();

    @BeforeEach
    public void init() {
        stubTagList.clear();
    }

    @Test
    public void execute_noDuplicateTags_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_GEORGE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND).build();

        stubTagList.add(new Tag(VALID_TAG_FRIEND));
        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_ADD_TAG_SUCCESS,
                Messages.tagSetToString(stubTagList), Messages.getName(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allDuplicateTags_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).build();

        stubTagList.add(new Tag(VALID_TAG_FRIENDS));

        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_DUPLICATE_TAGS,
                Messages.getName(editedPerson), tagSetToString(stubTagList));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someDuplicateTags_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_DANIEL))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIENDS, VALID_TAG_HUSBAND).build();

        stubTagList.add(new Tag(VALID_TAG_FRIENDS));
        stubTagList.add(new Tag(VALID_TAG_HUSBAND));

        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String nonDuplicateMessage = String.format(TagAddCommand.MESSAGE_ADD_TAG_SUCCESS + "\n",
                VALID_TAG_HUSBAND, Messages.getName(editedPerson));
        String duplicatesMessage = String.format(TagAddCommand.MESSAGE_DUPLICATE_TAGS,
                Messages.getName(editedPerson), VALID_TAG_FRIENDS);
        String expectedMessage = nonDuplicateMessage + duplicatesMessage;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatchingPerson_throwsCommandException() {
        TagAddCommand tagAddCommand = new TagAddCommand(new Name(INVALID_NAME), stubTagList);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_PERSON_DOESNT_EXIST, INVALID_NAME);
        assertCommandFailure(tagAddCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        Tag tag = new Tag(VALID_TAG_AMY);
        Name name = new Name(VALID_NAME_AMY);
        Set<Tag> standardTagList = new HashSet<>();
        standardTagList.add(tag);
        final TagAddCommand standardCommand = new TagAddCommand(name, standardTagList);

        // same values -> returns true
        Tag stubTag = new Tag(VALID_TAG_AMY);
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> stubTagList = new HashSet<>();
        stubTagList.add(stubTag);
        TagAddCommand commandWithSameValues = new TagAddCommand(stubName, stubTagList);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different tag -> returns false
        Tag newStubTag = new Tag(VALID_TAG_BOB);
        Set<Tag> newStubTagList = new HashSet<>();
        newStubTagList.add(newStubTag);
        TagAddCommand commandWithDifferentValues = new TagAddCommand(stubName, newStubTagList);
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

}
