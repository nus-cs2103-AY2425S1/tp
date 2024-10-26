package seedu.address.model.person.role.athlete;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

/**
 * Represents the String form of a Sport that an Athlete can participate in shown to the user.
 */
public class SportString {

    public Sport sport;

    public static String getSportString(Sport sport) {
        requireNonNull(sport);
        return switch (sport) {
        case BADMINTON -> "Badminton";
        case BASKETBALL_M -> "Basketball Men";
        case BASKETBALL_W -> "Basketball Women";
        case BOULDERING_M -> "Bouldering Men";
        case BOULDERING_W -> "Bouldering Women";
        case CHESS -> "Chess";
        case CONTACT_BRIDGE -> "Contact Bridge";
        case DODGEBALL -> "Dodgeball";
        case FLOORBALL_M -> "Floorball Men";
        case FLOORBALL_W -> "Floorball Women";
        case HANDBALL_M -> "Handball Men";
        case HANDBALL_W -> "Handball Women";
        case LEAGUE_OF_LEGENDS -> "League of Legends";
        case NETBALL -> "Netball";
        case REVERSI -> "Reversi";
        case SOCCER_M -> "Soccer Men";
        case SOCCER_W -> "Soccer Women";
        case SQUASH -> "Squash";
        case SWIMMING_M -> "Swimming Men";
        case SWIMMING_W -> "Swimming Women";
        case TABLE_TENNIS -> "Table Tennis";
        case TCHOUKBALL -> "Tchoukball";
        case TENNIS -> "Tennis";
        case TOUCH_RUGBY -> "Touch Rugby";
        case TRACK_M -> "Track Men";
        case TRACK_W -> "Track Women";
        case ULTIMATE_FRISBEE -> "Ultimate Frisbee";
        case VALORANT -> "Valorant";
        case VOLLEYBALL_M -> "Volleyball Men";
        case VOLLEYBALL_W -> "Volleyball Women";
        };
    }

    /**
     * Constructs a {@code SportString}.
     *
     * @param sport A valid sport.
     */
    public SportString(String sport) throws ParseException {
        requireNonNull(sport);
        this.sport = ParserUtil.parseSport(sport);
    }

    @Override
    public String toString() {
        return getSportString(sport);
    }

}
