package seedu.address.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;


/**
 * Controller for a import page
 */
public class ExportWindow extends UiPart<Stage> {
    public static final String EXPORT_MESSAGE = "Choose File Location:";

    private static final Logger logger = LogsCenter.getLogger(ExportWindow.class);
    private static final String FXML = "ExportWindow.fxml";
    private final Logic logic;

    @FXML
    private Label exportMessage;

    @FXML
    private TextField inputDir;

    /**
     * Constructs exportWindow
     * @param logic
     */
    public ExportWindow(Logic logic) {
        super(FXML, new Stage());
        exportMessage.setText(EXPORT_MESSAGE);
        this.logic = logic;
    }

    /**
     * Shows the export window.
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
        logger.fine("Showing export page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the export window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the export window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the export window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Create file to export
     */
    @FXML
    private void exportCsv() {
        String filePath = inputDir.getText();
        if (isValidCsvPath(filePath)) {
            try {
                String[] header = {"Name", "Number", "Email", "Address", "Tags", "isFavourite", "Department"};
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(String.join(",", header));
                writer.newLine();
                ObservableList<Person> people = logic.getAddressBook().getPersonList();
                for (Person person: people) {
                    String name = person.getName().toString();

                    String phone = person.getPhone().toString();

                    String email = person.getEmail().toString();

                    String address = person.getAddress().toString();

                    String tags = person.getTags().toString();
                    StringBuilder formattedTags = formatTags(tags);

                    String isFavourite = Boolean.toString(person.isFavorite());

                    String department = person.getDepartment().toString();

                    String result = toCsv(name, phone, email, address,
                            formattedTags.toString(), isFavourite, department);
                    writer.write(result);
                    writer.newLine();
                    System.out.println(result);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "The path is invalid, try the format:\n C:/path/to/file.csv", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("Invalid Export");
            alert.showAndWait();
        }
    }

    private static String toCsv(String... values) {
        StringBuilder csvBuilder = new StringBuilder();

        for (String value : values) {
            if (csvBuilder.length() > 0) {
                csvBuilder.append(",");
            }
            if (value.contains(",") || value.contains("\"")) {
                csvBuilder.append("\"" + value.replace("\"", "\"\"") + "\"");
            } else {
                csvBuilder.append(value);
            }
        }

        return csvBuilder.toString();
    }

    private static StringBuilder formatTags(String tags) {
        String formattedTags = tags.substring(1, tags.length() - 1);
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(formattedTags);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(matcher.group(1));
        }
        return result;
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
