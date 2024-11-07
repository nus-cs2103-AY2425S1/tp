package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents a confirmation dialog used to get user confirmation for certain actions,
 * such as deleting a client.
 */
public class ConfirmationDialog {

    /**
     * Displays a confirmation dialog to the user with the given client name.
     *
     * @param clientName The name of the client that the user is being asked to confirm deletion for.
     * @return {@code true} if the user confirms the action by pressing OK, {@code false} otherwise.
     */
    public static boolean showDeleteConfirmation(String clientName) {
        FXMLLoader loader =
                new FXMLLoader(ConfirmationDialog.class.getResource("/view/ConfirmationDialog.fxml"));
        Stage dialogStage = new Stage();

        try {
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Delete Confirmation");

            ConfirmationDialogController controller = loader.getController();
            controller.setHeaderLabelText("Are you sure you want to delete " + clientName + "?");
            controller.setClientName(clientName + " still has active listing(s)!");

            dialogStage.showAndWait();

            return controller.isConfirmed();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
