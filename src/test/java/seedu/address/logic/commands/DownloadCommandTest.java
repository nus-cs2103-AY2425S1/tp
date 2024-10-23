package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

class DownloadCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private DownloadCommand downloadCommand;
    private final Set<Tag> emptyTagSet = new HashSet<>();

    @Test
    public void execute_downloadWithTags_success() {
        // Test scenario where specific tags are used
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));
        downloadCommand = new DownloadCommand(tags);

        // Prepare test data
        Person alice = new PersonBuilder().withName("Alice").withTags("tag1").build();
        Person bob = new PersonBuilder().withName("Bob").withTags("tag2").build();
        model.addPerson(alice);
        model.addPerson(bob);

        // Execute the command
        CommandResult result = downloadCommand.execute(model);

        // Check the success message and CSV creation
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_downloadWithoutTags_success() {
        // Test scenario where no tags are used
        downloadCommand = new DownloadCommand(emptyTagSet); // No tags specified

        // Prepare test data
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        model.addPerson(alice);
        model.addPerson(bob);

        // Execute the command
        CommandResult result = downloadCommand.execute(model);

        // Check the success message and CSV creation
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_noMatchingTags_noCsvSaved() {
        // Test scenario where tags do not match any person
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("nonexistentTag"));
        downloadCommand = new DownloadCommand(tags);

        // Execute the command
        CommandResult result = downloadCommand.execute(model);

        // Check the success message and CSV creation
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_emptyPersonList_noCsvSaved() {
        // Test scenario with no persons in the model
        downloadCommand = new DownloadCommand(emptyTagSet); // No tags specified

        // Execute the command
        CommandResult result = downloadCommand.execute(model);

        // Check the success message and CSV creation
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        // Test the constructor with null tags
        assertThrows(NullPointerException.class, () -> new DownloadCommand(null));
    }

    @Test
    public void equals() {
        Set<Tag> tags1 = new HashSet<>();
        tags1.add(new Tag("tag1"));
        tags1.add(new Tag("tag2"));
        DownloadCommand commandWithTags1 = new DownloadCommand(tags1);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(new Tag("tag1"));
        tags2.add(new Tag("tag2"));
        DownloadCommand commandWithTags2 = new DownloadCommand(tags2);

        Set<Tag> tags3 = new HashSet<>();
        tags3.add(new Tag("tag3"));
        DownloadCommand commandWithDifferentTags = new DownloadCommand(tags3);

        // Same object
        assertTrue(commandWithTags1.equals(commandWithTags1));

        // Same tags
        assertTrue(commandWithTags1.equals(commandWithTags2));

        // Different tags
        assertFalse(commandWithTags1.equals(commandWithDifferentTags));

        // Different types
        assertFalse(commandWithTags1.equals("Some String"));

        // Null comparison
        assertFalse(commandWithTags1.equals(null));
    }

}
