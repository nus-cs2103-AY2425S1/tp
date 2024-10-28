package seedu.address.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Controller for a import page
 */
public class ImportWindow extends UiPart<Stage> {
    public static final String IMPORT_MESSAGE = "Choose File Location:";

    private static final Logger logger = LogsCenter.getLogger(ImportWindow.class);
    private static final String FXML = "ImportWindow.fxml";
    private final Logic logic;

    @FXML
    private Label importMessage;

    @FXML
    private TextField inputDir;

    /**
     * Constructs importWindow
     * @param logic
     */
    public ImportWindow(Logic logic) {
        super(FXML, new Stage());
        importMessage.setText(IMPORT_MESSAGE);
        this.logic = logic;
    }

    /**
     * Shows the import window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing import page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the import window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the import window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the import window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Gets file to import
     */
    @FXML
    private void importCsv() {
        String filePath = inputDir.getText();
        if (isValidCsvPath(filePath)) {
            try (Scanner scanner = new Scanner(new File(filePath))) {
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = Pattern.compile(",(?=(?:[^\"]|\"[^\"]*\")*$)").split(line);
                    String name = data[0];
                    String number = data[1];
                    String email = data[2];
                    String address = data[3];
                    String allTags = data[4];
                    String tagCommand = tagsArray(allTags);
                    String favourite = " ";
                    if (Boolean.valueOf(data[5])) {
                        favourite = " /f ";
                    }
                    String department = data[6];
                    String command = String.format("add n/%s p/%s e/%s a/%s %s%sd/%s",
                            name, number, email, address, tagCommand, favourite, department);
                    System.out.println(command);
                    this.logic.execute(command);
                }
            } catch (FileNotFoundException | CommandException | ParseException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Invalid import data, operation cancelled", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("Invalid Import Data");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "The path is invalid, try the format:\n C:/path/to/file.csv", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("Invalid Import");
            alert.showAndWait();
        }
    }

    private static String tagsArray(String tagsString) {
        String trimmed = tagsString.substring(1, tagsString.length() - 1).trim();
        String[] tags = trimmed.split("\\s*,\\s*");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tags.length; i++) {
            result.append("t/").append(tags[i]);
            if (i < tags.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    /**
     * Checks CSV path
     * @param filePath
     * @return
     */
    public static boolean isValidCsvPath(String filePath) {
        String regex = "^[a-zA-Z]:([/\\\\\\\\][^<>:\\\"/\\\\\\\\|?*]+)*[/\\\\\\\\]?[^<>:\\\"/\\\\\\\\|?*]+\\.csv$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filePath);
        if (!matcher.matches()) {
            return false;
        }
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            return false;
        }
        return true;
    }
}
