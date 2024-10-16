package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import seedu.address.model.person.Person;
import seedu.address.testutil.GuestBuilder;

public class PersonListPanelTest extends ApplicationTest {
    private PersonListPanel personListPanel;
    private ObservableList<Person> personObservableList;

    @Override
    public void start(Stage stage) throws Exception {
        personObservableList = FXCollections.observableArrayList(new GuestBuilder().build());
        personListPanel = new PersonListPanel(personObservableList, "Test Guest");
        stage.setScene(new javafx.scene.Scene(personListPanel.getRoot()));
        stage.show();
    }

    @Test
    public void constructor_validListAndTitle_setsTitleCorrectly() {
        Label titleLabel = lookup("#titleLabel").query();
        assertNotNull(titleLabel);
        assertEquals("Test Guest", titleLabel.getText());
    }

    @Test
    public void constructor_validListAndTitle_populatesPersonListView() {
        ListView<Person> personListView = lookup("#personListView").query();
        assertNotNull(personListView);
        assertEquals(1, personListView.getItems().size());

        assertEquals("Amy Bee", personListView.getItems().get(0).getName().toString());
    }
}
