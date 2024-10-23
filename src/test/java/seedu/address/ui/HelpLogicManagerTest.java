package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HelpLogicManagerTest {

    private HelpLogicManager helpLogicManager;
    private HelpContentManager contentManager;

    @BeforeEach
    public void setUp() {
        contentManager = new HelpContentManager();
        helpLogicManager = new HelpLogicManager(contentManager);
    }

    @Test
    public void processTextWithBackticks_codeFormatting_appliesMonospacedFont() {
        TextFlow helpContentFlow = new TextFlow();
        String testContent = "This is `code` text.";

        helpLogicManager.processTextWithBackticks(testContent, helpContentFlow);

        // Check that text inside backticks is monospaced
        assertEquals("Monospaced", ((Text) helpContentFlow.getChildren().get(1)).getFont().getFamily(),
                "Text inside backticks should use Monospaced font");
    }
}
