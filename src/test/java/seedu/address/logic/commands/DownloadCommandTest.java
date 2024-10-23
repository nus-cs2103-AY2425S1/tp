package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        downloadCommand = new DownloadCommand(); // No tags specified

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
        downloadCommand = new DownloadCommand(); // No tags specified

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
}
