package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final ObservableList<Person> personList;
    private final ObservableList<Participation> participationList;
    private final ObservableList<Tutorial> tutorialList;
    private Map<Person, ObservableList<Participation>> participationMap;

    @FXML
    private ListView<Person> personListView;
    @FXML
    private Label tutorials;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Participation> participationList,
                           ObservableList<Tutorial> tutorialList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        this.personList = personList;
        this.participationList = participationList;
        this.tutorialList = tutorialList;
        this.participationMap = createParticipationMap(personList, participationList);

        setTutorialsLabel();
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
                Platform.runLater(() -> setGraphic(new PersonCard(person,
                        participationMap.get(person), getIndex() + 1).getRoot()));
            }
        }
    }

    /**
     * @return Map of participation associated with each person in the personList.
     */
    private HashMap<Person, ObservableList<Participation>> createParticipationMap(
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

    /**
     * Adds listeners to ObservableList of {@code Persons} and ObservableList of {@code participation}
     * to create or modify participationMap on changes to personList and participationList
     * respectively to update the UI on user input and execution of the command. Listener is added
     * to ObservableList of {@code Tutorial} to update UI when tutorials are created or closed.
     */
    private void addListeners() {
        // Listener to recreate participationMap
        personList.addListener((ListChangeListener<Person>) change -> {
            this.participationMap = createParticipationMap(this.personList, this.participationList);
        });

        // Listener to add or remove participation from current participationMap
        participationList.addListener((ListChangeListener<Participation>) change -> {
            while (change.next()) {
                for (Participation removedParticipation : change.getRemoved()) {
                    ObservableList<Participation> participations =
                            participationMap.get(removedParticipation.getStudent());
                    if (participations != null) {
                        participations.remove(removedParticipation);
                    }
                }
                for (Participation addedParticipation : change.getAddedSubList()) {
                    ObservableList<Participation> participations =
                            participationMap.get(addedParticipation.getStudent());
                    if (participations != null) {
                        participations.add(addedParticipation);
                    }
                }
            }
        });

        //Listener to update tutorials Label
        tutorialList.addListener((ListChangeListener<Tutorial>) change -> {
            setTutorialsLabel();
        });
    }

    /**
     * Sets the label to display all tutorials currently available.
     */
    private void setTutorialsLabel() {
        StringBuilder tutorials = new StringBuilder();
        for (int i = 0; i < tutorialList.size(); i++) {
            tutorials.append(tutorialList.get(i).getSubject());
            if (i != tutorialList.size() - 1) {
                tutorials.append(" • ");
            }
        }
        this.tutorials.setText(tutorials.toString());
    }
}
