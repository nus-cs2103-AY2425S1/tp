package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private void addTagwValuesPersons(Model model) {
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
        addTagwValuesPersons(model);
    }
    //Test normal ascending
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
    //Test normal descending
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
    //Tag doesnt exist
    @Test
    public void execute_noMatchingContacts_returnsNoContactFoundMessage() {
        SortCommand sortCommand = new SortCommand("nonexistent", "asc");
        CommandResult result = sortCommand.execute(model);
        assertEquals(SortCommand.MESSAGE_NO_CONTACT_FOUND, result.getFeedbackToUser());
    }

}

