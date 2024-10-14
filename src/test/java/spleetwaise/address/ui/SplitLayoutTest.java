package spleetwaise.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javafx.scene.layout.Pane;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SplitLayoutTest extends TestFxJUnitAppRunner {

    private static final String LEFT_PANE = "#leftPane";
    private static final String RIGHT_PANE = "#rightPane";

    @Test
    @Order(1)
    @DisplayName("should contain both leftPane and rightPane if window width >= 800")
    public void verify_bothPanesVisible_whenLargeWidth() {
        // assumption: default window width is more than 800px
        Pane leftPane = lookup(LEFT_PANE).query();
        Pane rightPane = lookup(RIGHT_PANE).query();

        assertTrue(leftPane.isVisible(), "Left pane should be present.");
        assertTrue(rightPane.isVisible(), "Right pane should be present.");
    }
}
