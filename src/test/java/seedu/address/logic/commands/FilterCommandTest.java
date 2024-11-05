package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INEXISTENT_TAG_BESTFRIEND;
import static seedu.address.logic.commands.CommandTestUtil.INEXISTENT_TAG_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OWES_MONEY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;


public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        Set<Tag> friendTag = new HashSet<>();
        friendTag.add(new Tag(VALID_TAG_FRIEND));

        Set<Tag> husbandTag = new HashSet<>();
        husbandTag.add(new Tag(VALID_TAG_HUSBAND));

        Set<Tag> husbandFriendTag = new HashSet<>();
        husbandFriendTag.add(new Tag(VALID_TAG_HUSBAND));
        husbandFriendTag.add(new Tag(VALID_TAG_HUSBAND));

        FilterCommand filterFirstCommand = new FilterCommand(friendTag);
        FilterCommand filterSecondCommand = new FilterCommand(husbandTag);
        FilterCommand filterThirdCommand = new FilterCommand(husbandFriendTag);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));
        assertTrue(filterThirdCommand.equals(filterThirdCommand));
        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(friendTag);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));
        FilterCommand filterThirdCommandCopy = new FilterCommand(husbandFriendTag);
        assertTrue(filterThirdCommand.equals(filterThirdCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
        assertFalse(filterFirstCommand.equals(filterThirdCommand));
    }

    @Test
    public void execute_noTagFilteredFor_showsNothing() {
        expectedModel.updateFilteredPersonList(person -> false);
        //single tag
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(INEXISTENT_TAG_BESTFRIEND));
        FilterCommand bestFriendFilterCommand = new FilterCommand(tags);
        assertCommandSuccess(
                bestFriendFilterCommand,
                model,
                String.format(FilterCommand.MESSAGE_NOT_FOUND, INEXISTENT_TAG_BESTFRIEND),
                expectedModel
        );
        //multiple tags
        tags.add(new Tag(INEXISTENT_TAG_COLLEAGUE));
        FilterCommand multipleFilterCommand = new FilterCommand(tags);
        assertCommandSuccess(
                multipleFilterCommand,
                model,
                String.format(FilterCommand.MESSAGE_NOT_FOUND, INEXISTENT_TAG_BESTFRIEND
                        + ", " + INEXISTENT_TAG_COLLEAGUE),
                expectedModel
        );
    }

    @Test
    public void execute_tagFilteredForPresent_showsFiltered() {
        //single tag
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_FRIEND));
        FilterCommand friendFilterCommand = new FilterCommand(tags);
        expectedModel.updateFilteredPersonList(
                person -> person.getTags().contains(new Tag(VALID_TAG_FRIEND))
        );
        assertCommandSuccess(
                friendFilterCommand,
                model,
                String.format(FilterCommand.MESSAGE_SUCCESS, VALID_TAG_FRIEND),
                expectedModel
        );
        //multiple tags
        tags.add(new Tag(VALID_TAG_OWES_MONEY));
        FilterCommand multipleFilterCommand = new FilterCommand(tags);
        expectedModel.updateFilteredPersonList(
                person -> person.getTags().contains(new Tag(VALID_TAG_FRIEND))
                    && person.getTags().contains(new Tag(VALID_TAG_OWES_MONEY))
        );
        assertCommandSuccess(
                multipleFilterCommand,
                model,
                String.format(FilterCommand.MESSAGE_SUCCESS, VALID_TAG_OWES_MONEY + ", " + VALID_TAG_FRIEND),
                expectedModel
        );
    }
}

