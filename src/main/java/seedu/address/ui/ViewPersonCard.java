package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class ViewPersonCard extends UiPart<Region> {
    private static final String FXML = "ViewPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPersonCard.class);

    @FXML
    private VBox view;

    @FXML
    private Label personInfo;

    @FXML
    private Label viewName;

    @FXML
    private Label viewPhone;

    @FXML
    private Label viewEmail;

    @FXML
    private Label viewTelegram;

    @FXML
    private FlowPane viewRoles;

    @FXML
    private FlowPane viewAttendance;

    /**
     * Creates a {@code ViewPersonCard} with the given {@code Person}.
     */
    public ViewPersonCard(Person person) {
        super(FXML);
        if (person.getFavouriteStatus().equals(FavouriteStatus.FAVOURITE)) {
            viewName.setText(person.getName().fullName +  " \u2665");
        } else {
            viewName.setText(person.getName().fullName);
        }
        viewPhone.setText(person.getPhone().value);
        viewTelegram.setText("@" + person.getTelegram().value);
        viewEmail.setText(person.getEmail().value);
        person.getAttendance().stream()
                .sorted((d1, d2) -> d1.getDate().isBefore(d2.getDate()) ? 1 : -1)
                        .forEach(session -> viewAttendance.getChildren().add(new Label(session.getDate().toString())));
        viewAttendance.getChildren().stream().forEach(label -> label.setId("viewSesh"));
        if (person.getAttendance().isEmpty()) {
            viewAttendance.getChildren().add(new Label("NA"));
            viewAttendance.getChildren().stream().forEach(label -> label.setId("viewNA"));
        }
        person.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> viewRoles.getChildren().add(new Label(role.roleName)));
        viewRoles.getChildren().stream().forEach(label -> label.setId("viewRole"));
//        info = new SimpleStringProperty(person.generateContactInformation());
//        personInfo.textProperty().bind(info);
    }
}

