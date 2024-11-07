package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final String currentTheme;

    private double previousScrollPosition = 0;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, String currentTheme, double scrollPosition) {
        super(FXML);
        this.currentTheme = currentTheme;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> {
            PersonListViewCell cell = new PersonListViewCell();
            personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                cell.handleSelectionChange(newValue != null);
            });

            return cell;
        });

        // Set scroll position
        personListView.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                personListView.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
                    setScrollPosition(scrollPosition);
                });
            }
        });

        // Bug fix for unexpected jumping of scrolling on selection
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            double prevScrollPosition = getScrollPosition();
            Platform.runLater(() -> {
                setScrollPosition(prevScrollPosition);
            });
        });
    }

    private Optional<ScrollBar> getScrollBar() {
        return personListView.lookupAll(".scroll-bar").stream()
            .filter(node -> node instanceof ScrollBar)
            .map(node -> (ScrollBar) node)
            .filter(scrollBar -> scrollBar.getOrientation() == Orientation.VERTICAL)
            .findFirst();
    }

    public double getScrollPosition() {
        Optional<ScrollBar> scrollBar = getScrollBar();
        return scrollBar.map(ScrollBar::getValue).orElse(0.0);
    }

    public void setScrollPosition(double scrollPosition) {
        Optional<ScrollBar> scrollBar = getScrollBar();
        scrollBar.ifPresent(bar -> {
            personListView.layout();
            bar.layout();
            bar.setValue(scrollPosition);
            previousScrollPosition = scrollPosition;
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private boolean isSelectionChange = false;

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            // If it's a selection change, ignore updates
            if (isSelectionChange) {
                return;
            }

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, currentTheme).getRoot());
            }
        }

        public void handleSelectionChange(boolean selected) {
            isSelectionChange = selected;
        }
    }
}
