package seedu.address.ui;

import java.util.logging.Logger;

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
public class FindPersonPanel extends UiPart<Region> {
    private static final String FXML = "FindPersonList.fxml";
    private final Logger logger = LogsCenter.getLogger(FindPersonPanel.class);

    @FXML
    private ListView<Person> findPersonListView;

    /**
     * Creates a {@code FindPersonPanel} with the given {@code ObservableList}.
     */
    public FindPersonPanel(ObservableList<Person> personList) {
        super(FXML);
        findPersonListView.setItems(personList);
        findPersonListView.setCellFactory(listView -> new FindPersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code FindPersonCard}.
     */
    class FindPersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FindPersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
