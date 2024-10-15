//package seedu.address.ui;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
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
//import seedu.address.commons.core.GuiSettings;
//import seedu.address.logic.Logic;
//
//public class MainWindowTest extends ApplicationTest {
//
//    private MainWindow mainWindow;
//    private HelpWindow helpWindow;
//    private Stage primaryStage;
//    private GuiSettings guiSettings;
//
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        // Create or mock GuiSettings object
//        guiSettings = new GuiSettings(800, 600, 100, 100);
//
//        Platform.runLater(() -> {
//            primaryStage = new Stage();
//            helpWindow = mock(HelpWindow.class);
//            Logic logic = mockLogic();
//
//            // Mock or define the behavior of logic to return the GuiSettings
//            when(logic.getGuiSettings()).thenReturn(guiSettings);
//
//            mainWindow = new MainWindow(primaryStage, logic);
//            mainWindow.setHelpWindow(helpWindow);
//
//            latch.countDown();
//        });
//
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//
//    @Test
//    public void testHandleHelp_helpWindowNotShowing_shouldShow() throws Exception {
//        when(helpWindow.isShowing()).thenReturn(false);
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            mainWindow.handleHelp();
//            verify(helpWindow, times(1)).show();
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testHandleHelp_helpWindowShowing_shouldFocus() throws Exception {
//        when(helpWindow.isShowing()).thenReturn(true);
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            mainWindow.handleHelp();
//            verify(helpWindow, times(1)).focus();
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testHandleExit_shouldCloseWindows() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            mainWindow.handleExit();
//            verify(helpWindow, times(1)).hide();
//            assertFalse(primaryStage.isShowing());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testGetPrimaryStage_shouldReturnCorrectStage() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            assertEquals(primaryStage, mainWindow.getPrimaryStage());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testGetHelpWindow_shouldReturnCorrectHelpWindow() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            assertEquals(helpWindow, mainWindow.getHelpWindow());
//            latch.countDown();
//        });
//        latch.await(3, TimeUnit.SECONDS);
//    }
//
//    // Helper method to mock the Logic class
//    private Logic mockLogic() {
//        Logic logic = mock(Logic.class);
//        // Set up mock behavior as needed
//        return logic;
//    }
//}
