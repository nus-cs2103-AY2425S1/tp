package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_oneTagFilteredList_success() {
        Index index = INDEX_FIRST_PERSON;
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagSet = SampleDataUtil.getTagSet("testTag1");
        TagCommand tagCommand = new TagCommand(index, tagSet);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        Person taggedPerson = new PersonBuilder(firstPerson).withTags("friends", "testTag1").build();
        expectedModel.setPerson(firstPerson, taggedPerson);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS,
                taggedPerson.getName(), Tag.tagSetToString(tagSet));

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleTagsFilteredList_success() {
        Index index = INDEX_FIRST_PERSON;
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagSet = SampleDataUtil.getTagSet("testTag1", "testTag2", "testTag3");
        TagCommand tagCommand = new TagCommand(index, tagSet);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        Person taggedPerson = new PersonBuilder(firstPerson)
                .withTags("friends", "testTag1", "testTag2", "testTag3").build();

        expectedModel.setPerson(firstPerson, taggedPerson);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS,
                taggedPerson.getName(), Tag.tagSetToString(tagSet));

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tagList = SampleDataUtil.getTagSet();
        TagCommand tagCommand = new TagCommand(outOfBoundsIndex, tagList);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_excessiveTagsFilteredList_failure() {
        Index index = INDEX_FIRST_PERSON;

        Set<Tag> tagSet = SampleDataUtil.getTagSet("tag1", "tag2", "tag3", "tag4", "tag5", "tag6");
        TagCommand command = new TagCommand(index, tagSet);
        String expectedMessage = "A contact can only have up to 6 tags";
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_duplicateTagsUnfilteredList_failure() {
        Index index = INDEX_FIRST_PERSON;
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends"); //friends tag already exists in first person
        TagCommand command = new TagCommand(index, tagSet);
        String expectedMessage = "Person already has that Tag!";
        assertCommandFailure(command, model, expectedMessage);
    }
}
