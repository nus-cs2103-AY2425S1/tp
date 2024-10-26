package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ScheduleServicesTest {
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
        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{"2024-10-26 1200"}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{"2024-10-26 1400"}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{"2024-10-27 1800"}, new String[]{""}).build();
        Person scheduledPerson4 = new PersonBuilder().withName("Daniel")
                        .withSchedule(new String[]{"2024-10-27 1200"}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);
        model.addPerson(scheduledPerson4);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(3, result.size());
        assertEquals("1. Alice: 2024-10-26 1200", result.get(0));
        assertEquals("2. Benson: 2024-10-26 1400", result.get(1));
        assertEquals("3. Daniel: 2024-10-27 1200", result.get(2));
    }

    @Test
    public void getTopThreeSchedules_lessThanThreeSchedules_success() {
        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{"2024-10-26 1200"}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{"2024-10-26 1400"}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(2, result.size());
        assertEquals("1. Alice: 2024-10-26 1200", result.get(0));
        assertEquals("2. Benson: 2024-10-26 1400", result.get(1));
    }

    @Test
    public void getTopThreeSchedules_duplicateSchedules_success() {
        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{"2024-10-26 1200"}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{"2024-10-26 1200"}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{"2024-10-27 1200"}, new String[]{""}).build();
        Person scheduledPerson4 = new PersonBuilder().withName("Daniel")
                .withSchedule(new String[]{"2024-10-27 1200"}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);
        model.addPerson(scheduledPerson4);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(3, result.size());
        assertEquals("1. Alice: 2024-10-26 1200", result.get(0));
        assertEquals("2. Benson: 2024-10-26 1200", result.get(1));
        assertEquals("3. Carl: 2024-10-27 1200", result.get(2));
    }

    @Test
    public void getTopThreeSchedules_emptyDateTime_continue() {
        Person scheduledPerson1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{"2024-10-26 1200"}, new String[]{""}).build();
        Person scheduledPerson2 = new PersonBuilder().withName("Benson")
                .withSchedule(new String[]{""}, new String[]{""}).build();
        Person scheduledPerson3 = new PersonBuilder().withName("Carl")
                .withSchedule(new String[]{"2024-10-27 1800"}, new String[]{""}).build();

        model.addPerson(scheduledPerson1);
        model.addPerson(scheduledPerson2);
        model.addPerson(scheduledPerson3);

        List<String> result = scheduleServices.getTopThreeSchedules(model.getFilteredPersonList());

        assertEquals(2, result.size());
        assertEquals("1. Alice: 2024-10-26 1200", result.get(0));
        assertEquals("2. Carl: 2024-10-27 1800", result.get(1));
    }
}
