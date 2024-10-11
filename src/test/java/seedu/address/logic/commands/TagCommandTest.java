package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

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
import seedu.address.testutil.PersonUtil;


import java.util.Set;

public class TagCommandTest {

    Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
                taggedPerson.getName(), TagCommand.tagSetToString(tagSet));

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tagList = SampleDataUtil.getTagSet();
        TagCommand tagCommand = new TagCommand(outOfBoundsIndex, tagList);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
