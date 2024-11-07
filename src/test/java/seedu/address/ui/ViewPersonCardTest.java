package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewPersonCardTest {

    @BeforeAll
    public static void setUp() throws Exception {
        // Initialize JavaFX toolkit
        if (!PersonCardTest.toolkitInitialised()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                latch.countDown();
            });
            if (!latch.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("Timeout waiting for FX runtime to start");
            }
        }
    }

    @Test
    public void constructor_favouritePerson_personHasStar() {
        Person person = new PersonBuilder().withName("John Doe")
                .withPhone("99999999")
                .withEmail("johndoe@example.com")
                .withTelegram("johndoe")
                .withFavourite()
                .build();

        ViewPersonCard card = new ViewPersonCard(person);

        // Check if the name label is marked with a star
        assertEquals("Contact Information ☆", card.getViewTitle().getText());
    }

    @Test
    public void constructor_nonFavouritePerson_personNoStar() {
        Person person = new PersonBuilder().withName("Jane Doe")
                .withPhone("99999999")
                .withEmail("janedoe@example.com")
                .withTelegram("janedoe")
                .build();

        ViewPersonCard card = new ViewPersonCard(person);

        // Check if the name label is not marked with a star
        assertEquals("Contact Information", card.getViewTitle().getText());
    }

    @Test
    public void constructor_personWithAttendance_attendanceLabelsArePresent() {
        Person person = new PersonBuilder().withName("John Doe")
                .withPhone("99999999")
                .withEmail("johndoe@example.com")
                .withTelegram("johndoe")
                .withAttendance(new Attendance("2024-11-11"))
                .withAttendance(new Attendance("2024-10-10"))
                .build();

        ViewPersonCard card = new ViewPersonCard(person);

        // Check if attendance labels are populated
        FlowPane attendancePane = card.getViewAttendance();
        assertEquals(2, attendancePane.getChildren().size());
        assertTrue(attendancePane.getChildren().stream()
                .anyMatch(node -> ((Label) node).getText().equals("2024-11-11")));
        assertTrue(attendancePane.getChildren().stream()
                .anyMatch(node -> ((Label) node).getText().equals("2024-10-10")));
    }

    @Test
    public void constructor_personWithNoAttendance_attendanceShowsNA() {
        Person person = new PersonBuilder().withName("John Doe")
                .withPhone("99999999")
                .withEmail("johndoe@example.com")
                .withTelegram("johndoe")
                .build();

        ViewPersonCard card = new ViewPersonCard(person);

        // Check if "NA" is displayed when there are no attendance records and if each label id is "viewNA"
        FlowPane attendancePane = card.getViewAttendance();
        attendancePane.getChildren().stream().forEach(label -> assertEquals("viewNA", label.getId()));
        assertEquals(1, attendancePane.getChildren().size());
        assertEquals("NA", ((Label) attendancePane.getChildren().get(0)).getText());
    }

    @Test
    public void constructor_personWithRoles_rolesArePresent() {
        Person person = new PersonBuilder().withName("John Doe")
                .withPhone("99999999")
                .withEmail("johndoe@example.com")
                .withTelegram("johndoe")
                .withRoles("Member", "Leader")
                .build();

        ViewPersonCard card = new ViewPersonCard(person);

        // Check if roles labels are populated
        FlowPane rolesPane = card.getViewRoles();
        assertEquals(2, rolesPane.getChildren().size());
        assertTrue(rolesPane.getChildren().stream()
                .anyMatch(node -> ((Label) node).getText().equals("Leader")));
        assertTrue(rolesPane.getChildren().stream()
                .anyMatch(node -> ((Label) node).getText().equals("Member")));
    }
}
