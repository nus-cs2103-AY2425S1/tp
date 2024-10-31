package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
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
        Set<String> firstNames = new HashSet<>(Arrays.asList("first"));
        Set<String> secondNames = new HashSet<>(Arrays.asList("second"));
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("friend")));

        FilterCommand filterFirstCommand = new FilterCommand(firstNames, new HashSet<>());
        FilterCommand filterSecondCommand = new FilterCommand(secondNames, tags);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstNames, new HashSet<>());
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_nameKeyword_personsFound() {
        Set<String> names = new HashSet<>(Arrays.asList("Meier"));
        String expectedMessage = FilterCommand.constructSuccessMessage(names, new HashSet<>());
        FilterCommand command = new FilterCommand(names, new HashSet<>());
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_tagKeyword_personsFound() {
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("owesMoney")));
        String expectedMessage = FilterCommand.constructSuccessMessage(new HashSet<>(), tags);
        FilterCommand command = new FilterCommand(new HashSet<>(), tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndTagKeywords_personsFound() {
        Set<String> names = new HashSet<>(Arrays.asList("Meier"));
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("friends")));
        String expectedMessage = FilterCommand.constructSuccessMessage(names, tags);
        FilterCommand command = new FilterCommand(names, tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchingNameOrTag_allPersonsReturned() {
        Set<String> names = new HashSet<>(Arrays.asList("Damith"));
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("2103")));
        String expectedMessage = FilterCommand.MESSAGE_NO_CONTACT_FOUND;
        FilterCommand command = new FilterCommand(names, tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialNameMatch_personsFound() {
        Set<String> names = new HashSet<>(Arrays.asList("er"));
        String expectedMessage = FilterCommand.constructSuccessMessage(names, new HashSet<>());
        FilterCommand command = new FilterCommand(names, new HashSet<>());
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNames_personsFound() {
        Set<String> names = new HashSet<>(Arrays.asList("Meier", "Kurz"));
        String expectedMessage = FilterCommand.constructSuccessMessage(names, new HashSet<>());
        FilterCommand command = new FilterCommand(names, new HashSet<>());
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTags_matchesAllTags() {
        Set<Tag> tags = new HashSet<>(Arrays.asList(
                new Tag("friends"),
                new Tag("owesMoney")
        ));
        String expectedMessage = FilterCommand.constructSuccessMessage(new HashSet<>(), tags);
        FilterCommand command = new FilterCommand(new HashSet<>(), tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        // Should only return BENSON who has both tags
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTags_excludesPartialMatches() {
        Set<Tag> tags = new HashSet<>(Arrays.asList(
                new Tag("friends"),
                new Tag("owesMoney"),
                new Tag("client")
        ));
        String expectedMessage = FilterCommand.MESSAGE_NO_CONTACT_FOUND;
        FilterCommand command = new FilterCommand(new HashSet<>(), tags);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        // Should return empty list since no one has all three tags
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<String> names = new HashSet<>(Arrays.asList("John", "Doe"));
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("client")));
        FilterCommand filterCommand = new FilterCommand(names, tags);
        String expected = FilterCommand.class.getCanonicalName() + "{names=[John, Doe], tags=[[client]]}";
        assertEquals(expected, filterCommand.toString());
    }
}
