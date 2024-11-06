package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.client.Client;
import seedu.address.model.status.Status;

/**
 * An UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/agentassist-level4/issues/336">The issue on AgentAssist level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    private ClientCardField phone = new ClientCardField();
    private ClientCardField address = new ClientCardField();
    private ClientCardField job = new ClientCardField();
    private ClientCardField email = new ClientCardField();
    private ClientCardField income = new ClientCardField();
    private ClientCardField remark = new ClientCardField();
    @FXML
    private FlowPane assignedTier;
    @FXML
    private FlowPane assignedStatus;
    @FXML
    private VBox cardFields;
    /**
     * Creates a {@code ClientCode} with the given {@code Client} and index to display.
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        createFields();
        createStatus();
        createTier();
    }
    private void createFields() {
        name.setText(client.getName().fullName);
        phone.setFields(ClientCardField.ICON_LITERAL_PHONE, client.getPhone().value);
        address.setFields(ClientCardField.ICON_LITERAL_ADDRESS, client.getAddress().value);
        email.setFields(ClientCardField.ICON_LITERAL_EMAIL, client.getEmail().value);
        job.setFields(ClientCardField.ICON_LITERAL_JOB, client.getJob().value);
        income.setFields(ClientCardField.ICON_LITERAL_INCOME, client.getIncome().toString());
        remark.setFields(ClientCardField.ICON_LITERAL_REMARK, client.getRemark().value);
        cardFields.getChildren().addAll(phone, address, email, job, income, remark);
    }

    private void createTier() {
        String tierText = client.getTier().toParsableString();
        if (!tierText.isEmpty()) {
            Label tierLabel = new Label(tierText.toUpperCase());

            // Apply the existing style classes
            tierLabel.getStyleClass().add("label");

            // Add the tier-specific style class
            String tier = client.getTier().toParsableString().toLowerCase();
            tierLabel.getStyleClass().add(tier + "-tier");

            // Add the label to the FlowPane
            assignedTier.getChildren().add(tierLabel);
        } else {
            assignedTier.setVisible(false); // Hide the FlowPane if it's empty
            assignedTier.setManaged(false); // Exclude it from layout calculations
        }
    }

    private void createStatus() {
        Status.StatusEnum status = client.getStatus().status;

        // Only create a label if the status is relevant
        if (status == Status.StatusEnum.NA) {
            assignedStatus.setVisible(false);
            assignedStatus.setManaged(false);
            return;
        }

        // Create and configure the label for relevant statuses
        Label statusLabel = new Label(client.getStatus().toParsableString());
        statusLabel.getStyleClass().add(getStatusStyleClass(status));

        // Add the label to assignedStatus and ensure it’s visible and managed
        assignedStatus.getChildren().add(statusLabel);
        assignedStatus.setVisible(true);
        assignedStatus.setManaged(true);
    }

    private String getStatusStyleClass(Status.StatusEnum status) {
        return switch (status) {
        case URGENT -> "urgent-status";
        case NON_URGENT -> "nonUrgent-status";
        default -> ""; // This case won’t occur due to the check in createStatus
        };
    }
}
