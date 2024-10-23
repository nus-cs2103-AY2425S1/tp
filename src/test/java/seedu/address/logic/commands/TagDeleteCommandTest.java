package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OWESMONEY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    private Set<Tag> stubTagList = new HashSet<>();

    @BeforeEach
    public void init() {
        stubTagList.clear();
    }

    @Test
    public void execute_allTagsMatch_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person originalPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIENDS).build();
        Person editedPerson = new PersonBuilder(firstPerson).withTags().build();

        stubTagList.add(new Tag(VALID_TAG_FRIENDS));
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(originalPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.tagSetToString(stubTagList), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneTagMatch_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person originalPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIENDS).build();
        Person editedPerson = new PersonBuilder(firstPerson).withTags().build();

        stubTagList.add(new Tag(VALID_TAG_FRIENDS));
        stubTagList.add(new Tag(VALID_TAG_HUSBAND));
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(originalPerson.getName(), stubTagList);

        String expectedMessage1 = String.format(TagDeleteCommand.MESSAGE_TAG_DOESNT_EXIST + "\n",
                VALID_TAG_HUSBAND, Messages.format(originalPerson));
        String expectedMessage2 = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS,
                VALID_TAG_FRIENDS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage1 + expectedMessage2,
                expectedModel);
    }

    @Test
    public void execute_noTagMatch_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person originalPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIENDS).build();

        stubTagList.add(new Tag(VALID_TAG_HUSBAND));
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(originalPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_TAG_DOESNT_EXIST,
                Messages.tagSetToString(stubTagList), Messages.format(originalPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, originalPerson);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleTagMatch_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_BENSON))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person originalPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIENDS, VALID_TAG_OWESMONEY).build();
        Person editedPerson = new PersonBuilder(firstPerson).withTags().build();

        stubTagList.add(new Tag(VALID_TAG_FRIENDS));
        stubTagList.add(new Tag(VALID_TAG_OWESMONEY));
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(originalPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.tagSetToString(stubTagList), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_nameMismatch_exceptionThrown() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person wrongPerson = new PersonBuilder(firstPerson).withName(VALID_NAME_AMY).build();
        stubTagList.add(new Tag(VALID_TAG_FRIENDS));

        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(wrongPerson.getName(), stubTagList);

        assertThrows(CommandException.class, String.format(TagDeleteCommand.MESSAGE_PERSON_DOESNT_EXIST,
                        wrongPerson.getName()), () -> tagDeleteCommand.execute(model));
    }

    @Test
    public void equals() {

        Tag tag2 = new Tag(VALID_TAG_FRIENDS);
        Name name = new Name(VALID_NAME_AMY);
        Set<Tag> deleteTagList = new HashSet<>();
        deleteTagList.add(tag2);
        final TagDeleteCommand standardDeleteCommand = new TagDeleteCommand(name, deleteTagList);

        // same values -> returns true
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> deleteStubTagList = new HashSet<>();
        deleteStubTagList.add(tag2);
        TagDeleteCommand deleteCommandWithSameValues = new TagDeleteCommand(name, deleteStubTagList);
        assertTrue(standardDeleteCommand.equals(deleteCommandWithSameValues));

        // same object -> returns true
        assertTrue(standardDeleteCommand.equals(standardDeleteCommand));

        // null -> returns false
        assertFalse(standardDeleteCommand.equals(null));

        // different types -> returns false
        assertFalse(standardDeleteCommand.equals(new ClearCommand()));

        // different tag -> returns false
        Tag newStubTag1 = new Tag(VALID_TAG_BOB);
        Set<Tag> newDeleteStubTagList = new HashSet<>();
        deleteStubTagList.add(newStubTag1);
        TagDeleteCommand deleteCommandWithDifferentValues = new TagDeleteCommand(stubName, newDeleteStubTagList);
        assertFalse(standardDeleteCommand.equals(deleteCommandWithDifferentValues));
    }

}
