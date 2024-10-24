package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final double SCROLL_AMOUNT = 0.0003;
    private static final String FXML = "PersonListPanel.fxml";
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

        // Wait for the personList and personListView to be initialized
        Platform.runLater(() -> {
            reduceScrollSpeed();
            setupAutoScroll(personList);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
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

    private void reduceScrollSpeed() {
        ScrollBar verticalScrollBar = findVerticalScrollBar();
        if (verticalScrollBar != null) {
            verticalScrollBar.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                    // Reduce scroll speed by scaling down the delta values (scroll amount).
                    double deltaY = event.getDeltaY() * 0.005; // Slows down the scroll speed (adjust factor as needed)
                    verticalScrollBar.setValue(verticalScrollBar.getValue()
                            - deltaY * verticalScrollBar.getUnitIncrement());

                }
            });
        }
    }

    /**
     * Finds the vertical {@code ScrollBar} of a {@code ListView}.
     * @return The vertical {@code ScrollBar}, or {@code null} if not found.
     */
    private ScrollBar findVerticalScrollBar() {
        for (Node node : personListView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar) {
                if (scrollBar.getOrientation().equals(Orientation.VERTICAL)) {
                    return scrollBar;
                }
            }
        }
        return null;
    }

    /**
     * Sets up auto-scrolling to the bottom when new items are added
     */
    private void setupAutoScroll(ObservableList<Person> personList) {
        // Attach a listener on the personList (observable) to listen/observe for changes
        // Handle changes regarding item addition and item deletion only
        personList.addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    // Scroll to the bottom
                    Platform.runLater(() -> {
                        personListView.scrollTo(personList.size() - 1);
                    });
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> {
                        // Scroll to the removal index, or the nearest available index
                        int removalIndex = change.getFrom();
                        if (removalIndex >= personList.size()) {
                            removalIndex = Math.max(0, personList.size() - 1);
                        }
                        personListView.scrollTo(removalIndex);
                    });
                }
            }
        });
    }
}
