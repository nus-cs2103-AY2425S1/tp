package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
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

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friend"));
        FilterCommand filterFirstCommand = new FilterCommand("first", null);
        FilterCommand filterSecondCommand = new FilterCommand(null, expectedTags);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand("first", null);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    // Test case for filtering using name only
    @Test
    public void execute_nameKeyword_personsFound() {
        Set<Tag> expectedTags = new HashSet<>();
        String expectedMessage = FilterCommand.constructSuccessMessage("Meier", expectedTags);
        FilterCommand command = new FilterCommand("Meier", expectedTags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    // Test case for filtering using tag only
    @Test
    public void execute_tagKeyword_personsFound() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("owesMoney"));
        String expectedMessage = FilterCommand.constructSuccessMessage(null, expectedTags);
        FilterCommand command = new FilterCommand(null, expectedTags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    // Test case for filtering using both name and tags
    @Test
    public void execute_nameAndTagKeywords_personsFound() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        String expectedMessage = FilterCommand.constructSuccessMessage("Meier", expectedTags);
        FilterCommand command = new FilterCommand("Meier", expectedTags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    // Test case for when there is no one after filtering
    @Test
    public void execute_noMatchingNameOrTag_allPersonsReturned() {
        String expectedMessage = FilterCommand.MESSAGE_NO_CONTACT_FOUND;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("2103"));
        FilterCommand command = new FilterCommand("Damith", tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    // Test case to test partial string filtering
    @Test
    public void execute_partialNameMatch_personsFound() {
        Set<Tag> expectedTags = new HashSet<>();
        String expectedMessage = FilterCommand.constructSuccessMessage("er", expectedTags);
        FilterCommand command = new FilterCommand("er", expectedTags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("client"));
        FilterCommand filterCommand = new FilterCommand("John", tags);
        String expected = FilterCommand.class.getCanonicalName() + "{name=John, tags=[[client]]}";
        assertEquals(expected, filterCommand.toString());
    }
}
