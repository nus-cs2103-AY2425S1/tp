package seedu.eventfulnus.model.person.role.referee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;

public class RefereeTest {
    @Test
    void refereeCreation_withValidFacultyAndSports_success() {
        Faculty faculty = Faculty.CDE;
        List<Sport> sports = List.of(Sport.BASKETBALL_M, Sport.SOCCER_M);
        Referee referee = new Referee(faculty, sports);
        assertEquals("Athlete - CDE - Basketball Men, Soccer Men",
                referee.getRoleName());
    }

    @Test
    void refereeCreation_withEmptySportsList_success() {
        Faculty faculty = Faculty.CDE;
        List<Sport> sports = List.of();
        Referee referee = new Referee(faculty, sports);
        assertEquals("Athlete - CDE - ", referee.getRoleName());
    }

    @Test
    void refereeCreation_withNullFaculty_throwsNullPointerException() {
        List<Sport> sports = List.of(Sport.BASKETBALL_M, Sport.SOCCER_M);
        assertThrows(NullPointerException.class, () -> new Referee(null, sports));
    }

    @Test
    void refereeCreation_withNullSportsList_throwsNullPointerException() {
        Faculty faculty = Faculty.CDE;
        assertThrows(NullPointerException.class, () -> new Referee(faculty, null));
    }
}
