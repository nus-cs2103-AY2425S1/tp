package seedu.address.model.person.role.athlete;

import static java.util.Objects.requireNonNull;

/**
 * Represents the String form of a Sport that an Athlete can participate in shown to the user.
 */
public class SportString {
    public static String getSportString(Sport sport) {
        requireNonNull(sport);
        return switch (sport) {
        case BADMINTON -> "Badminton";
        case BASKETBALL_M -> "Basketball (M)";
        case BASKETBALL_W -> "Basketball (W)";
        case BOULDERING_M -> "Bouldering (M)";
        case BOULDERING_W -> "Bouldering (W)";
        case CHESS -> "Chess";
        case CONTACT_BRIDGE -> "Contact Bridge";
        case DODGEBALL -> "Dodgeball";
        case FLOORBALL_M -> "Floorball (M)";
        case FLOORBALL_W -> "Floorball (W)";
        case HANDBALL_M -> "Handball (M)";
        case HANDBALL_W -> "Handball (W)";
        case LEAGUE_OF_LEGENDS -> "League of Legends";
        case NETBALL -> "Netball";
        case REVERSI -> "Reversi";
        case SOCCER_M -> "Soccer (M)";
        case SOCCER_W -> "Soccer (W)";
        case SQUASH -> "Squash";
        case SWIMMING_M -> "Swimming (M)";
        case SWIMMING_W -> "Swimming (W)";
        case TABLE_TENNIS -> "Table Tennis";
        case TCHOUKBALL -> "Tchoukball";
        case TENNIS -> "Tennis";
        case TOUCH_RUGBY -> "Touch Rugby";
        case TRACK_M -> "Track (M)";
        case TRACK_W -> "Track (W)";
        case ULTIMATE_FRISBEE -> "Ultimate Frisbee";
        case VALORANT -> "Valorant";
        case VOLLEYBALL_M -> "Volleyball (M)";
        case VOLLEYBALL_W -> "Volleyball (W)";
        };
    }
}
