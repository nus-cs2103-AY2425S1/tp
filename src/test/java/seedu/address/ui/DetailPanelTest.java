package seedu.address.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class DetailPanelTest extends ApplicationTest {

    private DetailPanel detailPanel;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        // Assuming that PersonBuilder is correctly configured to use in tests
        Person examplePerson = new PersonBuilder().withName("John Doe")
                .withPhone("123456789")
                .withEmail("john@example.com")
                .withAddress("123 Main Street")
                .withRole("brUdder")
                .withMajor("cs")
                .build();

        detailPanel = new DetailPanel();
        detailPanel.setPerson(examplePerson, 0);  // Simulate setting a person in the UI

        scene = new Scene(detailPanel.getRoot(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void checkPersonDetailsDisplayed() {
        // Use TestFX to verify that labels display the correct information
        verifyThat("#name", hasText("Name: John Doe"));
        verifyThat("#phone", hasText("Phone: 123456789"));
        verifyThat("#email", hasText("Email: john@example.com"));
        verifyThat("#address", hasText("Address: 123 Main Street"));
        verifyThat("#role", hasText("Role: brUdder"));
        verifyThat("#major", hasText("Major: Computer Science"));  // Assuming the conversion to full name works
    }
}