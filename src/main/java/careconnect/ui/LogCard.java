package careconnect.ui;

import java.text.SimpleDateFormat;

import careconnect.model.log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Log}.
 */
public class LogCard extends UiPart<Region> {

    private static final String FXML = "LogCard.fxml";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd MMM yyyy HH:mm");

    public final Log log;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label remark;

    /**
     * Creates a {@code LogCode} with the given {@code Log} and index to display.
     */
    public LogCard(Log log, int displayedIndex) {
        super(FXML);
        this.log = log;
        id.setText(displayedIndex + ". ");
        date.setText(simpleDateFormat.format(log.getDate()));
        remark.setText(log.getRemark());
    }
}
