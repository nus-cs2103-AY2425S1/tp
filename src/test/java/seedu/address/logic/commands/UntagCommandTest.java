package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
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

public class UntagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_multipleTagRemoval_success() {
        Index index = INDEX_SECOND_PERSON;
        Person personToEdit = model.getFilteredPersonList().get(index.getZeroBased());
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "owesMoney");
        UntagCommand untagCommand = new UntagCommand(index, tagSet);


        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build(); // Assuming no remaining tags
        expectedModel.setPerson(personToEdit, editedPerson);

        String expectedMessage = String.format(UntagCommand.MESSAGE_UNTAG_SUCCESS + ". Tags removed: %2$s",
                personToEdit.getName(), "[owesMoney], [friends]");

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeAllTags_success() {
        Index index = INDEX_FIRST_PERSON;
        Person personToEdit = model.getFilteredPersonList().get(index.getZeroBased());
        UntagCommand untagCommand = new UntagCommand(index, null);

        // Expected Model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build(); // No tags after removal
        expectedModel.setPerson(personToEdit, editedPerson);

        String expectedMessage = String.format(UntagCommand.MESSAGE_UNTAG_SUCCESS + ". Tags removed: %2$s",
                personToEdit.getName(), "[friends]");

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTagRemoval_throwsCommandException() {
        Index index = INDEX_SECOND_PERSON;
        Set<Tag> nonExistentTagSet = SampleDataUtil.getTagSet("nonExistentTag");
        UntagCommand untagCommand = new UntagCommand(index, nonExistentTagSet);

        String expectedMessage = "Error: The following tags do not exist: nonExistentTag";
        assertCommandFailure(untagCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends");
        UntagCommand untagCommand = new UntagCommand(outOfBoundsIndex, tagSet);

        assertCommandFailure(untagCommand, model, "Error: " + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ".");
    }

}
