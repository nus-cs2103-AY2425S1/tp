package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Tag;

public class TagAddCommandTest {

    // private Model model = new ModelManager(getTypicalAddressBook(), new
    // UserPrefs());


    // @Test
    // public void execute() {
    //     assertCommandFailure(new RemarkCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    //     final String remark = "Some remark";
    //     assertCommandFailure(new RemarkCommand(INDEX_FIRST_PERSON, remark), model,
    //             String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), remark));
    // }

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
