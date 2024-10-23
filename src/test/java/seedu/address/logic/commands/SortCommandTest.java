package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.SortOrder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class SortCommandTest {
    private static final Person A = new PersonBuilder().withName("Alice").withTelegram("a").build();
    private static final Person B = new PersonBuilder().withName("Bob").withTelegram("b").build();
    private static final Person C = new PersonBuilder().withName("Charlie").withTelegram("c").build();
    private static final Person D = new PersonBuilder().withName("David").withTelegram("d").build();

    private Model model;

    private final List<Person> personsAscending = Arrays.asList(A, B, C, D);

    private final List<Person> personsDescending = Arrays.asList(D, C, B, A);

    private final List<Person> personsUnordered = Arrays.asList(B, D, A, C);

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        // Populate the model with some test data
        for (Person person : personsUnordered) {
            model.addPerson(person);
        }
    }

    @Test
    void execute_sortAscending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(SortOrder.ASC);
        CommandResult result = sortCommand.execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.ASC), result.getFeedbackToUser());
        List<Person> sortedPersons = model.getSortedPersonList();
        assertEquals(personsAscending, sortedPersons.stream().toList());
    }

    @Test
    void execute_sortDescending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(SortOrder.DESC);
        CommandResult result = sortCommand.execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.DESC), result.getFeedbackToUser());
        List<Person> sortedPersons = model.getSortedPersonList();

        assertEquals(personsDescending, sortedPersons.stream().toList());
    }

    @Test
    void execute_sortOriginal_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(SortOrder.OG);
        CommandResult result = sortCommand.execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.OG), result.getFeedbackToUser());
        List<Person> sortedPersons = model.getSortedPersonList();
        assertEquals(personsUnordered, sortedPersons);
    }
}
