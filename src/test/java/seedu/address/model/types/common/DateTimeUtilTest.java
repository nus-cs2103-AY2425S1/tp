package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 * Note: Realtime testing of timeline is not possible without adding dependencies
 * Also, the DateTimeUtil class mainly acts as a facade for Timeline and LocalDateTime,
 * both of which are part of Java and not editable
 */
public class DateTimeUtilTest {

    @Test
    public void getCurrentDateTimeString() {
        String output = DateTimeUtil.getCurrentDateTimeString();
        assertTrue(output.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$"));
    }

    @Test
    public void createTimeLine_oneArgument() {
        AtomicBoolean actionExecuted = new AtomicBoolean(false);
        Runnable action = () -> actionExecuted.set(true);

        Timeline timeline = DateTimeUtil.createTimeline(action);
        assertEquals(timeline.getCycleDuration(), Duration.seconds(1));
        assertEquals(timeline.getDelay(), Duration.seconds(0));
        assertEquals(timeline.getCycleCount(), Timeline.INDEFINITE);

        KeyFrame keyFrame = timeline.getKeyFrames().get(0);
        keyFrame.getOnFinished().handle(new ActionEvent());
        assertTrue(actionExecuted.get());

        timeline.stop();
    }

    @Test
    public void createTimeLine_noDelay() {
        AtomicBoolean actionExecuted = new AtomicBoolean(false);
        Runnable action = () -> actionExecuted.set(true);

        Timeline timeline = DateTimeUtil.createTimeline(action, Duration.seconds(1), Integer.toUnsignedLong(0));
        assertEquals(timeline.getCycleDuration(), Duration.seconds(1));
        assertEquals(timeline.getDelay(), Duration.seconds(0));
        assertEquals(timeline.getCycleCount(), Timeline.INDEFINITE);

        KeyFrame keyFrame = timeline.getKeyFrames().get(0);
        keyFrame.getOnFinished().handle(new ActionEvent());
        assertTrue(actionExecuted.get());

        timeline.stop();
    }

    @Test
    public void createTimeLine_withDelay() {
        AtomicBoolean actionExecuted = new AtomicBoolean(false);
        Runnable action = () -> actionExecuted.set(true);

        Timeline timeline = DateTimeUtil.createTimeline(action, Duration.seconds(1), Integer.toUnsignedLong(1000));
        assertEquals(timeline.getCycleDuration(), Duration.seconds(1));
        assertEquals(timeline.getDelay(), Duration.seconds(1));
        assertEquals(timeline.getCycleCount(), Timeline.INDEFINITE);

        KeyFrame keyFrame = timeline.getKeyFrames().get(0);
        keyFrame.getOnFinished().handle(new ActionEvent());
        assertTrue(actionExecuted.get());

        timeline.stop();
    }
}
