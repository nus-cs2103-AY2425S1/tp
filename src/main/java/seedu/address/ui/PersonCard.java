package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
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
    @FXML
    private Label overdue;

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
        projectStatus.setText("Project Status: " + person.getProjectStatus().toString());
        clientStatus.setText(person.getClientStatus().toString().toUpperCase());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        String deadlineString = person.checkAndGetDeadline();
        overdue.setText("");
        if (deadlineString.contains("[OVERDUE]")) {
            overdue.setText("OVERDUE");
            deadlineString = person.getDeadline().toString();
            cardPane.getStyleClass().add("overdue_bar");
        }
        deadline.setText("Deadline: " + deadlineString);

        Tooltip clientStatusTooltip = new Tooltip();
        clientStatusTooltip.setShowDelay(Duration.seconds(0.01));
        clientStatusTooltip.setText("Client Status");
        Tooltip.install(clientStatus, clientStatusTooltip);

        String payStatus = person.getPaymentStatus().toString();
        paymentStatus.setText("$");

        Tooltip paymentTooltip = new Tooltip();

        paymentTooltip.setShowDelay(Duration.seconds(0.01));

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
        HBox.setMargin(cardPane, new Insets(10, 10, 10, 10));
    }
}
