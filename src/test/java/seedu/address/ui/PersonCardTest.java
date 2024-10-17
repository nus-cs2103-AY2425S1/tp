/*package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.model.person.Person; // Adjust the import based on your package structure
import seedu.address.testutil.PersonBuilder; // Ensure you have a PersonBuilder class for test data

public class PersonCardTest {

    private static Person person; // Mock or create an actual Person instance here
    private static boolean javafxInitialized = false;

    @BeforeAll
    public static void setUpOnce() {
        if (!javafxInitialized) {
            // Launch a JavaFX application, ensuring it's only done once
            Platform.startup(() -> {
                javafxInitialized = true;
                // This lambda is called on the JavaFX application thread
                // Any UI-related initialization can be done here
            });
        }

        // Initialize your person object here
        person = new PersonBuilder()
                .withName("John Doe") // Set name
                .withPhone("12345678") // Set phone
                .withEmail("john@example.com") // Set email
                .withAddress("123 Main St") // Set address
                .build(); // Create a Person instance

    }

    @AfterAll
    public static void tearDownOnce() {
        // Schedule the call to exit the JavaFX platform after tests complete
        Platform.runLater(() -> {
            Platform.exit();
        });
    }

    @Test
    public void testPersonCardCreation() {
        // Create a PersonCard instance
        PersonCard personCard = new PersonCard(person, 1);
        // Ensure that the PersonCard is created successfully
        assertNotNull(personCard); // Check that the PersonCard object is not null
    }

    // A dummy application to launch the JavaFX toolkit
    public static class FakeApplication extends Application {
        @Override
        public void start(Stage primaryStage) {
            // Do nothing
        }
    }
}*/
