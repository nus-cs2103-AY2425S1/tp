package keycontacts.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class MakeupLessonTest {
    private String datestr = "08-01-2025";
    private String startTimeStr = "14:00";
    private String endTimeStr = "15:00";
    private Date date = new Date(datestr);

    private Time startTime = new Time(startTimeStr);
    private Time endTime = new Time(endTimeStr);
    private MakeupLesson makeupLesson = new MakeupLesson(date, startTime, endTime);

    @Test
    public void getLessonTest() {
        assertEquals(date, makeupLesson.getLessonDate());
        assertEquals(startTime, makeupLesson.getStartTime());
        assertEquals(endTime, makeupLesson.getEndTime());
    }

    @Test
    public void equals() {
        MakeupLesson sameMakeupLesson = new MakeupLesson(new Date("08-01-2025"), new Time("14:00"), new Time("15:00"));
        assertEquals(sameMakeupLesson, makeupLesson);
        assertNotEquals(new MakeupLesson(date, startTime, new Time("16:00")), makeupLesson);
        assertNotEquals(new MakeupLesson(date, new Time("03:00"), endTime), makeupLesson);
        assertNotEquals(new MakeupLesson(new Date("08-02-2025"), startTime, endTime), makeupLesson);
    }

    @Test
    public void getMakeupLessonSetTest() {
        String datestr2 = "09-10-2035";
        String startTimeStr2 = "19:00";
        String endTimeStr2 = "20:00";
        Time startTime2 = new Time(startTimeStr2);
        Time endTime2 = new Time(endTimeStr2);
        MakeupLesson makeupLesson2 = new MakeupLesson(new Date(datestr2), startTime2, endTime2);
        Set<MakeupLesson> makeupLessons = new HashSet<>();
        makeupLessons.add(makeupLesson);
        makeupLessons.add(makeupLesson2);
        String[] array1 = {datestr, datestr2};
        String[] array2 = {startTimeStr, startTimeStr2};
        String[] array3 = {endTimeStr, endTimeStr2};

        assertTrue(makeupLessons.equals(MakeupLesson.getMakeupLessonSet(array1, array2, array3)));
    }
}