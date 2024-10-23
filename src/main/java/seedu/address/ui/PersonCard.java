package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label projectStatus;
    @FXML
    private Label paymentStatus;
    @FXML
    private Label clientStatus;
    @FXML
    private Label deadline;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        projectStatus.setText("Project status: " + person.getProjectStatus().toString());
//        paymentStatus.setText("Payment status: " + person.getPaymentStatus().toString());
        clientStatus.setText("Client Status: " + person.getClientStatus().toString());
        deadline.setText("Deadline: " + person.checkAndGetDeadline());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        String payStatus = person.getPaymentStatus().toString();
        paymentStatus.setText("$");

        Tooltip paymentTooltip = new Tooltip();
        paymentTooltip.setShowDelay(Duration.seconds(0.01));

        // Apply color based on payment status
        switch (payStatus) {
        case "pending":
            paymentStatus.getStyleClass().add("payment_status_pending");
            paymentTooltip.setText("pending");
            break;
        case "late":
            paymentStatus.getStyleClass().add("payment_status_late");
            paymentTooltip.setText("late");
            break;
        case "paid":
            paymentStatus.getStyleClass().add("payment_status_paid");
            paymentTooltip.setText("paid");
            break;
        case "partial":
            paymentStatus.getStyleClass().add("payment_status_partial");
            paymentTooltip.setText("partial");
            break;
        default:
            break;
        }
        Tooltip.install(paymentStatus, paymentTooltip);
    }
}
