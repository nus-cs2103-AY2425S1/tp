package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class ListAppointmentsCommandTest {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_emptyAddressBook_success() {
        CommandResult result = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty()).execute(model);
        assertEquals("Listed 0 upcoming appointments", result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithAppointments_success() {
        LocalDateTime now = LocalDateTime.now();
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{
                        now.plusDays(1).format(INPUT_FORMATTER),
                        now.plusDays(3).format(INPUT_FORMATTER)
                }, new String[]{"Appointment 1", "Appointment 2"}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{now.plusDays(2).format(INPUT_FORMATTER)},
                        new String[]{"Bob's appointment"}).build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withSchedule(new String[]{now.minusDays(1).format(INPUT_FORMATTER)},
                        new String[]{"Past appointment"}).build();
        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);

        CommandResult result = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty()).execute(model);

        String expectedMessage = String.format("Listed 3 upcoming appointments\n%s: %s - Appointment 1"
                        + "\n%s: %s - Bob's appointment\n%s: %s - Appointment 2",
                person1.getName(), now.plusDays(1).format(OUTPUT_FORMATTER),
                person2.getName(), now.plusDays(2).format(OUTPUT_FORMATTER),
                person1.getName(), now.plusDays(3).format(OUTPUT_FORMATTER));
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithDateFilter_success() {
        LocalDateTime now = LocalDateTime.now();
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{now.plusDays(1).format(INPUT_FORMATTER)},
                        new String[]{"Alice's appointment"}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{now.plusDays(2).format(INPUT_FORMATTER)},
                        new String[]{"Bob's appointment"}).build();
        model.addPerson(person1);
        model.addPerson(person2);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);

        CommandResult result = new ListAppointmentsCommand(Optional.of(now.plusDays(1).toLocalDate()),
                Optional.empty()).execute(model);

        String expectedMessage = String.format("Listed 1 upcoming appointments\n%s: %s - Alice's appointment",
                person1.getName(), now.plusDays(1).format(OUTPUT_FORMATTER));
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithTimeFilter_success() {
        LocalDate testDate = LocalDate.of(2024, 12, 10);
        LocalTime testTime = LocalTime.of(14, 0);

        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{testDate.atTime(testTime).format(INPUT_FORMATTER)},
                        new String[]{"Alice's appointment"}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{testDate.atTime(testTime.plusHours(1)).format(INPUT_FORMATTER)},
                        new String[]{"Bob's appointment"}).build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withSchedule(new String[]{testDate.atTime(testTime.minusHours(1))
                        .format(INPUT_FORMATTER)}, new String[]{"Charlie's appointment"})
                .build();

        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);

        ListAppointmentsCommand command = new ListAppointmentsCommand(Optional.of(testDate), Optional.of(testTime));
        CommandResult result = command.execute(model);

        String expectedMessage = String.format("Listed 1 upcoming appointments\n%s: %s - Alice's appointment",
                person1.getName(), testDate.atTime(testTime).format(OUTPUT_FORMATTER));

        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithMultipleAppointmentsPerPerson_success() {
        LocalDateTime now = LocalDateTime.now();
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{
                        now.plusDays(1).format(INPUT_FORMATTER),
                        now.plusDays(3).format(INPUT_FORMATTER),
                        now.plusDays(5).format(INPUT_FORMATTER)
                }, new String[]{"App 1", "App 2", "App 3"}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{
                        now.plusDays(2).format(INPUT_FORMATTER),
                        now.plusDays(4).format(INPUT_FORMATTER)
                }, new String[]{"Bob 1", "Bob 2"}).build();
        model.addPerson(person1);
        model.addPerson(person2);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);

        CommandResult result = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty()).execute(model);

        String expectedMessage = String.format("Listed 5 upcoming appointments\n"
                        + "%s: %s - App 1\n"
                        + "%s: %s - Bob 1\n"
                        + "%s: %s - App 2\n"
                        + "%s: %s - Bob 2\n"
                        + "%s: %s - App 3",
                person1.getName(), now.plusDays(1).format(OUTPUT_FORMATTER),
                person2.getName(), now.plusDays(2).format(OUTPUT_FORMATTER),
                person1.getName(), now.plusDays(3).format(OUTPUT_FORMATTER),
                person2.getName(), now.plusDays(4).format(OUTPUT_FORMATTER),
                person1.getName(), now.plusDays(5).format(OUTPUT_FORMATTER));
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }
}
