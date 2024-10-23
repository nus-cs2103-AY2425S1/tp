package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                // Clear the cell if it's empty
                setGraphic(null);
                setText(null);
                getStyleClass().removeAll("student-card", "teacher-card"); // Clear any previous styles
            } else {
                // Set the PersonCard as the graphic for the ListCell
                PersonCard personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());

                // Remove any previous styles applied to the cell
                getStyleClass().removeAll("student-card", "teacher-card");

                // Apply the appropriate style based on whether the person is a Student or Teacher
                if (person instanceof Student) {
                    // Apply inline style for student
                    setStyle("-fx-background-color: #349beb;");

                } else if (person instanceof Teacher) {
                    // Apply inline style for teacher
                    setStyle("-fx-background-color: #269e2e;");
                } else {
                    // Optionally, clear the style for other types of people
                    setStyle("");
                }
            }
        }

    }

}
