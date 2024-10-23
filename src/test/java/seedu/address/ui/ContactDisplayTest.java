// package seedu.address.ui;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.util.WaitForAsyncUtils;

// import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.scene.layout.FlowPane;
// import javafx.stage.Stage;

// public class ContactDisplayTest extends ApplicationTest {

//     private ContactDisplay contactDisplay;
//     private Label nameLabel;
//     private Label phoneLabel;
//     private Label emailLabel;
//     private Label categoryLabel;
//     private Label addressLabel;
//     private FlowPane tags;

//     @Override
//     public void start(Stage stage) {
//         System.setProperty("java.awt.headless", "true");
//         contactDisplay = new ContactDisplay();
//         Scene scene = new Scene(contactDisplay.getRoot());
//         stage.setScene(scene);
//         stage.show();
//     }

//     @BeforeEach
//     public void setUp() {
//         nameLabel = (Label) contactDisplay.getRoot().lookup("#nameLabel");
//         phoneLabel = (Label) contactDisplay.getRoot().lookup("#phoneLabel");
//         emailLabel = (Label) contactDisplay.getRoot().lookup("#emailLabel");
//         categoryLabel = (Label) contactDisplay.getRoot().lookup("#categoryLabel");
//         addressLabel = (Label) contactDisplay.getRoot().lookup("#addressLabel");
//         tags = (FlowPane) contactDisplay.getRoot().lookup("#tags");
//         WaitForAsyncUtils.waitForFxEvents();
//     }

//     // @Test
//     // public void initialization_setsUpUiComponents() {
//     //     assertNotNull(nameLabel);
//     //     assertNotNull(phoneLabel);
//     //     assertNotNull(emailLabel);
//     //     assertNotNull(categoryLabel);
//     //     assertNotNull(addressLabel);
//     //     assertNotNull(tags);
//     // }

// }
