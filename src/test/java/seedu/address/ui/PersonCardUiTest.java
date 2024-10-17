package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.tag.Tag;

public class PersonCardUiTest extends ApplicationTest {

    private PersonCard personCard;
    private Person samplePerson;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() ->
            new MainApp(tempDir.resolve("ui_data.json"))
        );
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20); // Wait for JavaFX to complete rendering
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages(); // Clean up after tests
    }

    @Override
    public void start(Stage stage) {
        // Create a sample Person object for testing
        samplePerson = createSamplePerson();

        // Instantiate PersonCard to be tested, passing in the sample Person and a displayed index of 1
        personCard = new PersonCard(samplePerson, 1);

        // Set the scene for JavaFX testing
        stage.setScene(personCard.getRoot().getScene());
        stage.show();
    }

    @Test
    void personCard_displayCorrectDetails() {
        // Assert that the person object is not null
        assertNotNull(personCard.person);

        // Check if the displayed ID, name, phone, email, and other labels are correct
        assertEquals("1. ", personCard.getId().getText());
        assertEquals("John Doe", personCard.getName().getText());
        assertEquals("91234567", personCard.getPhone().getText());
        assertEquals("johndoe@example.com", personCard.getEmail().getText());
        assertEquals("Date: 2023-01-01, From: 10:00, To: 11:00", personCard.getAppointment().getText());
        assertEquals("NUS", personCard.getProperty().getText());

        // Check if the tags are correctly displayed
        FlowPane tagsFlowPane = personCard.getTags();
        assertEquals(2, tagsFlowPane.getChildren().size()); // Expecting 2 tags

        // Tags are rendered lexicographically
        Label firstTag = (Label) tagsFlowPane.getChildren().get(0);
        Label secondTag = (Label) tagsFlowPane.getChildren().get(1);
        assertEquals("colleagues", firstTag.getText());
        assertEquals("friends", secondTag.getText());
    }

    @Test
    void personCard_throwsIllegalArgumentExceptionForInvalidPhone() {
        // Use assertThrows to verify that IllegalArgumentException is thrown for an invalid phone number
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Person(
                    new Name("Invalid Phone"),
                    new Phone("12"), // Invalid phone number (less than 3 digits)
                    new Email("invalid@example.com"),
                    new HashSet<>(),
                    null,
                    new Property("Some Property")
            );
        });

        // Verify the exception message
        assertEquals("Phone numbers should only contain numbers, and it should be at least 3 digits long",
                thrown.getMessage());
    }

    @Test
    void personCard_handlesDifferentIndexes() {
        // Test PersonCard with various indexes
        PersonCard personCardIndex5 = new PersonCard(samplePerson, 5);
        assertEquals("5. ", personCardIndex5.getId().getText());

        PersonCard personCardIndex10 = new PersonCard(samplePerson, 10);
        assertEquals("10. ", personCardIndex10.getId().getText());
    }

    /**
     * Helper method to create a sample Person object for testing.
     */
    private Person createSamplePerson() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));
        tagSet.add(new Tag("colleagues"));

        return new Person(
                new Name("John Doe"),
                new Phone("91234567"),
                new Email("johndoe@example.com"),
                tagSet,
                new Appointment(new Date("2023-01-01"), new From("10:00"), new To("11:00")),
                new Property("NUS")
        );
    }
}
