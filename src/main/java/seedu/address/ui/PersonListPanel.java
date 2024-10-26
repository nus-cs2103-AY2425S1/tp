package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final ObservableList<Person> personList;
    private final ObservableList<Participation> participationList;
    private final Map<Person, ObservableList<Participation>> participationMap;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Participation> participationList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        this.personList = personList;
        this.participationList = participationList;
        participationMap = groupParticipationByPerson(personList, participationList);

        addListeners();
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
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    private HashMap<Person, ObservableList<Participation>> groupParticipationByPerson(
            ObservableList<Person> personList, ObservableList<Participation> participationList) {
        HashMap<Person, ObservableList<Participation>> participationMap = new HashMap<>();

        for (Person person : personList) {
            participationMap.put(person, FXCollections.observableArrayList());
        }

        for (Participation participation : participationList) {
            ObservableList<Participation> participations = participationMap.get(participation.getStudent());
            if (participations != null) {
                participations.add(participation);
            }
        }

        return participationMap;
    }

    private void addListeners() {
        // Listener for changes in the personList
        personList.addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                for (Person removedPerson : change.getRemoved()) {
                    participationMap.remove(removedPerson);
                }
                for (Person addedPerson : change.getAddedSubList()) {
                    participationMap.put(addedPerson, FXCollections.observableArrayList());
                }
            }
        });

        // Listener for changes in the participationList
        participationList.addListener((ListChangeListener<Participation>) change -> {
            while (change.next()) {
                for (Participation removedParticipation : change.getRemoved()) {
                    ObservableList<Participation> participations = participationMap.get(removedParticipation.getStudent());
                    if (participations != null) {
                        participations.remove(removedParticipation);
                    }
                }
                for (Participation addedParticipation : change.getAddedSubList()) {
                    ObservableList<Participation> participations = participationMap.get(addedParticipation.getStudent());
                    if (participations != null) {
                        participations.add(addedParticipation);
                    }
                }
            }
        });
    }
}
