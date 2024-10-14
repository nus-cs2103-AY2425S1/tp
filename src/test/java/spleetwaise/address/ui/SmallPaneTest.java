package spleetwaise.address.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import spleetwaise.address.MainApp;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SmallPaneTest extends TestFxJUnitAppRunner {

    private static final String LEFT_PANE = "#leftPane";
    private static final String RIGHT_PANE = "#rightPane";

    @BeforeEach
    @Override
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp::new);
        FxToolkit.showStage();
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        WaitForAsyncUtils.asyncFx(() -> stage.setWidth(700));

        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @Test
    @Order(2)
    @DisplayName("should only contain leftPane if window width < 800")
    public void verify_onlyLeftPaneVisible_ifSmallWidth() {
        Pane leftPane = lookup(LEFT_PANE).query();

        assertTrue(leftPane.isVisible(), "Left pane should still be present.");
        assertThrows(EmptyNodeQueryException.class, () -> lookup(RIGHT_PANE).query(),
                "Right pane should not be present."
        );
    }
}
