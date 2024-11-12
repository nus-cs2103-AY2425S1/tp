package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.JobCodeStatistics;

public class JobCodeStatisticsTest {
    private static JobCodeStatistics jobCodeStatistics = new JobCodeStatistics();

    @Test
    public void testBlankStatistics() {
        jobCodeStatistics = new JobCodeStatistics();
        assertEquals(0, jobCodeStatistics.getN());
        assertEquals(0, jobCodeStatistics.getBP());
        assertEquals(0, jobCodeStatistics.getBC());
        assertEquals(0, jobCodeStatistics.getTP());
        assertEquals(0, jobCodeStatistics.getTC());
        assertEquals(0, jobCodeStatistics.getA());
        assertEquals(0, jobCodeStatistics.getR());
    }

    @Test
    public void testIncrementedStatistics() {
        jobCodeStatistics = new JobCodeStatistics();
        jobCodeStatistics.incrementTag("N");
        assertEquals(1, jobCodeStatistics.getN());
        jobCodeStatistics.incrementTag("BP");
        assertEquals(1, jobCodeStatistics.getBP());
        jobCodeStatistics.incrementTag("BC");
        assertEquals(1, jobCodeStatistics.getBC());
        jobCodeStatistics.incrementTag("TP");
        assertEquals(1, jobCodeStatistics.getTP());
        jobCodeStatistics.incrementTag("TC");
        assertEquals(1, jobCodeStatistics.getTC());
        jobCodeStatistics.incrementTag("A");
        assertEquals(1, jobCodeStatistics.getA());
        jobCodeStatistics.incrementTag("R");
    }
}
