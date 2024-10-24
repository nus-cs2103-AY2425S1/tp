package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
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

        Platform.runLater(this::reduceScrollSpeed);

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
}
