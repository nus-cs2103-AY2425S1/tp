package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.Observable;
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
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private ObservableList<Person> personList;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList<Person>}
     * and a {@code PersonSelectionHandler} for handling user selections of a person.
     */
    public PersonListPanel(ObservableList<Person> personList, PersonDetailView personDetailView) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                personDetailView.update(newValue);
            }
        });
        this.personList = personList;
        this.personList.addListener((Observable observable) -> {
            // set the visibility PersonDetailView to be false when the personList is empty
            if (personList.isEmpty()) {
                personDetailView.getRoot().setVisible(false);
            } else {
                personDetailView.update(personList.get(0));
            }
        });

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
                PersonCard personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
            }
        }
    }
}
