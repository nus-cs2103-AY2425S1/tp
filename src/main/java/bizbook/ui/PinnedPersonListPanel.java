package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.LogsCenter;
import bizbook.model.person.Person;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of pinned persons.
 */
public class PinnedPersonListPanel extends UiPart<Region> {
    private static final String FXML = "PinnedPersonListPanel.fxml";
    private static final Integer INITIAL_INDEX_VALUE = -1;
    private final Logger logger = LogsCenter.getLogger(PinnedPersonListPanel.class);


    @FXML
    private ListView<Person> pinnedPersonListView;

    /**
     * Creates a {@code PinnedPersonListPanel} with the given {@code ObservableList}.
     */
    public PinnedPersonListPanel(ObservableList<Person> pinnedPersonList, ObjectProperty<Person> focusedPerson) {
        super(FXML);

        pinnedPersonListView.setItems(pinnedPersonList);
        pinnedPersonListView.setCellFactory(listView -> new PinnedPersonListViewCell());

        // Add event handler
        pinnedPersonListView.setOnMouseClicked(event -> handleListViewClick(event, focusedPerson));
    }

    private void handleListViewClick(MouseEvent event, ObjectProperty<Person> focusedPerson) {
        int index = pinnedPersonListView.getSelectionModel().getSelectedIndex();
        Person selectedPerson = pinnedPersonListView.getSelectionModel().getSelectedItem();

        if (index != INITIAL_INDEX_VALUE) {
            focusedPerson.set(selectedPerson);
            logger.info("Clicked on pinned person: " + selectedPerson + " at index " + index);
        } else {
            logger.info("Clicked on an empty area of the PinnedListView");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PinnedPersonCard}.
     */
    class PinnedPersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PinnedPersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
