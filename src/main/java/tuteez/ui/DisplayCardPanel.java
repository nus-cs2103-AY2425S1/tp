package tuteez.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;

/**
 * Panel containing the current person on display.
 */
public class DisplayCardPanel extends UiPart<Region> {
    private static final String FXML = "DisplayCardPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayCardPanel.class);

    @FXML
    private ListView<Person> displayCardListView;

    private ObservableList<Person> lastViewedPersonList;

    /**
     * Creates an empty {@code DisplayCardPanel}.
     */
    public DisplayCardPanel() {
        super(FXML);
        lastViewedPersonList = FXCollections.observableArrayList();
        displayCardListView.setItems(lastViewedPersonList);
        displayCardListView.setCellFactory(listView -> new DisplayCardListViewCell());
    }

    /**
     * Sets a new {@code DisplayCard} in the panel.
     *
     * @param personToDisplay The person whose details will be displayed.
     */
    public void setDisplayCard(Optional<Person> personToDisplay) {
        lastViewedPersonList.clear();

        personToDisplay.ifPresent(lastViewedPersonList::add);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code DisplayCard}.
     */
    class DisplayCardListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(Optional.of(person)).getRoot());
            }
        }
    }
}
