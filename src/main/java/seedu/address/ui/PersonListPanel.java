package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private boolean isInitialized = false; // Add this flag
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    @FXML
    private Label titleLabel;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, String title) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell()); // map each item to a display cell
        titleLabel.setText(title);

        // Wait for the personListView to be initialized
        Platform.runLater(() -> {
            assert(personListView != null);
            setupAutoScroll(personList);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    static class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonListCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    private void setupAutoScroll(ObservableList<Person> personList) {
        personListView.getItems().addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded() && isInitialized) {
                    Platform.runLater(() -> {
                        personListView.scrollTo(change.getTo());
                        personListView.getSelectionModel().select(change.getTo() - 1);
                    });
                    break;
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> {
                        int removalIndex = change.getFrom();
                        if (removalIndex >= personList.size()) {
                            removalIndex = Math.max(0, personList.size() - 1);
                        }
                        personListView.scrollTo(removalIndex);
                        personListView.getSelectionModel().select(removalIndex);
                    });
                    break;
                }
            }
        });
        isInitialized = true;
    }
}
