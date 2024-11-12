package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    private Map<Person, ObservableList<Participation>> participationMap;

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
        this.participationMap = createParticipationMap(personList, participationList);

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
                logger.info("Updating list cell with person: " + person
                        + "\n - participation list: " + participationMap.get(person));
                Platform.runLater(() -> setGraphic(new PersonCard(person,
                        participationMap.get(person), getIndex() + 1).getRoot()));
            }
        }
    }

    /**
     * Creates a participation map to be used for displaying attendance records tagged to each person.
     *
     * @return Map of participation associated with each person in the personList.
     */
    private HashMap<Person, ObservableList<Participation>> createParticipationMap(
            ObservableList<Person> personList, ObservableList<Participation> participationList) {
        HashMap<Person, ObservableList<Participation>> participationMap = new HashMap<>();
        logger.info("Creating new participation map with"
                + "\n - person list: " + personList
                + "\n - participation list: " + participationList);
        for (Person person : personList) {
            participationMap.put(person, FXCollections.observableArrayList());
        }

        for (Participation participation : participationList) {
            logger.info("Adding participation to map: " + participation);
            ObservableList<Participation> participations = participationMap.get(participation.getStudent());
            if (participations != null) {
                participations.add(participation);
            }
        }
        logger.info("Successfully created participation map" + "\n" + participationMap);
        return participationMap;
    }

    /**
     * Adds listeners to ObservableList of {@code Persons} and ObservableList of {@code participation}
     * to create or modify participationMap on changes to personList and participationList
     * respectively to update the UI on user input and execution of the command.
     * </p>
     * Listener is added to ObservableList of {@code Tutorial} to update UI
     * when tutorials are created or closed.
     */
    private void addListeners() {
        // Listener to recreate participationMap
        personList.addListener((ListChangeListener<Person>) change -> {
            logger.info("Change observed in person list: creating new participation map");
            this.participationMap = createParticipationMap(this.personList, this.participationList);
        });

        // Listener to add or remove participation from current participationMap
        participationList.addListener((ListChangeListener<Participation>) change -> {
            logger.info("Change observed in participation list: " + change);
            while (change.next()) {
                for (Participation removedParticipation : change.getRemoved()) {
                    logger.info("Participation to be removed: " + removedParticipation);
                    ObservableList<Participation> participations =
                            participationMap.get(removedParticipation.getStudent());
                    if (participations != null) {
                        participations.remove(removedParticipation);
                        logger.info("Removed participation: " + removedParticipation);
                    }
                }
                for (Participation addedParticipation : change.getAddedSubList()) {
                    logger.info("Participation to be added: " + addedParticipation);
                    ObservableList<Participation> participations =
                            participationMap.get(addedParticipation.getStudent());
                    if (participations != null) {
                        participations.add(addedParticipation);
                        logger.info("Added participation: " + addedParticipation);
                    }
                }
            }
        });
    }
}
