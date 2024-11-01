package seedu.address.ui;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final int DISPLAYED_INDEX_OFFSET = 1;

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    /** Tracks all the persons that the user has requested to view all the information on. */
    private final Set<Integer> personsBeingViewed;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personsBeingViewed = new HashSet<>();
        personListView.setItems(personList);
        // cell factory creates new ListCell objects for each item in the ListView.
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Remakes the {@code ListView<Person> personListView} such that contact cards
     * that the user has requested to view are expanded {@code PersonCardFull}.
     *
     * @param indexToToggle The index that the user wishes to toggle the view status on.
     */
    public void updateViewedPersons(Optional<Index> indexToToggle) {
        assert indexToToggle.isPresent() : "Index should not be empty at this point";
        int index = indexToToggle.get().getZeroBased();
        if (personsBeingViewed.contains(index)) {
            personsBeingViewed.remove(index);
        } else {
            personsBeingViewed.add(index);
        }
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person}.
     * Graphics may be displayed as a {@code PersonCard} or a {@code PersonCardFull},
     * depending on whether the user has requested to view all information on that {@code Person}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            int currCellIndexInt = getIndex();
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else if (personsBeingViewed.contains(currCellIndexInt)) {
                // if the user had requested this person to be viewed in full, return a PersonCardFull instead
                setGraphic(new PersonCardFull(person, getIndex() + DISPLAYED_INDEX_OFFSET).getRoot());
            } else {
                setGraphic(new PersonCard(person, currCellIndexInt + DISPLAYED_INDEX_OFFSET).getRoot());
            }
        }
    }
}
