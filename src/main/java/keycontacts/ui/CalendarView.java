package keycontacts.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * The calendar view
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "Calendar.fxml";

    private static final int NUM_ROWS = 7;
    private static final int NUM_COLS = 24;

    @FXML
    private GridPane grid;

    public CalendarView() {
        super(FXML);
        update();
    }

    public void update() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1;");
                grid.add(pane, j, i);
            }
        }
    }
}
