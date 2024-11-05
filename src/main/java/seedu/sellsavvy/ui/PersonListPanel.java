package seedu.sellsavvy.ui;

import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final String EMPTY_PERSON_LIST_MESSAGE = "You do not have any recorded customers currently.";
    private static final String NO_RELATED_PERSONS_FOUND = "No related customers found.";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ListChangeListener<Person> orderChangeListener = change -> handleChangeInPersons();
    private ObservableList<Person> personList;

    @FXML
    private ListView<Person> personListView;
    @FXML
    private Label personListEmpty;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        personList.addListener(orderChangeListener);;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        handleChangeInPersons();
    }

    /**
     * Handles events whether a displayed person list changes.
     */
    private void handleChangeInPersons() {
        toggleNoPersonsLabel(personListView.getItems().isEmpty());
    }

    /**
     * Toggles whether the {@code personListEmpty} is displayed and determines the
     * message displayed.
     */
    private void toggleNoPersonsLabel(boolean personListIsEmpty) {
        setPersonListEmptyText();
        personListEmpty.setManaged(personListIsEmpty);
        personListEmpty.setVisible(personListIsEmpty);
        personListView.setManaged(!personListIsEmpty);
        personListView.setVisible(!personListIsEmpty);
    }

    /**
     * Sets the text displayed in {@code personListEmpty}.
     */
    private void setPersonListEmptyText() {
        FilteredList<Person> filteredList = (FilteredList<Person>) personList;
        if (filteredList.getPredicate() == PREDICATE_SHOW_ALL_PERSONS) {
            personListEmpty.setText(EMPTY_PERSON_LIST_MESSAGE);
        } else {
            personListEmpty.setText(NO_RELATED_PERSONS_FOUND);
        }
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

}
