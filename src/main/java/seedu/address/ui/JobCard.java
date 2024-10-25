package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;



/**
 * An UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobListCard.fxml";

    public final Job job;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label company;
    @FXML
    private Label salary;
    @FXML
    private Label description;
    @FXML
    private FlowPane requirements;
    @FXML
    private Label matches;

    /**
     * Creates a {@code JobCode} with the given {@code Job} and index to display.
     */
    public JobCard(Job job, int displayedIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayedIndex + ". ");
        name.setText(job.getName().fullName);
        company.setText(job.getCompany().fullName);
        salary.setText(job.getSalary().toString());
        description.setText(job.getDescription().value);
        job.getRequirements().stream()
                .sorted(Comparator.comparing(requirement -> requirement.tagName))
                .forEach(requirement -> requirements.getChildren().add(new Label(requirement.tagName)));
        matches.setText("Employee count - " + job.getMatches().size());
    }
}
