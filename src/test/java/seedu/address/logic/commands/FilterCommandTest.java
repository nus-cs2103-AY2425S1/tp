package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        friendTag.add(new Tag("friends"));

        Set<Tag> colleaguesTag = new HashSet<>();
        colleaguesTag.add(new Tag("colleagues"));

        Set<Tag> colleaguesFriendTag = new HashSet<>();
        colleaguesFriendTag.add(new Tag("colleagues"));
        colleaguesFriendTag.add(new Tag("friends"));

        FilterCommand filterFirstCommand = new FilterCommand(friendTag);
        FilterCommand filterSecondCommand = new FilterCommand(colleaguesTag);
        FilterCommand filterThirdCommand = new FilterCommand(colleaguesFriendTag);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));
        assertTrue(filterThirdCommand.equals(filterThirdCommand));
        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(friendTag);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));
        FilterCommand filterThirdCommandCopy = new FilterCommand(colleaguesFriendTag);
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
        String[] absentTags = {
            "bestFriends",
            "Colleagues",
            "4Horsemen"
        };
        for (String absentTag : absentTags) {
            Set<Tag> tags = new HashSet<>();
            tags.add(new Tag(absentTag));
            FilterCommand filterCommand = new FilterCommand(tags);
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, absentTag),
                    expectedModel
            );
        }
    }

    @Test
    public void execute_tagFilteredForPresent_showsFiltered() {
        String[] presentTags = {
            "owesMoney",
            "friends"
        };
        for (String presentTag : presentTags) {
            Set<Tag> tags = new HashSet<>();
            tags.add(new Tag(presentTag));
            FilterCommand filterCommand = new FilterCommand(tags);
            expectedModel.updateFilteredPersonList(
                    person -> person.getTags().contains(new Tag(presentTag))
            );
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, presentTag),
                    expectedModel
            );
        }
    }
}
