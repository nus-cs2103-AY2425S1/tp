package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GEORGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Set<Tag> stubTagList = new HashSet<>();

    @Test
    public void execute_deleteTagUnfilteredList_success() {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(VALID_NAME_GEORGE))
                .toList();

        Person firstPerson = matchingPersons.get(0);
        Person originalPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND).build();
        Person editedPerson = new PersonBuilder(firstPerson).build();

        stubTagList.add(new Tag(VALID_TAG_FRIEND));
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(originalPerson.getName(), stubTagList);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {

        Tag tag1 = new Tag(VALID_TAG_AMY);
        Tag tag2 = new Tag(VALID_TAG_FRIEND);
        Name name = new Name(VALID_NAME_AMY);
        Set<Tag> standardTagList = new HashSet<>();
        Set<Tag> deleteTagList = new HashSet<>();
        standardTagList.add(tag1);
        standardTagList.add(tag2);
        deleteTagList.add(tag2);
        final TagAddCommand standardCommand = new TagAddCommand(name, standardTagList);
        final TagDeleteCommand standardDeleteCommand = new TagDeleteCommand(name, deleteTagList);

        // same values -> returns true
        Tag stubTag1 = new Tag(VALID_TAG_AMY);
        Tag stubTag2 = new Tag(VALID_TAG_FRIEND);
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> stubTagList = new HashSet<>();
        Set<Tag> deleteStubTagList = new HashSet<>();
        stubTagList.add(stubTag1);
        stubTagList.add(stubTag2);
        deleteStubTagList.add(tag2);
        TagAddCommand commandWithSameValues = new TagAddCommand(stubName, stubTagList);
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
        Tag newStubTag2 = new Tag(VALID_TAG_FRIEND);
        Set<Tag> newStubTagList = new HashSet<>();
        Set<Tag> newDeleteStubTagList = new HashSet<>();
        newStubTagList.add(newStubTag1);
        newStubTagList.add(newStubTag2);
        deleteStubTagList.add(newStubTag1);
        TagAddCommand commandWithDifferentValues = new TagAddCommand(stubName, newStubTagList);
        TagDeleteCommand deleteCommandWithDifferentValues = new TagDeleteCommand(stubName, newDeleteStubTagList);
        assertFalse(standardDeleteCommand.equals(deleteCommandWithDifferentValues));
    }

}
