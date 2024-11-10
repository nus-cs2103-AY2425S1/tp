package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class SortCommandTest {
    private Model model;

    private void addTagsWithValuesPersons(Model model) {
        Set<Tag> tags1 = new HashSet<>(List.of(new Tag("friends", "1")));
        Set<Tag> tags2 = new HashSet<>(List.of(new Tag("friends", "3")));
        Set<Tag> tags3 = new HashSet<>(List.of(new Tag("friends", "2")));
        Set<Tag> tags5 = new HashSet<>(List.of(new Tag("friends", "4")));

        model.addPerson(new Person(new Name("Alice"), new Phone("12345678"), new Email("alice@example.com"),
                new Address("123 Main St"), tags1, "Finance Info 1", "@alice"));
        model.addPerson(new Person(new Name("Bob"), new Phone("23456789"), new Email("bob@example.com"),
                new Address("456 Main St"), tags2, "Finance Info 2", "@bob"));
        model.addPerson(new Person(new Name("Charlie"), new Phone("34567890"), new Email("charlie@example.com"),
                new Address("789 Main St"), tags3, "Finance Info 3", "@charlie"));
        model.addPerson(new Person(new Name("Eve"), new Phone("56789012"), new Email("eve@example.com"),
                new Address("121 Main St"), tags5, "Finance Info 5", "@eve"));
    }
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        addTagsWithValuesPersons(model);
    }
    // Test normal ascending
    @Test
    public void execute_validSortOrderAsc_returnsSortedCommandResult() {
        SortCommand sortCommand = new SortCommand("friends", "asc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Alice", sortedList.get(0).getName().fullName);
        assertEquals("Charlie", sortedList.get(1).getName().fullName);
        assertEquals("Bob", sortedList.get(2).getName().fullName);
        assertEquals("Eve", sortedList.get(3).getName().fullName);
    }
    // Test normal descending
    @Test
    public void execute_validSortOrderDesc_returnsSortedCommandResult() {
        SortCommand sortCommand = new SortCommand("friends", "desc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Eve", sortedList.get(0).getName().fullName);
        assertEquals("Bob", sortedList.get(1).getName().fullName);
        assertEquals("Charlie", sortedList.get(2).getName().fullName);
        assertEquals("Alice", sortedList.get(3).getName().fullName);
    }
    // Tag doesnt exist
    @Test
    public void execute_noMatchingContacts_returnsNoContactFoundMessage() {
        SortCommand sortCommand = new SortCommand("nonexistent", "asc");
        CommandResult result = sortCommand.execute(model);
        assertEquals(SortCommand.MESSAGE_NO_CONTACT_FOUND, result.getFeedbackToUser());
    }

    // Test ascending with Tags with string value
    @Test
    public void execute_ascWithStringValueTag_returnsSortedCommandResult() {
        Set<Tag> stringValueTag1 = new HashSet<>(List.of(new Tag("friends", "always")));
        Set<Tag> stringValueTag2 = new HashSet<>(List.of(new Tag("friends", "before")));
        Set<Tag> stringValueTag3 = new HashSet<>(List.of(new Tag("friends", "Z")));
        model.addPerson(new Person(new Name("Friendo"), new Phone("10000000"), new Email("f@example.com"),
                new Address("100 Empty St"), stringValueTag1, "Finance Info 100", "@ffff"));
        model.addPerson(new Person(new Name("Griendo"), new Phone("20000000"), new Email("g@example.com"),
                new Address("200 Empty St"), stringValueTag2, "Finance Info 200", "@gggg"));
        model.addPerson(new Person(new Name("Hriendo"), new Phone("30000000"), new Email("h@example.com"),
                new Address("300 Empty St"), stringValueTag3, "Finance Info 300", "@hhhh"));

        SortCommand sortCommand = new SortCommand("friends", "asc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Alice", sortedList.get(0).getName().fullName);
        assertEquals("Charlie", sortedList.get(1).getName().fullName);
        assertEquals("Bob", sortedList.get(2).getName().fullName);
        assertEquals("Eve", sortedList.get(3).getName().fullName);
        assertEquals("Friendo", sortedList.get(4).getName().fullName);
        assertEquals("Griendo", sortedList.get(5).getName().fullName);
        assertEquals("Hriendo", sortedList.get(6).getName().fullName);
    }

    // Test descending with Tags with string value
    @Test
    public void execute_descWithStringValueTag_returnsSortedCommandResult() {
        Set<Tag> stringValueTag1 = new HashSet<>(List.of(new Tag("friends", "always")));
        Set<Tag> stringValueTag2 = new HashSet<>(List.of(new Tag("friends", "before")));
        Set<Tag> stringValueTag3 = new HashSet<>(List.of(new Tag("friends", "Z")));
        model.addPerson(new Person(new Name("Friendo"), new Phone("10000000"), new Email("f@example.com"),
                new Address("100 Empty St"), stringValueTag1, "Finance Info 100", "@ffff"));
        model.addPerson(new Person(new Name("Griendo"), new Phone("20000000"), new Email("g@example.com"),
                new Address("200 Empty St"), stringValueTag2, "Finance Info 200", "@gggg"));
        model.addPerson(new Person(new Name("Hriendo"), new Phone("30000000"), new Email("h@example.com"),
                new Address("300 Empty St"), stringValueTag3, "Finance Info 300", "@hhhh"));

        SortCommand sortCommand = new SortCommand("friends", "desc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Hriendo", sortedList.get(0).getName().fullName);
        assertEquals("Griendo", sortedList.get(1).getName().fullName);
        assertEquals("Friendo", sortedList.get(2).getName().fullName);
        assertEquals("Eve", sortedList.get(3).getName().fullName);
        assertEquals("Bob", sortedList.get(4).getName().fullName);
        assertEquals("Charlie", sortedList.get(5).getName().fullName);
        assertEquals("Alice", sortedList.get(6).getName().fullName);
    }

    // Test ascending with Tag with no value
    @Test
    public void execute_ascWithNullValueTag_returnsSortedCommandResult() {
        Set<Tag> nullValueTag = new HashSet<>(List.of(new Tag("friends")));
        model.addPerson(new Person(new Name("Null"), new Phone("00000000"), new Email("nullify@example.com"),
                new Address("000 Empty St"), nullValueTag, "Finance Info 0", "@nullify"));

        SortCommand sortCommand = new SortCommand("friends", "asc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Alice", sortedList.get(0).getName().fullName);
        assertEquals("Charlie", sortedList.get(1).getName().fullName);
        assertEquals("Bob", sortedList.get(2).getName().fullName);
        assertEquals("Eve", sortedList.get(3).getName().fullName);
        assertEquals("Null", sortedList.get(4).getName().fullName);
    }

    // Test descending with Tag with no value
    @Test
    public void execute_descWithNullValueTag_returnsSortedCommandResult() {
        Set<Tag> nullValueTag = new HashSet<>(List.of(new Tag("friends")));
        model.addPerson(new Person(new Name("Null"), new Phone("00000000"), new Email("nullify@example.com"),
                new Address("000 Empty St"), nullValueTag, "Finance Info 0", "@nullify"));

        SortCommand sortCommand = new SortCommand("friends", "desc");
        CommandResult result = sortCommand.execute(model);
        String expectedMessage = "Displaying sorted results: Tag: friends";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> sortedList = model.getFilteredPersonList();

        assertEquals("Eve", sortedList.get(0).getName().fullName);
        assertEquals("Bob", sortedList.get(1).getName().fullName);
        assertEquals("Charlie", sortedList.get(2).getName().fullName);
        assertEquals("Alice", sortedList.get(3).getName().fullName);
        assertEquals("Null", sortedList.get(4).getName().fullName);
    }

    @Test
    public void equals() {
        SortCommand sortCommand1 = new SortCommand("dummy", "asc");
        SortCommand sortCommand2 = new SortCommand("dummy", "desc");
        SortCommand emptyStringNameCommand = new SortCommand("", "desc");
        SortCommand sortCommandNullName1 = new SortCommand(null, "asc");
        SortCommand sortCommandNullName2 = new SortCommand(null, "desc");

        // Expected for both to equal due to same tag name
        assertEquals(sortCommand1, sortCommand1);
        assertEquals(sortCommand1, sortCommand2);
        // Expected to not equal due to different tag names
        assertNotEquals(sortCommand2, emptyStringNameCommand);
        assertNotEquals(sortCommand2, sortCommandNullName1);
        // Null object case
        assertNotEquals(sortCommand2, null);
        // Expected for both to equal due to null name
        assertEquals(sortCommandNullName1, sortCommandNullName2);
    }

    @Test
    public void toStringMethod() {
        SortCommand command = new SortCommand("dummy", "asc");
        SortCommand emptyStringNameCommand = new SortCommand("", "asc");
        SortCommand nullNameCommand = new SortCommand(null, "asc");
        // Test using non-empty string as name
        assertEquals("seedu.address.logic.commands.SortCommand{tagName=dummy}", command.toString());
        // Test using empty string as name
        assertEquals("seedu.address.logic.commands.SortCommand{tagName=}",
                emptyStringNameCommand.toString());
        // Test using null as name
        assertEquals("seedu.address.logic.commands.SortCommand{tagName=null}", nullNameCommand.toString());
    }
}

