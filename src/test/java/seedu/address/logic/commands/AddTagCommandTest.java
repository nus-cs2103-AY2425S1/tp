package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class AddTagCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
        expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int startingModelSize = model.getCampusConnect().getPersonList().size();
        Set<Tag> tags = new HashSet<>(List.of(new Tag("sample")));

        // index >= size of PersonList
        Index index = Index.fromZeroBased(startingModelSize);
        AddTagCommand addTagCommand = new AddTagCommand(index, tags);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Set<Tag> tags = new HashSet<>(List.of(new Tag("friends"), new Tag("owesMoney")));

        Index indexOne = Index.fromZeroBased(0);
        Index indexTwo = Index.fromZeroBased(1);

        // one conflict: one duplicate tag
        // two conflict: both tags are duplicates
        AddTagCommand addTagCommandOneConflict = new AddTagCommand(indexOne, tags);
        AddTagCommand addTagCommandTwoConflict = new AddTagCommand(indexTwo, tags);

        String expectedMessageOne = String.format(AddTagCommand.MESSAGE_CONTAINS_DUPLICATE_TAG,
                Messages.format(ALICE));
        String expectedMessageTwo = String.format(AddTagCommand.MESSAGE_CONTAINS_DUPLICATE_TAG,
                Messages.format(BENSON));

        assertCommandFailure(addTagCommandOneConflict, model, expectedMessageOne);
        assertCommandFailure(addTagCommandTwoConflict, model, expectedMessageTwo);
    }

    @Test
    public void execute_validTagsAndIndex_tagsAdded() {
        AddTagCommand addTagCommand;
        String expectedMessage;

        Set<Tag> oneTag = new HashSet<>(List.of(new Tag("colleagues")));
        Set<Tag> twoTags = new HashSet<>(
                List.of(new Tag("CS2100"), new Tag("classmate")));

        Index indexOne = Index.fromZeroBased(0);
        Index indexLast = Index.fromZeroBased(6);

        // add one valid tag to person with pre-existing tag
        addTagCommand = new AddTagCommand(indexOne, oneTag);
        expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(ALICE));
        expectedModel.addPersonTags(ALICE, oneTag);
        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);

        // add multiple valid tags to person without pre-existing tag
        addTagCommand = new AddTagCommand(indexLast, twoTags);
        expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(GEORGE));
        expectedModel.addPersonTags(GEORGE, twoTags);
        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index indexZero = Index.fromZeroBased(0);
        Index indexOne = Index.fromZeroBased(1);

        Set<Tag> tagsZero = new HashSet<>(List.of(new Tag("sample")));
        Set<Tag> tagsOne = new HashSet<>(List.of(new Tag("sample"), new Tag("honkydonky")));

        AddTagCommand addTagZeroCommand = new AddTagCommand(indexZero, tagsZero);
        AddTagCommand addTagOneCommand;

        // same object -> returns true
        assertEquals(addTagZeroCommand, addTagZeroCommand);

        // same values -> returns true
        AddTagCommand addTagZeroCommandCopy = new AddTagCommand(indexZero, tagsZero);
        assertEquals(addTagZeroCommand, addTagZeroCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addTagZeroCommand);

        // null -> returns false
        assertNotEquals(null, addTagZeroCommand);

        // different index -> returns false
        addTagOneCommand = new AddTagCommand(indexOne, tagsZero);
        assertNotEquals(addTagZeroCommand, addTagOneCommand);

        // different tags -> returns false
        addTagOneCommand = new AddTagCommand(indexZero, tagsOne);
        assertNotEquals(addTagZeroCommand, addTagOneCommand);
    }
}

