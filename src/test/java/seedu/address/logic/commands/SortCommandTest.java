package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.SortOrder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class SortCommandTest {
    // Test data
    private static final Person a = new PersonBuilder().withName("alice").withTelegram("a1xxx").build();
    private static final Person A = new PersonBuilder().withName("Alice").withTelegram("a2xxx").build();
    private static final Person b = new PersonBuilder().withName("bob").withTelegram("b1xxx").build();
    private static final Person B = new PersonBuilder().withName("Bob").withTelegram("b2xxx").build();
    private static final Person c = new PersonBuilder().withName("charlie").withTelegram("c1xxx").build();
    private static final Person C = new PersonBuilder().withName("Charlie").withTelegram("c2xxx").build();
    private static final Person d = new PersonBuilder().withName("david").withTelegram("d1xxx").build();
    private static final Person D = new PersonBuilder().withName("David").withTelegram("d2xxx").build();

    private Model model;

    /**
     * Initialises the model with the relevant list of Persons
     */
    public void addToModel(List<Person> list) {
        model = new ModelManager();
        for (Person person : list) {
            model.addPerson(person);
        }
    }

    @Nested
    class SortUncapitalisedTest {
        private final List<Person> personsUncapitalisedAscending = Arrays.asList(a, b, c, d);
        private final List<Person> personsUncapitalisedDescending = Arrays.asList(d, c, b, a);
        private final List<Person> personsUncapitalisedUnordered = Arrays.asList(b, d, a, c);

        @BeforeEach
        void init() {
            // Load the uncapitalised test data into model
            addToModel(personsUncapitalisedUnordered);
        }

        @Test
        void execute_sortUncapitalisedAscending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.ASC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.ASC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsUncapitalisedAscending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortUncapitalisedDescending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.DESC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.DESC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();

            assertEquals(personsUncapitalisedDescending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortUncapitalisedOriginal_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.OG);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.OG), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsUncapitalisedUnordered, sortedPersons);
        }
    }

    @Nested
    class SortCapitalisedTest {
        private final List<Person> personsCapitalisedAscending = Arrays.asList(A, B, C, D);
        private final List<Person> personsCapitalisedDescending = Arrays.asList(D, C, B, A);
        private final List<Person> personsCapitalisedUnordered = Arrays.asList(B, D, A, C);

        @BeforeEach
        void init() {
            // Load the capitalised test data into the model
            addToModel(personsCapitalisedUnordered);
        }
        @Test
        void execute_sortCapitalisedAscending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.ASC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.ASC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsCapitalisedAscending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortCapitalisedDescending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.DESC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.DESC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();

            assertEquals(personsCapitalisedDescending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortCapitalisedOriginal_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.OG);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.OG), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsCapitalisedUnordered, sortedPersons);
        }
    }

    @Nested
    class SortMixedTest {
        private final List<Person> personsMixedAscending = Arrays.asList(a, B, c, D);
        private final List<Person> personsMixedDescending = Arrays.asList(D, c, B, a);
        private final List<Person> personsMixedUnordered = Arrays.asList(B, D, a, c);

        @BeforeEach
        void init() {
            // Load the test data into model
            addToModel(personsMixedUnordered);
        }

        @Test
        void execute_sortMixedAscending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.ASC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.ASC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsMixedAscending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortMixedDescending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.DESC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.DESC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();

            assertEquals(personsMixedDescending, sortedPersons.stream().toList());
        }

        @Test
        void execute_sortMixedOriginal_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.OG);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.OG), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsMixedUnordered, sortedPersons);
        }
    }

    @Nested
    class SortSameMixedTest {
        private final List<Person> personsMixedSameChar = Arrays.asList(a, A);

        @BeforeEach
        void init() {
            // Load the test data into model
            addToModel(personsMixedSameChar);
        }

        @Test
        void execute_sortSameMixedAscending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.ASC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.ASC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsMixedSameChar, sortedPersons);
        }

        @Test
        void execute_sortSameMixedDescending_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.DESC);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.DESC), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsMixedSameChar, sortedPersons);
        }

        @Test
        void execute_sortSameMixedOriginal_success() throws CommandException {
            SortCommand sortCommand = new SortCommand(SortOrder.OG);
            CommandResult result = sortCommand.execute(model);

            assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, SortOrder.OG), result.getFeedbackToUser());
            List<Person> sortedPersons = model.getSortedPersonList();
            assertEquals(personsMixedSameChar, sortedPersons);
        }
    }
}
