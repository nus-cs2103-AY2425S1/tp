package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.security.PasswordManager;


/**
 * The PasswordPromptDialog class provides a modal dialog for users to enter
 * or set their password. It interacts with the PasswordManager to handle
 * password storage and validation.
 */
public class PasswordPromptDialog {
    /**
     * Displays the password prompt dialog, allowing the user to enter or set
     * their password.
     *
     * @param owner The owner stage of the dialog.
     * @return True if the user successfully entered the correct password or set a new password; false otherwise.
     */
    public static boolean display(Stage owner) {
        // Read the existing password, if any
        String existingPassword = PasswordManager.readPassword(null);

        try {
            FXMLLoader loader = new FXMLLoader(PasswordPromptDialog.class.getResource(
                    "/view/PasswordPromptDialog.fxml"));
            Parent root = loader.load();
            PasswordPromptDialogController controller = loader.getController();
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            controller.setDialog(dialog);
            controller.setExistingPassword(existingPassword);
            controller.setPasswordLabel();
            Scene scene = new Scene(root, 400, 200);
            scene.getStylesheets().add("/view/DarkTheme.css"); // Apply the CSS file

            dialog.getIcons().add(new Image(PasswordPromptDialog.class.getResourceAsStream("/images/VBookLogo.png")));
            dialog.setTitle("Welcome to VBook!");
            dialog.setScene(scene);
            dialog.showAndWait();
            // Return true if the password is correct or if the user set a new password
            return existingPassword == null
                    || PasswordManager.isPasswordCorrect(controller.getPasswordField().getText(), null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
