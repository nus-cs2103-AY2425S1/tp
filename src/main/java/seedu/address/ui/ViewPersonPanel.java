package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A panel displaying details of a person stated in a "View" Command
 */
public class ViewPersonPanel extends UiPart<Region> {

    private static final String FXML = "ViewPersonPanel.fxml";
    private Person person;
    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label job;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label address;

    /**
     * Details to include inside the panel
     */
    public ViewPersonPanel(Person person) {
        super(FXML);
        this.person = person;
        setPersonDetails(person);
    }

    public void setPersonDetails(Person person) {
        name.setText(person.getName().fullName);
        job.setText("Job : " + person.getJob().jobName);
        status.setText("Status : " + person.getStatus());
        phone.setText("Phone Number : " + person.getPhone().value);
        email.setText("Email Address : " + person.getEmail().value);
        address.setText("Address : " + person.getAddress().value);
    }
}
