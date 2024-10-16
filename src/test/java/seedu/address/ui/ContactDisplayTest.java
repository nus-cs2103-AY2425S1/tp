package seedu.address.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactDisplayTest {

    private ContactDisplay contactDisplay;
    private Label nameLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private Label categoryLabel;
    private Label addressLabel;
    private FlowPane tags;

    @BeforeEach
    public void setUp() {
        Platform.startup(() -> {
            contactDisplay = new ContactDisplay();
            nameLabel = (Label) contactDisplay.getRoot().lookup("#nameLabel");
            phoneLabel = (Label) contactDisplay.getRoot().lookup("#phoneLabel");
            emailLabel = (Label) contactDisplay.getRoot().lookup("#emailLabel");
            categoryLabel = (Label) contactDisplay.getRoot().lookup("#categoryLabel");
            addressLabel = (Label) contactDisplay.getRoot().lookup("#addressLabel");
            tags = (FlowPane) contactDisplay.getRoot().lookup("#tags");
        });
    }

    @Test
    public void updateContactDetails_validPerson_contactDetailsDisplayed() {
        Person testPerson = new PersonBuilder().withName("John Doe").withCategory("student")
                .withPhone("12345678").withEmail("johndoe@example.com").withAddress("123 Main St")
                .withTags("friend", "colleague").build();

        Platform.runLater(() -> contactDisplay.updateContactDetails(testPerson));

        Platform.runLater(() -> {
            assertEquals("Name: John Doe", nameLabel.getText());
            assertEquals("Category: student", categoryLabel.getText());
            assertEquals("Phone: 12345678", phoneLabel.getText());
            assertEquals("Email: johndoe@example.com", emailLabel.getText());
            assertEquals("Address: 123 Main St", addressLabel.getText());
            
            assertEquals(2, tags.getChildren().size());
            assertTrue(tags.getChildren().stream().anyMatch(node -> ((Label) node).getText().equals("friend")));
            assertTrue(tags.getChildren().stream().anyMatch(node -> ((Label) node).getText().equals("colleague")));
        });
    }
}
