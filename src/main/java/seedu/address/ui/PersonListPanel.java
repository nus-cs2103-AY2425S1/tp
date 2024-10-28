package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;
    private ObservableMap<Event, ObservableList<Person>> personEventMapping;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableMap<Event, ObservableList<Person>> personEventMapping) {
        super(FXML);
        this.personEventMapping = personEventMapping;

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
                setGraphic(null);
                setText(null);
            } else {
                String eventName = findEventNameForPerson(person);
                setGraphic(new PersonCard(person, getIndex() + 1, eventName).getRoot());
            }
        }

        /**
         * Finds the event name associated with the given person.
         */
        private String findEventNameForPerson(Person person) {
            for (Event event : personEventMapping.keySet()) {
                ObservableList<Person> persons = personEventMapping.get(event);
                if (persons != null && persons.contains(person)) {
                    return event.getEventName(); // Assuming Event has a getEventName() method
                }
            }
            return "No Event";
        }
    }

}
