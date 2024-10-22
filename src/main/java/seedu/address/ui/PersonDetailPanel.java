package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information about a {@code Person}.
 * Shows all available fields including name, phone, address, email, job, income, tier, and remarks.
 * Fields can be shown or hidden based on the presence of a person's data.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailPanel.class);

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
    private Label remarkLabel;

    /**
     * Creates a new PersonDetailPanel.
     * This constructor initializes all labels and hides them by default.
     */
    public PersonDetailPanel() {
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
    }

    /**
     * Hides all fields in the detail panel by setting their managed and visible properties to false.
     * This is used when no person is selected or when clearing the panel.
     */
    private void hideAllFields() {
        setManagedAndVisible(nameLabel, false);
        setManagedAndVisible(phoneLabel, false);
        setManagedAndVisible(addressLabel, false);
        setManagedAndVisible(emailLabel, false);
        setManagedAndVisible(jobLabel, false);
        setManagedAndVisible(incomeLabel, false);
        setManagedAndVisible(tierPane, false);
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
     * Updates the detail panel to display information about the given person.
     * If person is null, all fields will be hidden.
     *
     * @param person The person whose details should be displayed, can be null
     */
    public void setPersonDetails(Person person) {
        if (person != null) {
            showAllFields();
            setLabelText(nameLabel, person.getName().fullName);
            setLabelText(phoneLabel, person.getPhone().value);
            setLabelText(addressLabel, person.getAddress().value);
            setLabelText(emailLabel, person.getEmail().value);
            setLabelText(jobLabel, person.getJob().value);
            setLabelText(incomeLabel, String.valueOf(person.getIncome()));
            setTier(person.getTier().toParsableString());
            setLabelText(remarkLabel, person.getRemark().value);
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

    /**
     * Sets the tier display in the detail panel.
     * Creates a new styled label for the tier and adds it to the tier pane.
     *
     * @param tier The tier value to display
     */
    private void setTier(String tier) {
        tierPane.getChildren().clear();
        Label tierLabel = new Label(tier.toUpperCase());
        tierLabel.getStyleClass().addAll("label", "detail-tier-label", tier.toLowerCase() + "-tier");
        tierPane.getChildren().add(tierLabel);
    }

    /**
     * Shows all fields in the detail panel by setting their managed and visible properties to true.
     * This is used when displaying a person's details.
     */
    private void showAllFields() {
        setManagedAndVisible(nameLabel, true);
        setManagedAndVisible(phoneLabel, true);
        setManagedAndVisible(addressLabel, true);
        setManagedAndVisible(emailLabel, true);
        setManagedAndVisible(jobLabel, true);
        setManagedAndVisible(incomeLabel, true);
        setManagedAndVisible(tierPane, true);
        setManagedAndVisible(remarkLabel, true);
    }
}
