package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.tagSetToString;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GEORGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING4;
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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

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
        Person editedPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_WEDDING1).build();

        stubTagList.add(new Tag(VALID_TAG_WEDDING1));
        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format("\n" + TagAddCommand.MESSAGE_ADD_TAG_SUCCESS,
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

        stubTagList.add(new Tag(VALID_TAG_WEDDING1));

        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format("\n" + TagAddCommand.MESSAGE_DUPLICATE_TAGS,
                Messages.getName(editedPerson), tagSetToString(stubTagList));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someDuplicateTagsandNoWedding_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_WEDDING2, VALID_TAG_WEDDING1).build();

        stubTagList.add(new Tag(VALID_TAG_WEDDING2));
        stubTagList.add(new Tag(VALID_TAG_WEDDING1));

        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String nonDuplicateMessage = String.format(TagAddCommand.MESSAGE_WEDDING_DOESNT_EXIST + "\n",
                VALID_TAG_WEDDING2, VALID_TAG_WEDDING2);
        String duplicatesMessage = String.format(TagAddCommand.MESSAGE_DUPLICATE_TAGS,
                Messages.getName(editedPerson), VALID_TAG_WEDDING1);
        String expectedMessage = nonDuplicateMessage + duplicatesMessage;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasWedding_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_GEORGE))
                .toList();

        List<Wedding> matchingWeddings = model.getFilteredWeddingList().stream()
                .filter(wedding -> wedding.getWeddingName().toString().equals(VALID_TAG_WEDDING1))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Wedding firstWedding = matchingWeddings.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_WEDDING1).build();
        Wedding editedWedding = new WeddingBuilder(firstWedding).withParticipant(editedPerson).build();
        stubTagList.add(new Tag(VALID_TAG_WEDDING1));
        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format("\n" + TagAddCommand.MESSAGE_ADD_TAG_SUCCESS,
                Messages.tagSetToString(stubTagList), Messages.getName(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new WeddingBook(model.getWeddingBook()));
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.setWedding(firstWedding, editedWedding);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noWedding_success() throws CommandException {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_ALICE))
                .toList();


        Person firstPerson = matchingPersons.get(0);
        Person editedPerson = new PersonBuilder(firstPerson).build();
        stubTagList.add(new Tag(VALID_TAG_WEDDING3));

        TagAddCommand tagAddCommand = new TagAddCommand(editedPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_WEDDING_DOESNT_EXIST,
                tagSetToString(stubTagList), tagSetToString(stubTagList));

        assertCommandFailure(tagAddCommand, model, expectedMessage);
    }

    @Test
    public void execute_noMatchingPerson_throwsCommandException() {
        TagAddCommand tagAddCommand = new TagAddCommand(new Name(INVALID_NAME), stubTagList);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_PERSON_DOESNT_EXIST, INVALID_NAME);
        assertCommandFailure(tagAddCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        Tag tag = new Tag(VALID_TAG_WEDDING3);
        Name name = new Name(VALID_NAME_AMY);
        Set<Tag> standardTagList = new HashSet<>();
        standardTagList.add(tag);
        final TagAddCommand standardCommand = new TagAddCommand(name, standardTagList);

        // same values -> returns true
        Tag stubTag = new Tag(VALID_TAG_WEDDING3);
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
        Tag newStubTag = new Tag(VALID_TAG_WEDDING4);
        Set<Tag> newStubTagList = new HashSet<>();
        newStubTagList.add(newStubTag);
        TagAddCommand commandWithDifferentValues = new TagAddCommand(stubName, newStubTagList);
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

}
