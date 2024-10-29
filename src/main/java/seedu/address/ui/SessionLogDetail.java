package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.log.Log;
import seedu.address.model.person.Person;

public class SessionLogDetail extends UiPart<Region> {

    private static final String FXML = "SessionLogDetail.fxml";

    @FXML
    private Label detailId;

    @FXML
    private Label detailName;

    @FXML
    private Label detailDate;

    @FXML
    private Label detailSessionEntry;

    @FXML
    private FlowPane detailTags;

    public SessionLogDetail() {
        super(FXML);
    }

    public void setLogDetails(Log log, Person person) {
        detailId.setText(person.getIdentityNumber().toString());
        detailName.setText(person.getName().toString());
        detailDate.setText(log.getAppointmentDate().toString());
        detailSessionEntry.setText(log.toDetailedString());

        detailTags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.toString());
            detailTags.getChildren().add(tagLabel);
        });
    }
}