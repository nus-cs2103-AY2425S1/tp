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
        // Create a label for the tier
        Label tierLabel = new Label(client.getTier().toParsableString().toUpperCase());

        // Apply the existing style classes
        tierLabel.getStyleClass().add("label");

        // Add the tier-specific style class
        String tier = client.getTier().toParsableString().toLowerCase();
        tierLabel.getStyleClass().add(tier + "-tier");

        // Add the label to the FlowPane
        assignedTier.getChildren().add(tierLabel);
    }

    private void createStatus() {
        Label statusLabel = new Label(client.getStatus().toParsableString());

        // Apply a different style class based on the status value
        Status.StatusEnum status = client.getStatus().status;
        switch (status) {
        case NONE -> statusLabel.getStyleClass().add("none-status");
        case NON_URGENT -> statusLabel.getStyleClass().add("nonUrgent-status");
        case URGENT -> statusLabel.getStyleClass().add("urgent-status");
        default -> statusLabel = null;
        }
        if (statusLabel != null) {
            assignedStatus.getChildren().add(statusLabel);

        }
    }
}
