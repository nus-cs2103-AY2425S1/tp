package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandCommons;
import seedu.address.model.client.Client;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * A UI component that displays detailed information about a {@code Client}.
 * Shows all available fields including name, phone, address, email, job, income, tier, status, and remarks.
 * Fields can be shown or hidden based on the presence of a client's data.
 */
public class ClientDetailPanel extends UiPart<Region> {
    private static final String FXML = "ClientDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClientDetailPanel.class);

    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private FlowPane tierPane;
    @FXML
    private FlowPane statusPane;
    @FXML
    private HBox tagsContainer;
    @FXML
    private HBox tagsGroup;
    @FXML
    private Label remarkLabel;

    /**
     * Creates a new ClientDetailPanel.
     * This constructor initializes all labels and hides them by default.
     */
    public ClientDetailPanel() {
        super(FXML);
        initializeLabels();
        hideAllFields();
    }

    /**
     * Initializes all FXML-injected labels, creating new ones if they are null.
     * This ensures all label references are valid for manipulation.
     */
    private void initializeLabels() {
        nameLabel = nameLabel == null ? new Label() : nameLabel;
        phoneLabel = phoneLabel == null ? new Label() : phoneLabel;
        addressLabel = addressLabel == null ? new Label() : addressLabel;
        emailLabel = emailLabel == null ? new Label() : emailLabel;
        jobLabel = jobLabel == null ? new Label() : jobLabel;
        incomeLabel = incomeLabel == null ? new Label() : incomeLabel;
        remarkLabel = remarkLabel == null ? new Label() : remarkLabel;
        tagsSetting();
    }

    /**
     * Setting up group of tag(s) for alignment setup
     */
    private void tagsSetting() {
        if (tagsGroup == null) {
            tagsGroup = new HBox();
            tagsGroup.setSpacing(10);
        }
        if (tagsContainer == null) {
            tagsContainer = new HBox();
            tagsContainer.setAlignment(javafx.geometry.Pos.CENTER);
            tagsContainer.getChildren().add(tagsGroup);
        }
    }

    /**
     * Hides all fields in the detail panel by setting their managed and visible properties to false.
     * This is used when no client is selected or when clearing the panel.
     */
    private void hideAllFields() {
        setManagedAndVisible(nameLabel, false);
        setManagedAndVisible(phoneLabel, false);
        setManagedAndVisible(addressLabel, false);
        setManagedAndVisible(emailLabel, false);
        setManagedAndVisible(jobLabel, false);
        setManagedAndVisible(incomeLabel, false);
        setManagedAndVisible(tagsContainer, false);
        setManagedAndVisible(remarkLabel, false);
    }

    /**
     * Sets both the managed and visible properties of a JavaFX node.
     *
     * @param node The JavaFX node to modify
     * @param value The boolean value to set for both managed and visible properties
     */
    private void setManagedAndVisible(javafx.scene.Node node, boolean value) {
        if (node != null) {
            node.setManaged(value);
            node.setVisible(value);
        }
    }

    /**
     * Updates the detail panel to display information about the given client.
     * If client is null, all fields will be hidden.
     *
     * @param client The client whose details should be displayed, can be null
     */
    public void setClientDetails(Client client) {
        if (client != null) {
            showAllFields();
            setLabelText(nameLabel, client.getName().fullName);
            setLabelText(phoneLabel, client.getPhone().value);
            setLabelText(addressLabel, client.getAddress().value);
            setLabelText(emailLabel, client.getEmail().value);
            setLabelText(jobLabel, client.getJob().value);
            setLabelText(incomeLabel, String.valueOf(client.getIncome()));
            tagsGroup.getChildren().clear();
            setTier(client.getTier());
            setStatus(client.getStatus());
            setRemarkText(remarkLabel, client.getRemark().value);
            boolean hasTags = !tagsGroup.getChildren().isEmpty();
            setManagedAndVisible(tagsContainer, hasTags);
        } else {
            hideAllFields();
        }
    }

    /**
     * Sets the text of a label if the label is not null.
     *
     * @param label The label to update
     * @param text The text to set
     */
    private void setLabelText(Label label, String text) {
        if (label != null) {
            label.setText(text);
        }
    }

    private void setRemarkText(Label remarkLabel, String remarkText) {
        if (remarkText.equalsIgnoreCase(CommandCommons.DEFAULT_REMARK)) {
            setLabelText(remarkLabel, "");
        } else {
            setLabelText(remarkLabel, remarkText);
        }
    }

    /**
     * Sets the tier display in the detail panel.
     * Creates a new styled label for the tier and adds it to the tier pane.
     *
     * @param tier The tier value to display
     */
    private void setTier(Tier tier) {
        if (tier.tierName != Tier.TierEnum.NA) {
            Label tierLabel = new Label(tier.getValue().toUpperCase());
            tierLabel.getStyleClass().addAll("label", tier.getValue().toLowerCase() + "-tier");
            tagsGroup.getChildren().add(tierLabel);
        }
    }

    /**
     * Sets the status display in the detail panel.
     * Creates a new styled label for the status and adds it to the status pane.
     *
     * @param status The status to display
     */
    private void setStatus(Status status) {
        if (status.status != Status.StatusEnum.NA) {
            Label statusLabel = new Label(status.getValue());
            String styleClass = switch (status.status) {
            case URGENT -> "urgent-status";
            case NON_URGENT -> "nonUrgent-status";
            default -> "";
            };
            statusLabel.getStyleClass().addAll("label", styleClass);
            tagsGroup.getChildren().add(statusLabel);
        }
    }

    /**
     * Shows all fields in the detail panel by setting their managed and visible properties to true.
     * This is used when displaying a client's details.
     */
    private void showAllFields() {
        setManagedAndVisible(nameLabel, true);
        setManagedAndVisible(phoneLabel, true);
        setManagedAndVisible(addressLabel, true);
        setManagedAndVisible(emailLabel, true);
        setManagedAndVisible(jobLabel, true);
        setManagedAndVisible(incomeLabel, true);
        setManagedAndVisible(tagsContainer, true);
        setManagedAndVisible(remarkLabel, true);
    }
}
