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
public class FindAppointmentPanel extends UiPart<Region> {
    private static final String FXML = "FindAppointmentList.fxml";
    private final Logger logger = LogsCenter.getLogger(FindAppointmentPanel.class);

    @FXML
    private ListView<Person> findAppointmentListView;

    /**
     * Creates a {@code FindPersonPanel} with the given {@code ObservableList}.
     */
    public FindAppointmentPanel(ObservableList<Person> personList) {
        super(FXML);
        findAppointmentListView.setItems(personList);
        findAppointmentListView.setCellFactory(listView -> new FindAppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code FindPersonCard}.
     */
    class FindAppointmentListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FindAppointmentCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
