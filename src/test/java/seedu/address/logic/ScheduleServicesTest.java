package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ScheduleServicesTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private Model model;
    private ScheduleServices scheduleServices;

    @BeforeEach
    public void setUp() {
        scheduleServices = new ScheduleServices();
        model = new ModelManager();
    }

    @Test
    public void getTopThreeSchedules_emptyPersonList_returnsEmptyList() {
        List<Person> emptyPersonList = new ArrayList<>();
        List<String> result = scheduleServices.getTopThreeSchedules(emptyPersonList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getTopThreeSchedules_validList_success() {
        LocalDateTime now = LocalDateTime.now();
        String futureTime1 = now.plusDays(1).format(DATE_TIME_FORMATTER);
        String futureTime2 = now.plusDays(1).plusHours(2).format(DATE_TIME_FORMATTER);
        String futureTime3 = now.plusDays(2).plusHours(1).format(DATE_TIME_FORMATTER);
        String futureTime4 = now.plusDays(2).format(DATE_TIME_FORMATTER);

        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{futureTime1}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{futureTime2}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{futureTime3}, new String[]{""}).build();
        Person scheduledPerson4 = new PersonBuilder().withName("Daniel")
                        .withSchedule(new String[]{futureTime4}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);
        model.addPerson(scheduledPerson4);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(3, result.size());
        assertEquals("1. Alice: " + futureTime1, result.get(0));
        assertEquals("2. Benson: " + futureTime2, result.get(1));
        assertEquals("3. Daniel: " + futureTime4, result.get(2));
    }

    @Test
    public void getTopThreeSchedules_lessThanThreeSchedules_success() {
        LocalDateTime now = LocalDateTime.now();
        String futureTime1 = now.plusDays(1).format(DATE_TIME_FORMATTER);
        String futureTime2 = now.plusDays(1).plusHours(2).format(DATE_TIME_FORMATTER);

        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{futureTime1}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{futureTime2}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(2, result.size());
        assertEquals("1. Alice: " + futureTime1, result.get(0));
        assertEquals("2. Benson: " + futureTime2, result.get(1));
    }

    @Test
    public void getTopThreeSchedules_duplicateSchedules_success() {
        LocalDateTime now = LocalDateTime.now();
        String futureTime1 = now.plusDays(1).format(DATE_TIME_FORMATTER);
        String futureTime2 = now.plusDays(1).format(DATE_TIME_FORMATTER);
        String futureTime3 = now.plusDays(2).format(DATE_TIME_FORMATTER);
        String futureTime4 = now.plusDays(2).format(DATE_TIME_FORMATTER);

        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{futureTime1}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{futureTime2}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{futureTime3}, new String[]{""}).build();
        Person scheduledPerson4 = new PersonBuilder().withName("Daniel")
                .withSchedule(new String[]{futureTime4}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);
        model.addPerson(scheduledPerson4);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(3, result.size());
        assertEquals("1. Alice: " + futureTime1, result.get(0));
        assertEquals("2. Benson: " + futureTime2, result.get(1));
        assertEquals("3. Carl: " + futureTime3, result.get(2));
    }

    @Test
    public void getTopThreeSchedules_emptyDateTime_continue() {
        LocalDateTime now = LocalDateTime.now();
        String futureTime1 = now.plusDays(1).format(DATE_TIME_FORMATTER);
        String futureTime3 = now.plusDays(2).plusHours(1).format(DATE_TIME_FORMATTER);

        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{futureTime1}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{""}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{futureTime3}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(2, result.size());
        assertEquals("1. Alice: " + futureTime1, result.get(0));
        assertEquals("2. Carl: " + futureTime3, result.get(1));
    }
}
