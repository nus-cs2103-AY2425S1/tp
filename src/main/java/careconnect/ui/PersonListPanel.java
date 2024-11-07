package careconnect.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import careconnect.commons.core.LogsCenter;
import careconnect.model.person.Person;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> implements ShiftTabFocusable{
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, Consumer<Integer> setAndShowSelectedPerson) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell(setAndShowSelectedPerson));
    }

    @Override
    public void focus() {
        this.personListView.requestFocus();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private final Consumer<Integer> setAndShowSelectedPerson;

        public PersonListViewCell(Consumer<Integer> setAndShowSelectedPerson) {
            this.setAndShowSelectedPerson = setAndShowSelectedPerson;
        }
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, setAndShowSelectedPerson).getRoot());
            }
        }
    }

}
