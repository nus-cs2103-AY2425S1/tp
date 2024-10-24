package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.FilteredAppointment.FilteredAppointment;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code FilteredAppointment}
 */
public class FilteredApptCard extends UiPart<Region> {
    private static final String FXML = "FilteredApptListCard.fxml";

    public final Person person;
    public final Appt appt;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label appointmentDateTime;
    @FXML
    private Label name;
    @FXML
    private Label nric;


    /**
     * Creates a {@code FilteredApptCard} with the given {@code FilteredAppointment} and index to display
     */
    public FilteredApptCard(FilteredAppointment filteredAppointment, int displayedIndex) {
        super(FXML);
        this.appt = filteredAppointment.getAppt();
        this.person = filteredAppointment.getPerson();
        id.setText(displayedIndex + ". ");
        appointmentDateTime.setText(appt.toString());
        name.setText(person.getName().fullName);
        nric.setText(person.getNric().value);
    }

}
