package guitests;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.testfx.api.FxRobot;

import guitests.guihandles.exceptions.StageNotFoundException;
import javafx.stage.Stage;

/**
 * Provides utility methods to assist in GUI testing, extending the {@code FxRobot} functionality from the TestFX
 * framework. This class also supports handling headless mode and waiting for specific GUI events.
 */
public class GuiRobot extends FxRobot {

    private static final int PAUSE_FOR_HUMAN_DELAY_MILLISECONDS = 10;
    private static final int DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS = 5000;

    private static final String PROPERTY_TESTFX_HEADLESS = "testfx.headless";

    private final boolean isHeadlessMode;

    /**
     * Constructs a {@code GuiRobot} instance and determines whether the tests are running in headless mode based on the
     * system property {@code testfx.headless}.
     */
    public GuiRobot() {
        String headlessPropertyValue = System.getProperty(PROPERTY_TESTFX_HEADLESS);
        isHeadlessMode = headlessPropertyValue != null && headlessPropertyValue.equals("true");
    }

    /**
     * Pauses the execution briefly to allow manual observation of the GUI, only if not running in headless mode.
     */
    public void pauseForHuman() {
        if (isHeadlessMode) {
            return;
        }

        sleep(PAUSE_FOR_HUMAN_DELAY_MILLISECONDS);
    }

    /**
     * Returns true if tests are run in headless mode.
     */
    public boolean isHeadlessMode() {
        return isHeadlessMode;
    }

    /**
     * Waits for the specified event to occur, using a default timeout.
     *
     * @param event A {@code BooleanSupplier} representing the event condition.
     * @throws EventTimeoutException if the event does not occur within the timeout.
     */
    public void waitForEvent(BooleanSupplier event) {
        waitForEvent(event, DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS);
    }

    /**
     * Waits for the specified event to occur, with a custom timeout.
     *
     * @param event   A {@code BooleanSupplier} representing the event condition.
     * @param timeOut The maximum time to wait for the event, in milliseconds.
     * @throws EventTimeoutException if the event does not occur within the timeout.
     */
    public void waitForEvent(BooleanSupplier event, int timeOut) {
        int timePassed = 0;
        final int retryInterval = 50;

        while (!event.getAsBoolean()) {
            sleep(retryInterval);
            timePassed += retryInterval;

            if (timePassed >= timeOut) {
                throw new EventTimeoutException();
            }
        }

        pauseForHuman();
    }

    /**
     * Checks if a window with the specified title is currently shown.
     *
     * @param stageTitle The title of the window to search for.
     * @return {@code true} if a window with the specified title is shown; {@code false} otherwise.
     */
    public boolean isWindowShown(String stageTitle) {
        return getNumberOfWindowsShown(stageTitle) >= 1;
    }

    /**
     * Returns the number of windows currently shown with the specified title.
     *
     * @param stageTitle The title of the windows to search for.
     * @return The number of windows with the specified title that are currently shown.
     */
    public int getNumberOfWindowsShown(String stageTitle) {
        return (int) listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count();
    }

    /**
     * Retrieves the {@code Stage} with the specified title.
     *
     * @param stageTitle The title of the stage to retrieve.
     * @return The {@code Stage} with the specified title.
     * @throws StageNotFoundException if no stage with the specified title is found.
     */
    public Stage getStage(String stageTitle) {
        Optional<Stage> targetStage = listTargetWindows().stream()
                .filter(Stage.class::isInstance) // checks that the window is of type Stage
                .map(Stage.class::cast)
                .filter(stage -> stage.getTitle().equals(stageTitle))
                .findFirst();

        return targetStage.orElseThrow(StageNotFoundException::new);
    }

    /**
     * Exception thrown when an event does not occur within the expected timeout.
     */
    private class EventTimeoutException extends RuntimeException {
    }
}
