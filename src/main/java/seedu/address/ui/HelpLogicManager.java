package seedu.address.ui;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Handles logic related to displaying and formatting help content in the HelpWindow.
 */
public class HelpLogicManager {

    private final HelpContentManager contentManager;

    /**
     * Constructs a HelpLogicManager with the specified HelpContentManager.
     * @param contentManager The HelpContentManager used to manage help content.
     * @throws AssertionError if the provided HelpContentManager is null.
     */
    public HelpLogicManager(HelpContentManager contentManager) {
        assert contentManager != null : "HelpContentManager should not be null";
        this.contentManager = contentManager;
    }

    /**
     * Handles the Table of Contents (TOC) selection to display appropriate content in the HelpWindow.
     * @param selectedItem The selected item from the TOC.
     * @param helpWindow The HelpWindow where the content will be displayed.
     */
    public void handleTocSelection(String selectedItem, HelpWindow helpWindow) {
        assert selectedItem != null && !selectedItem.isEmpty() : "Selected TOC item should not be null or empty";
        assert helpWindow != null : "HelpWindow should not be null";
        if ("Command Summary".equals(selectedItem)) {
            helpWindow.displayCommandSummary();
        } else {
            helpWindow.displayHelpContent(selectedItem);
        }
    }

    /**
     * Formats the TextFlow with content.
     */
    public void formatTextFlow(String content, TextFlow helpContentFlow) {
        assert content != null && !content.isEmpty() : "Content should not be null or empty";
        assert helpContentFlow != null : "TextFlow should not be null";

        helpContentFlow.getChildren().clear();
        String[] parts = content.split("\n", 2);
        String titleText = parts[0];
        String bodyText = parts.length > 1 ? parts[1] : "";

        Text title = new Text(titleText + "\n");
        title.setId("helpTitle");

        helpContentFlow.getChildren().add(title);
        processTextWithBackticks(bodyText, helpContentFlow);
    }

    /**
     * Processes text and applies formatting for backticks as inline code.
     */
    public void processTextWithBackticks(String text, TextFlow helpContentFlow) {

        assert text != null && !text.isEmpty() : "Text should not be null or empty";
        assert helpContentFlow != null : "TextFlow should not be null";

        String[] segments = text.split("`");
        boolean isCode = false;

        for (String segment : segments) {
            Text t = new Text(segment);
            if (isCode) {
                t.setFont(javafx.scene.text.Font.font("Monospaced"));
                t.setStyle("-fx-font-size: 14px; -fx-fill: #3b82f6;");
            } else {
                t.setStyle("-fx-font-size: 14px;");
            }
            helpContentFlow.getChildren().add(t);
            isCode = !isCode;
        }
    }
}
