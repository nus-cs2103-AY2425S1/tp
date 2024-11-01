package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_TIME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ScheduleBuilder;

public class ScheduleTest {

    private static final Schedule AMY_SCHEDULE = new Schedule(
            VALID_SCHEDULE_NAME_AMY, VALID_SCHEDULE_DATE_AMY, VALID_SCHEDULE_TIME_AMY);
    private static final Schedule BOB_SCHEDULE = new Schedule(
            VALID_SCHEDULE_NAME_BOB, VALID_SCHEDULE_DATE_BOB, VALID_SCHEDULE_TIME_BOB);
    private static final Schedule EMPTY_SCHEDULE = new Schedule("", "", "");

    @Test
    public void equals() {
        // same values -> returns true
        Schedule amyCopy = new ScheduleBuilder(AMY_SCHEDULE).build();
        assertTrue(AMY_SCHEDULE.equals(amyCopy));

        // same object -> returns true
        assertTrue(AMY_SCHEDULE.equals(AMY_SCHEDULE));

        // null -> returns false
        assertFalse(AMY_SCHEDULE.equals(null));

        // different type -> returns false
        assertFalse(AMY_SCHEDULE.equals(5));

        // different person -> returns false
        assertFalse(AMY_SCHEDULE.equals(BOB_SCHEDULE));

        // different scheduleName -> returns false
        Schedule editedAmySchedule = new ScheduleBuilder(AMY_SCHEDULE)
                .withScheduleName(VALID_SCHEDULE_NAME_BOB).build();
        assertFalse(AMY_SCHEDULE.equals(editedAmySchedule));

        // different scheduleDate -> returns false
        editedAmySchedule = new ScheduleBuilder(AMY_SCHEDULE)
                .withScheduleDate(VALID_SCHEDULE_DATE_BOB).build();
        assertFalse(AMY_SCHEDULE.equals(editedAmySchedule));

        // different scheduleTime -> returns false
        editedAmySchedule = new ScheduleBuilder(AMY_SCHEDULE)
                .withScheduleTime(VALID_SCHEDULE_TIME_BOB).build();
        assertFalse(AMY_SCHEDULE.equals(editedAmySchedule));
    }

    @Test
    public void toStringMethod() {
        // empty schedule -> empty string
        assertTrue(EMPTY_SCHEDULE.toString().equals(""));

        // empty time -> only schedule name + schedule date
        Schedule editedAmySchedule = new ScheduleBuilder(AMY_SCHEDULE).withScheduleTime("").build();
        String expectedString = AMY_SCHEDULE.scheduleName + ": " + AMY_SCHEDULE.dateString;
        assertTrue(editedAmySchedule.toString().equals(expectedString));

        // expected string
        expectedString = AMY_SCHEDULE.scheduleName + ": " + AMY_SCHEDULE.dateString + " " + AMY_SCHEDULE.timeString;
        assertTrue(AMY_SCHEDULE.toString().equals(expectedString));
    }
}
