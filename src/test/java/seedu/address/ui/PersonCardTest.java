package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    private Person personWithValidSchedule;
    private Person personWithNoSchedule;
    private Person personWithReminder;
    private Person personWithoutReminder;

    @BeforeAll
    public static void initToolkit() {
        // This initializes the JavaFX Toolkit, ensuring the tests can use JavaFX components.
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        personWithValidSchedule = new PersonBuilder(getTypicalPersons().get(0))
                .withSchedule("2024-10-04 1000", "Meeting notes").build();

        personWithNoSchedule = new PersonBuilder(getTypicalPersons().get(1))
                .withSchedule("", "").build();

        personWithReminder = new PersonBuilder(getTypicalPersons().get(2))
                .withReminder("2024-10-04 1000", "1 day").build();
    }

    @Test
    public void testSetNoteField_withNotes() {
        PersonCard personCard = new PersonCard(personWithValidSchedule, 1);
        assertEquals("Notes: Meeting notes", personCard.getNote().getText());
    }

    @Test
    public void testSetNoteField_noNotes() {
        PersonCard personCard = new PersonCard(personWithNoSchedule, 2);
        assertEquals("", personCard.getNote().getText());
    }

    @Test
    public void testSetDateField_withValidSchedule() {
        PersonCard personCard = new PersonCard(personWithValidSchedule, 1);
        String expectedFormattedDate = LocalDateTime.parse("2024-10-04 1000",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                .format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));

        assertEquals(expectedFormattedDate, personCard.getSchedule().getText());
    }

    @Test
    public void testSetDateField_noSchedule() {
        PersonCard personCard = new PersonCard(personWithNoSchedule, 2);
        assertEquals("", personCard.getSchedule().getText());
    }

    @Test
    public void testSetReminderField_withReminder() {
        PersonCard personCard = new PersonCard(personWithReminder, 1);
        String expectedFormattedDate = LocalDateTime.parse("2024-10-04 1000",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                .format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));

        assertEquals(String.format("Reminder on %s, 1 day before", expectedFormattedDate),
                personCard.getReminder().getText());
    }
}
