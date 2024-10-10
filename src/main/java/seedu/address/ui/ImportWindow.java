package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for a import page
 */
public class ImportWindow extends UiPart<Stage> {
    public static final String IMPORT_MESSAGE = "Choose File Location:";

    private static final Logger logger = LogsCenter.getLogger(ImportWindow.class);
    private static final String FXML = "ImportWindow.fxml";
    private final MainWindow mainWindow;

    @FXML
    private Label importMessage;

    @FXML
    private TextField fileDirectory;
//
//    /**
//     * Creates a new importWindow.
//     *
//     * @param root Stage to use as the root of the ImportWindow.
//     */
//    public ImportWindow(Stage root) {
//        super(FXML, root);
//        importMessage.setText(IMPORT_MESSAGE);
//    }
//
//    /**
//     * Creates a new ImportWindow.
//     */
//    public ImportWindow() {
//        this(new Stage());
//    }

    public ImportWindow(MainWindow mainWindow) {
        super(FXML, new Stage());
        importMessage.setText(IMPORT_MESSAGE);
        this.mainWindow = mainWindow;
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
    private void importCSV() {
        String filePath = fileDirectory.getText();
        if (isValidCsvPath(filePath)) {
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = Pattern.compile(",(?=(?:[^\"]|\"[^\"]*\")*$)").split(line);
                    String name = data[0].substring(1);
                    String number = data[1];
                    String email = data[2];
                    String address = data[3];
                    String command = String.format("add n/%s p/%s e/%s a/%s", name, number, email, address);
                    System.out.println(command);
                    this.mainWindow.execute(command);
                }
            } catch (FileNotFoundException | CommandException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The path is invalid, try the format:\n C:/path/to/file.csv", ButtonType.OK);
            alert.setHeaderText(null); // Optional: remove header text
            alert.setTitle("Invalid Import");
            alert.showAndWait();
        }
    }

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
