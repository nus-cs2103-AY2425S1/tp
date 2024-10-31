package seedu.address.ui;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class UniversityListPanel extends UiPart<Region> {
    private static final String FXML = "UniversityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(UniversityListPanel.class);

    @FXML
    private ListView<Person> universityListView;

    private ObservableList<Person> filteredList;
    private Set<String> addedUniversities;
    private Person highlightedPerson = null;

    /**
     * Creates a {@code UniversityListPanel} with the given {@code ObservableList}.
     */
    public UniversityListPanel(ObservableList<Person> personList) {
        super(FXML);

        // Use a Set to store unique universities and filter the list
        addedUniversities = new HashSet<>();
        filteredList = FXCollections.observableArrayList();

        for (Person person : personList) {
            String university = person.getUniversity().value;
            if (!addedUniversities.contains(university)) {
                addedUniversities.add(university);
                filteredList.add(person);
            }
        }

        universityListView.setItems(filteredList);
        universityListView.setCellFactory(listView -> new UniversityListViewCell());
    }
    /**
     * Highlights the person associated with the specified university name in the list.
     * This method first clears any existing highlights and then searches for the
     * person whose university matches the provided name. If found, the person is
     * highlighted, and the list view scrolls to their position.
     *
     * @param universityName The name of the university to highlight.
     */
    public void highlightUniversity(String universityName) {
        clearHighlight();

        // Find the person with the matching university and highlight it
        for (Person person : filteredList) {
            if (person.getUniversity().value.equalsIgnoreCase(universityName)) {
                highlightedPerson = person;
                universityListView.scrollTo(person);
                universityListView.getSelectionModel().select(person); // Optional: visually select the item
                universityListView.refresh();
                break;
            }
        }
    }

    /**
     * Clears any highlights from the currently highlighted person in the list.
     * This method resets the highlighted person to null and removes the selection
     * from the list view, ensuring that no items are visually highlighted.
     */
    public void clearHighlight() {
        highlightedPerson = null;
        universityListView.getSelectionModel().clearSelection();
        universityListView.refresh(); // Refresh to remove highlight
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class UniversityListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new UniversityListCard(person, getIndex() + 1).getRoot());

                // Apply or remove highlight based on whether this person is the highlightedPerson
                if (person.equals(highlightedPerson)) {
                    getStyleClass().add("highlighted");
                } else {
                    getStyleClass().remove("highlighted");
                }
            }
        }
    }
}
