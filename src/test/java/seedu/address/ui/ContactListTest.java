package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private static CountDownLatch latch = new CountDownLatch(1);
    private ObservableList<Person> personList;

    // Dummy JavaFX application
    public static class TestApp extends Application {
        @Override
        public void start(javafx.stage.Stage primaryStage) {
            latch.countDown(); // Release the latch when the application starts
        }
    }

    @BeforeAll
    static void setUpOnce() throws Exception {
        // Start the JavaFX application
        new Thread(() -> Application.launch(TestApp.class)).start();
        // Wait for the application to start
        latch.await();
    }

    @Test
    void testComponentNotNull() throws TimeoutException, InterruptedException {

        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());

        assertDoesNotThrow(() -> new ContactList(this.personList));
    }

    @AfterAll
    static void tearDownOnce() {
        // Shut down the JavaFX application
        Platform.exit();
    }

}
