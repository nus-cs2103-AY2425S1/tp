package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
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
    private Map<Event, ArrayList<Person>> personEventAssociationMap;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, Map<Event, ArrayList<Person>> personEventAssociationMap) {
        super(FXML);
        this.personEventAssociationMap = personEventAssociationMap;
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
                Set<Event> associatedEvents = findEventsForPerson(person);
                setGraphic(new PersonCard(person, getIndex() + 1, associatedEvents).getRoot());
            }
        }
    }

    /**
     * Finds all event names associated with the given person.
     */
    private Set<Event> findEventsForPerson(Person person) {
        Set<Event> events = new HashSet<>();
        for (Event event : personEventAssociationMap.keySet()) {
            ArrayList<Person> persons = personEventAssociationMap.get(event);
            if (persons != null && persons.contains(person)) {
                events.add(event);
            }
        }
        return events;
    }

    public void refreshPersonListView() {
        personListView.refresh();
    }
}
