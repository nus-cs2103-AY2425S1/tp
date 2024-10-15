package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
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

public class PersonCardTest extends ApplicationTest {

    private PersonCard personCard;
    private Person samplePerson;

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

        //Tags are rendered lexicographically
        Label firstTag = (Label) tagsFlowPane.getChildren().get(0);
        Label secondTag = (Label) tagsFlowPane.getChildren().get(1);
        assertEquals("colleagues", firstTag.getText());
        assertEquals("friends", secondTag.getText());
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
