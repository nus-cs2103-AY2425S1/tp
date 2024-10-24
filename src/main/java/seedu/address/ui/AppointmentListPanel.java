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
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Person> appointmentListView;

    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<Person> personList) {
        super(FXML);
        appointmentListView.setItems(personList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    static class AppointmentListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
