package careconnect.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import careconnect.commons.core.LogsCenter;
import careconnect.model.person.Person;
import careconnect.ui.MainWindow.FocusItemUpdater;
import careconnect.ui.MainWindow.FocusItems;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> implements ShiftTabFocusable {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList,
                           FocusItemUpdater focusItemUpdater, Consumer<Person> showSelectedPerson) {
        super(FXML);
        this.personListView.setItems(personList);
        this.personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.setOnMousePressed(event -> {
            focusItemUpdater.updateCurrentFocusItem(FocusItems.PERSON_LIST_ITEM);
        });
        // Add listener to respond to selection changes
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldPerspm, newPerson) -> {
            if (newPerson != null) {
                showSelectedPerson.accept(newPerson);
            }
        });
    }

    protected void clearSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    protected void setSelected(int index) {
        personListView.getSelectionModel().select(index);
    }

    @Override
    public void focus() {
        this.personListView.requestFocus();
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
