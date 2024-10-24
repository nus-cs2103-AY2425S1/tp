package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox placeholderContainer;

    @FXML
    private Label placeholderLabel;

    @FXML
    private VBox personListContainer;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, String title) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell()); // map each item to a display cell
        titleLabel.setText(title);

        // Allow label to expand to fill all available width in parent container
        // Then, center it
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Toggle visibility based on the list's content
        togglePlaceholder(personList.isEmpty());

        personList.addListener((ListChangeListener<Person>) change -> {
            togglePlaceholder(personList.isEmpty());
        });

        // Wait for the personListView to be initialized
        Platform.runLater(() -> {
            assert(personListView != null);
            logger.info("Setting up auto scroll mechanism");
            setupAutoScroll(personList);

            disableScrollbarButtons();
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
                if (change.wasAdded()) {
                    /*
                     * Initially (when the app first opens), when adding either a guest or vendor,
                     * three changes are picked up here.
                     * The change involving adding the new person.
                     * The change involving adding the entire current guests.
                     * The change involving adding the entire current vendors.
                     * Only auto-scroll for the first change.
                     * Hence, we use the below if check to enforce this.
                     */
                    if (change.getAddedSize() == 1) {
                        Platform.runLater(() -> {
                            personListView.scrollTo(change.getTo());
                            personListView.getSelectionModel().select(change.getTo() - 1);
                        });
                        break;
                    } else {
                        continue;
                    }
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
    }

    private void disableScrollbarButtons() {
        for (Node node : personListView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar) {
                scrollBar.setDisable(true);
            }
        }
    }

    private void togglePlaceholder(boolean isEmpty) {
        if (isEmpty) {
            placeholderContainer.setVisible(true);
            placeholderContainer.setManaged(true);

            personListContainer.setVisible(false);
            personListContainer.setManaged(false);
        } else {
            placeholderContainer.setVisible(false);
            placeholderContainer.setManaged(false);

            personListContainer.setVisible(true);
            personListContainer.setManaged(true);
        }
    }
}
