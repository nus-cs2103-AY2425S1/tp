package seedu.hireme.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * An UI component that displays information of an {@code InternshipApplication}.
 */
public class InternshipApplicationCard extends UiPart<Region> {

    private static final String FXML = "InternshipApplicationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final InternshipApplication internshipApplication;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label role;
    @FXML
    private Label date;
    @FXML
    private Label email;
    @FXML
    private Label status;

    /**
     * Creates a {@code InternshipApplicationCard} with the given {@code InternshipApplication} and index to display.
     */
    public InternshipApplicationCard(InternshipApplication internshipApplication, int displayedIndex) {
        super(FXML);
        this.internshipApplication = internshipApplication;

        id.setText(displayedIndex + ". ");
        name.setText(internshipApplication.getCompany().getName().toString());
        email.setText("Email: " + internshipApplication.getCompany().getEmail().toString());
        role.setText("Role: " + internshipApplication.getRole().toString());
        date.setText("Date of Application: " + internshipApplication.getDateOfApplication().toString());

        String statusValue = internshipApplication.getStatus().toString().toUpperCase();
        status.setText(statusValue);

        switch (statusValue) {
        case "PENDING":
            status.getStyleClass().add("status-pending");
            break;
        case "ACCEPTED":
            status.getStyleClass().add("status-accepted");
            break;
        case "REJECTED":
            status.getStyleClass().add("status-rejected");
            break;
        default:
            break;
        }
    }
}
