package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

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

    public PersonDetailPanel() {
        super(FXML);
        initializeLabels();
        hideAllFields();
    }

    private void initializeLabels() {
        nameLabel = nameLabel == null ? new Label() : nameLabel;
        phoneLabel = phoneLabel == null ? new Label() : phoneLabel;
        addressLabel = addressLabel == null ? new Label() : addressLabel;
        emailLabel = emailLabel == null ? new Label() : emailLabel;
        jobLabel = jobLabel == null ? new Label() : jobLabel;
        incomeLabel = incomeLabel == null ? new Label() : incomeLabel;
        remarkLabel = remarkLabel == null ? new Label() : remarkLabel;
    }

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

    private void setManagedAndVisible(javafx.scene.Node node, boolean value) {
        if (node != null) {
            node.setManaged(value);
            node.setVisible(value);
        }
    }

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

    private void setLabelText(Label label, String text) {
        if (label != null) {
            label.setText(text);
        }
    }

    private void setTier(String tier) {
        tierPane.getChildren().clear();
        Label tierLabel = new Label(tier.toUpperCase());
        tierLabel.getStyleClass().addAll("label", "detail-tier-label", tier.toLowerCase() + "-tier");
        tierPane.getChildren().add(tierLabel);
    }

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