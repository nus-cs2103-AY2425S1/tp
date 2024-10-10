package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label requirements;
    @FXML
    private Label description;

    /**
     * Creates a {@code JobCode} with the given {@code Job} and index to display.
     */
    public JobCard(Job job, int displayedIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayedIndex + ". ");
        name.setText(job.getName().value);
        company.setText(job.getCompany().value);
        salary.setText(String.valueOf(job.getSalary().value));
        requirements.setText(job.getRequirements().value);
        description.setText(job.getDescription().value);
    }
}
