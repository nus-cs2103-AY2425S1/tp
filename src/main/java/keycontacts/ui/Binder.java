package keycontacts.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

/**
 * The binder in the middle of the notebook
 */
public class Binder extends UiPart<Region> {

    private static final double ARC_RADIUS_X = 8.0;
    private static final double ARC_RADIUS_Y = 6.0;
    private static final double ARC_LENGTH = 260.0;
    private static final double ARC_START_ANGLE = -40.0;

    private static final String FXML = "Binder.fxml";

    @FXML
    private VBox loopParent;

    /**
     * Default constructor
     * @param initialHeight the initial height of the scene to update binders
     */
    public Binder(double initialHeight) {
        super(FXML);
        updateArcs(initialHeight);
    }

    /**
     * Attaches the listener to the scene's height property to update the binders
     */
    public void addListener() {
        this.getRoot().getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            updateArcs(newValue.doubleValue());
        });
    }

    /**
     * Updates the number of arcs acting as the binders based on the height of the scene
     * @param height the height of the scene
     */
    private void updateArcs(double height) {
        loopParent.getChildren().clear();

        int numArcs = (int) (height / (ARC_RADIUS_Y * 2 + loopParent.getSpacing())) - 5;

        addArcs(numArcs);
    }

    /**
     * Adds {@code numArcs} arcs to {@code loopParent}
     */
    private void addArcs(int numArcs) {
        for (int i = 0; i < numArcs; i++) {
            Arc arc = new Arc();
            arc.setRadiusX(ARC_RADIUS_X);
            arc.setRadiusY(ARC_RADIUS_Y);
            arc.setLength(ARC_LENGTH);
            arc.setStartAngle(ARC_START_ANGLE);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Color.BLACK);

            loopParent.getChildren().add(arc);
        }
    }
}
