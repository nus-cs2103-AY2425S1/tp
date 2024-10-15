//package seedu.address.ui;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import javafx.application.Platform;
//import javafx.stage.Stage;
//
//public class HelpWindowTest extends ApplicationTest {
//
//    private HelpWindow helpWindow;
//    private Stage stage;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            stage = new Stage();
//            helpWindow = new HelpWindow(stage);
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testShow_shouldMakeWindowVisible() throws Exception {
//        assertFalse(helpWindow.isShowing());
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            helpWindow.show();
//            assertTrue(helpWindow.isShowing());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testHide_shouldMakeWindowInvisible() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            helpWindow.show();
//            assertTrue(helpWindow.isShowing());
//
//            helpWindow.hide();
//            assertFalse(helpWindow.isShowing());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testFocus_shouldRefocusWindowWhenAlreadyShowing() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            helpWindow.show();
//            assertTrue(helpWindow.isShowing());
//
//            helpWindow.focus();
//            // Assuming no exception should be thrown, and the focus method should work.
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testIsShowing_initiallyInvisible() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            assertFalse(helpWindow.isShowing());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//}
