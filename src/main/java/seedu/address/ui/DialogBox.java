package seedu.address.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle rectangle = new Rectangle(0, 0, 150, 150);
        dialog.setText(text);
        displayPicture.setImage(img);
        displayPicture.setClip(rectangle);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Turns the dialog box into an error box message.
     */
    private void makeErrorMessage() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        getChildren().setAll(tmp);
        dialog.getStyleClass().add("error-label");
    }

    /**
     * Returns a user dialog box message.
     *
     * @param text Command text inputted by user.
     * @param img Image to be used by the dialog box.
     * @param isError Whether the command resulted in an error.
     */
    public static DialogBox getUserDialog(String text, Image img, boolean isError) {
        var db = new DialogBox(text, img);
        if (isError) {
            db.makeErrorMessage();
        }
        return db;
    }

    /**
     * Returns a chatbot dialog box message.
     *
     * @param text Command text inputted by user.
     * @param img Image to be used by the dialog box.
     */
    public static DialogBox getChatBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();

        return db;
    }
}
