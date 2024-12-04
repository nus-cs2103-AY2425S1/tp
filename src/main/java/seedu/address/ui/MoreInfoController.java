package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * Controller for the MoreInfo window, which displays additional information about a client.
 */
public class MoreInfoController {
    private Person person;

    @FXML
    private Label clientNameLabel;
    @FXML
    private Label clientEmailLabel;
    @FXML
    private Label clientPhoneLabel;
    @FXML
    private Label clientAppointmentLabel;
    @FXML
    private Label clientRoleLabel;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label clientRemarksLabel;
    @FXML
    private TextArea remarkInput;

    /**
     * Initializes the MoreInfoController and sets the profile picture.
     */
    @FXML
    public void initialize() {
        Image image = new Image("/images/profile-picture.png");
        profilePicture.setImage(image);
        profilePicture.setFitWidth(70);
        profilePicture.setFitHeight(70);

        remarkInput.setOnKeyPressed(this::handleKeyPressed);
    }

    public void setPerson(Person person) {
        this.person = person;
        clientNameLabel.setText("" + person.getName());
        clientEmailLabel.setText("Email: " + person.getEmail());
        clientPhoneLabel.setText("Phone: " + person.getPhone());
        clientAppointmentLabel.setText("Appointment: " + person.getAppointment());
        clientRoleLabel.setText("Role: " + person.getRole());
        clientRemarksLabel.setText("" + person.getRemark());
    }

    /**
     * Handles key pressed events in the remark input text area.
     * @param event The key event that occurred.
     */
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String newRemark = remarkInput.getText().trim();
            if (!newRemark.isEmpty()) {
                clientRemarksLabel.setText(newRemark);
                person.updateRemark(newRemark);
                remarkInput.clear();
            }
            event.consume();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            handleClose();
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) clientNameLabel.getScene().getWindow();
        stage.close();
    }
}
