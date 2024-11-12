package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Person;
import seedu.address.model.role.Member;

/**
 * Panel containing the list of persons.
 */
public class ViewPersonCard extends UiPart<Region> {
    private static final String FXML = "ViewPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPersonCard.class);

    @FXML
    private VBox view;

    @FXML
    private Label viewTitle;

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

    @FXML
    private ScrollPane viewScroll;

    /**
     * Creates a {@code ViewPersonCard} with the given {@code Person}.
     */
    public ViewPersonCard(Person person) {
        super(FXML);
        if (person.getFavouriteStatus().equals(FavouriteStatus.FAVOURITE)) {
            viewTitle.setText("Contact Information \u2606");
        } else {
            viewTitle.setText("Contact Information");
        }
        viewName.setText(person.getName().fullName);
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
        viewRoles.getChildren().stream().map(node -> (Label) node)
                .filter(label -> label.getText().equals(Member.MEMBER_ROLE))
                .forEach(label -> label.setId("memberRole"));
        viewScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    public Label getViewName() {
        return this.viewName;
    }

    public Label getViewPhone() {
        return this.viewPhone;
    }

    public Label getViewTitle() {
        return this.viewTitle;
    }

    public Label getViewTelegram() {
        return this.viewTelegram;
    }

    public FlowPane getViewRoles() {
        return this.viewRoles;
    }

    public FlowPane getViewAttendance() {
        return this.viewAttendance;
    }
}

