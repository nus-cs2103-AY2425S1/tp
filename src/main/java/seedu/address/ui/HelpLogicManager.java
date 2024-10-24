package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;

import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Handles logic related to displaying and formatting help content in the HelpWindow.
 */
public class HelpLogicManager {

    private final HelpContentManager contentManager;

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
     * Formats the TextFlow with content, adding hyperlinks where necessary.
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

        if (content.contains("Quick start")) {
            addQuickStartHyperlinks(helpContentFlow); // Handles hyperlinks specific to "Quick Start"
        } else {
            processTextWithBackticks(bodyText, helpContentFlow);
        }
    }

    /**
     * Adds hyperlinks for the "Quick Start" section.
     */
    private void addQuickStartHyperlinks(TextFlow helpContentFlow) {

        assert helpContentFlow != null : "TextFlow should not be null";

        Text beforeLink1 = new Text("1. Ensure you have Java ");
        Hyperlink javaLink = new Hyperlink("17");
        javaLink.setOnAction(e -> openHyperlink("https://www.oracle.com/java/technologies/downloads/#java17"));
        Text afterLink1 = new Text(" or above installed in your Computer. (Visit the ");

        Hyperlink oracleLink = new Hyperlink("Oracle website");
        oracleLink.setOnAction(e -> openHyperlink("https://www.oracle.com/java/technologies/downloads/#java17"));
        Text afterOracleLink = new Text(" for Java 17 installation.)");

        Text beforeLink2 = new Text("\n\n2. Download the latest ");
        Hyperlink jarLink = new Hyperlink(".jar file");
        jarLink.setOnAction(e -> openHyperlink("https://github.com/AY2425S1-CS2103T-F14b-4/tp/releases/tag/v1.0"));

        Text instructions = new Text("\n\n3. Copy the `.jar` file to your desired folder and open a terminal."
                + "\n\n4. Run the application using `java -jar fart_in_a.jar`."
                + "\n\n5. Type commands in the command box and press Enter.");

        helpContentFlow.getChildren().addAll(
                beforeLink1, javaLink, afterLink1, oracleLink, afterOracleLink,
                beforeLink2, jarLink, instructions
        );
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

    /**
     * Opens a hyperlink in the system's default browser.
     */
    public void openHyperlink(String url) {

        assert url != null && !url.isEmpty() : "URL should not be null or empty";

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Platform does not support desktop browsing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
