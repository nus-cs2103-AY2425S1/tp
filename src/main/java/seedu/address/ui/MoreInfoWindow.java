package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * A window that displays detailed information about a specific client.
 */
public class MoreInfoWindow {

    private Stage stage;

    /**
     * Creates a MoreInfoWindow with the specified client details.
     *
     * @param person The client whose information is to be displayed.
     */
    public MoreInfoWindow(Person person) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Client Information");

        try {
            FXMLLoader loader = new FXMLLoader(MoreInfoWindow.class.getResource("/view/MoreInfoWindow.fxml"));
            VBox layout = loader.load();

            MoreInfoController controller = loader.getController();
            controller.setPerson(person);

            Scene scene = new Scene(layout, 800, 700);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the window.
     */
    public void show() {
        stage.showAndWait();
    }
}
