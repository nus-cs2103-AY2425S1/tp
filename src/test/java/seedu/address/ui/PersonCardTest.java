package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Role;

public class PersonCardTest {
    private static boolean initialised = false;
    private static final Name TEST_NAME = new Name("Test");
    private static final Phone TEST_PHONE = new Phone("99999999");
    private static final Email TEST_EMAIL = new Email("test@test.com");
    private static final Telegram TEST_TELEGRAM = new Telegram("test123");
    private static final Set<Role> TEST_ROLES = new HashSet<>();
    private static final Set<Attendance> TEST_ATTENDANCE = new HashSet<>();
    private static final FavouriteStatus TEST_FAVOURITE_STATUS = FavouriteStatus.FAVOURITE;

    private static final Person TEST_PERSON = new Person(TEST_NAME, TEST_PHONE, TEST_EMAIL, TEST_TELEGRAM,
            TEST_ROLES, TEST_ATTENDANCE, TEST_FAVOURITE_STATUS);

    @BeforeAll
    public static void setUp() throws Exception {
        // Initialize JavaFX toolkit
        PersonCardTest.initialised = true;
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout waiting for FX runtime to start");
        }
    }

    @Test
    public void constructor_validPerson_correctInitialisation() {
        PersonCard testPersonCard = new PersonCard(TEST_PERSON, 1);
        assertEquals("1. ", testPersonCard.getIdLabel().getText());
        assertEquals(TEST_NAME.fullName, testPersonCard.getNameLabel().getText());
        assertEquals(TEST_PHONE.value, testPersonCard.getPhoneLabel().getText());
        assertEquals("@" + TEST_TELEGRAM.value, testPersonCard.getTelegramLabel().getText());
        assertEquals(TEST_EMAIL.value, testPersonCard.getEmailLabel().getText());
        assertEquals("☐", testPersonCard.getAttendanceLabel().getText().trim()); //has no attendance
    }

    @Test
    public void constructor_notFavourite_correctClassName() {
        Person notFavPerson = new Person(TEST_NAME, TEST_PHONE, TEST_EMAIL, TEST_TELEGRAM,
                TEST_ROLES, TEST_ATTENDANCE, FavouriteStatus.NOT_FAVOURITE);
        PersonCard testPersonCard = new PersonCard(notFavPerson, 1);
        assertFalse(testPersonCard.getCardPane().getStyleClass().contains("favCardPane"));
    }

    @Test
    public void constructor_favourite_correctClassName() {
        PersonCard testPersonCard = new PersonCard(TEST_PERSON, 1);
        assertTrue(testPersonCard.getCardPane().getStyleClass().contains("favCardPane"));
    }

    @Test
    public void constructor_hasAttendedToday() {
        PersonCard testPersonCard = new PersonCard(TEST_PERSON, 1);
        assertFalse(testPersonCard.hasAttendedToday(TEST_PERSON));
    }

    public static boolean toolkitInitialised() {
        return PersonCardTest.initialised;
    }
}
