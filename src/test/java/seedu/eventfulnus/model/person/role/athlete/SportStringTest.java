package seedu.eventfulnus.model.person.role.athlete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SportStringTest {
    @Test
    void getSportString_validSport_returnsCorrectString() {
        assertEquals("Badminton", SportString.getSportString(Sport.BADMINTON));
        assertEquals("Basketball Men", SportString.getSportString(Sport.BASKETBALL_M));
        assertEquals("Basketball Women", SportString.getSportString(Sport.BASKETBALL_W));
        assertEquals("Bouldering Men", SportString.getSportString(Sport.BOULDERING_M));
        assertEquals("Bouldering Women", SportString.getSportString(Sport.BOULDERING_W));
        assertEquals("Chess", SportString.getSportString(Sport.CHESS));
        assertEquals("Contact Bridge", SportString.getSportString(Sport.CONTACT_BRIDGE));
        assertEquals("Dodgeball", SportString.getSportString(Sport.DODGEBALL));
        assertEquals("Floorball Men", SportString.getSportString(Sport.FLOORBALL_M));
        assertEquals("Floorball Women", SportString.getSportString(Sport.FLOORBALL_W));
        assertEquals("Handball Men", SportString.getSportString(Sport.HANDBALL_M));
        assertEquals("Handball Women", SportString.getSportString(Sport.HANDBALL_W));
        assertEquals("League of Legends", SportString.getSportString(Sport.LEAGUE_OF_LEGENDS));
        assertEquals("Netball", SportString.getSportString(Sport.NETBALL));
        assertEquals("Reversi", SportString.getSportString(Sport.REVERSI));
        assertEquals("Soccer Men", SportString.getSportString(Sport.SOCCER_M));
        assertEquals("Soccer Women", SportString.getSportString(Sport.SOCCER_W));
        assertEquals("Squash", SportString.getSportString(Sport.SQUASH));
        assertEquals("Swimming Men", SportString.getSportString(Sport.SWIMMING_M));
        assertEquals("Swimming Women", SportString.getSportString(Sport.SWIMMING_W));
        assertEquals("Table Tennis", SportString.getSportString(Sport.TABLE_TENNIS));
        assertEquals("Tchoukball", SportString.getSportString(Sport.TCHOUKBALL));
        assertEquals("Tennis", SportString.getSportString(Sport.TENNIS));
        assertEquals("Touch Rugby", SportString.getSportString(Sport.TOUCH_RUGBY));
        assertEquals("Track Men", SportString.getSportString(Sport.TRACK_M));
        assertEquals("Track Women", SportString.getSportString(Sport.TRACK_W));
        assertEquals("Ultimate Frisbee", SportString.getSportString(Sport.ULTIMATE_FRISBEE));
        assertEquals("Valorant", SportString.getSportString(Sport.VALORANT));
        assertEquals("Volleyball Men", SportString.getSportString(Sport.VOLLEYBALL_M));
        assertEquals("Volleyball Women", SportString.getSportString(Sport.VOLLEYBALL_W));
    }

    @Test
    void getSportString_nullSport_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SportString.getSportString(null));
    }
}
