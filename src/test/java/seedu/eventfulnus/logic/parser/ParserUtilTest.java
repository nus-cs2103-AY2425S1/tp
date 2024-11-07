package seedu.eventfulnus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.eventfulnus.testutil.Assert.assertThrows;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.model.person.role.athlete.Athlete;
import seedu.eventfulnus.model.person.role.athlete.Sport;
import seedu.eventfulnus.model.person.role.committee.Branch;
import seedu.eventfulnus.model.person.role.committee.Position;
import seedu.eventfulnus.model.person.role.volunteer.VolunteerRole;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROLE = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "1234567";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_ROLE_1 = "friend";
    private static final String VALID_ROLE_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseFaculty_validFaculty_returnsFaculty() {
        assertEquals(Faculty.BIZ, ParserUtil.parseFaculty("BIZ"));
        assertEquals(Faculty.CDE, ParserUtil.parseFaculty("CDE"));
        assertEquals(Faculty.COM, ParserUtil.parseFaculty("COM"));
        assertEquals(Faculty.DEN, ParserUtil.parseFaculty("DEN"));
        assertEquals(Faculty.FASS, ParserUtil.parseFaculty("FASS"));
        assertEquals(Faculty.LAW, ParserUtil.parseFaculty("LAW"));
        assertEquals(Faculty.MED, ParserUtil.parseFaculty("MED"));
        assertEquals(Faculty.NUSC, ParserUtil.parseFaculty("NUSC"));
        assertEquals(Faculty.SCI, ParserUtil.parseFaculty("SCI"));
        assertEquals(Faculty.YNC, ParserUtil.parseFaculty("YNC"));
    }

    @Test
    public void parseFaculty_invalidFaculty_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFaculty("INVALID"));
    }

    @Test
    public void parseSport_validSport_returnsSport() {
        assertEquals(Sport.BADMINTON, ParserUtil.parseSport("BADMINTON"));
        assertEquals(Sport.BASKETBALL_M, ParserUtil.parseSport("BASKETBALL MEN"));
        assertEquals(Sport.BASKETBALL_W, ParserUtil.parseSport("BASKETBALL WOMEN"));
        assertEquals(Sport.BOULDERING_M, ParserUtil.parseSport("BOULDERING MEN"));
        assertEquals(Sport.BOULDERING_W, ParserUtil.parseSport("BOULDERING WOMEN"));
        assertEquals(Sport.CHESS, ParserUtil.parseSport("CHESS"));
        assertEquals(Sport.CONTACT_BRIDGE, ParserUtil.parseSport("CONTACT BRIDGE"));
        assertEquals(Sport.DODGEBALL, ParserUtil.parseSport("DODGEBALL"));
        assertEquals(Sport.FLOORBALL_M, ParserUtil.parseSport("FLOORBALL MEN"));
        assertEquals(Sport.FLOORBALL_W, ParserUtil.parseSport("FLOORBALL WOMEN"));
        assertEquals(Sport.HANDBALL_M, ParserUtil.parseSport("HANDBALL MEN"));
        assertEquals(Sport.HANDBALL_W, ParserUtil.parseSport("HANDBALL WOMEN"));
        assertEquals(Sport.LEAGUE_OF_LEGENDS, ParserUtil.parseSport("LEAGUE OF LEGENDS"));
        assertEquals(Sport.NETBALL, ParserUtil.parseSport("NETBALL"));
        assertEquals(Sport.REVERSI, ParserUtil.parseSport("REVERSI"));
        assertEquals(Sport.SOCCER_M, ParserUtil.parseSport("SOCCER MEN"));
        assertEquals(Sport.SOCCER_W, ParserUtil.parseSport("SOCCER WOMEN"));
        assertEquals(Sport.SQUASH, ParserUtil.parseSport("SQUASH"));
        assertEquals(Sport.SWIMMING_M, ParserUtil.parseSport("SWIMMING MEN"));
        assertEquals(Sport.SWIMMING_W, ParserUtil.parseSport("SWIMMING WOMEN"));
        assertEquals(Sport.TABLE_TENNIS, ParserUtil.parseSport("TABLE TENNIS"));
        assertEquals(Sport.TCHOUKBALL, ParserUtil.parseSport("TCHOUKBALL"));
        assertEquals(Sport.TENNIS, ParserUtil.parseSport("TENNIS"));
        assertEquals(Sport.TOUCH_RUGBY, ParserUtil.parseSport("TOUCH RUGBY"));
        assertEquals(Sport.TRACK_M, ParserUtil.parseSport("TRACK MEN"));
        assertEquals(Sport.TRACK_W, ParserUtil.parseSport("TRACK WOMEN"));
        assertEquals(Sport.ULTIMATE_FRISBEE, ParserUtil.parseSport("ULTIMATE FRISBEE"));
        assertEquals(Sport.VALORANT, ParserUtil.parseSport("VALORANT"));
        assertEquals(Sport.VOLLEYBALL_M, ParserUtil.parseSport("VOLLEYBALL MEN"));
        assertEquals(Sport.VOLLEYBALL_W, ParserUtil.parseSport("VOLLEYBALL WOMEN"));
    }

    @Test
    public void parseSport_invalidSport_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSport("INVALID"));
    }

    @Test
    public void parseBranch_validBranch_returnsBranch() {
        assertEquals(Branch.MARKETING, ParserUtil.parseBranch("MARKETING"));
        assertEquals(Branch.PUBLICITY, ParserUtil.parseBranch("PUBLICITY"));
        assertEquals(Branch.SPORTS, ParserUtil.parseBranch("SPORTS"));
    }

    @Test
    public void parseBranch_invalidBranch_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBranch("INVALID"));
    }

    @Test
    public void parsePosition_validPosition_returnsPosition() {
        assertEquals(Position.PROJECT_DIRECTOR, ParserUtil.parsePosition("PROJECT DIRECTOR"));
        assertEquals(Position.VICE_PROJECT_DIRECTOR, ParserUtil.parsePosition("VICE PROJECT DIRECTOR"));
        assertEquals(Position.SPORTS_DIRECTOR, ParserUtil.parsePosition("SPORTS DIRECTOR"));
        assertEquals(Position.VICE_SPORTS_DIRECTOR, ParserUtil.parsePosition("VICE SPORTS DIRECTOR"));
        assertEquals(Position.MEMBER, ParserUtil.parsePosition("MEMBER"));
    }

    @Test
    public void parsePosition_invalidPosition_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePosition("INVALID"));
    }

    @Test
    public void parseVolunteer_validVolunteer_returnsVolunteerRole() {
        assertEquals(VolunteerRole.PHOTOGRAPHER, ParserUtil.parseVolunteer("PHOTOGRAPHER"));
        assertEquals(VolunteerRole.EMCEE, ParserUtil.parseVolunteer("emcee"));
        assertEquals(VolunteerRole.USHER, ParserUtil.parseVolunteer("Usher"));
        assertEquals(VolunteerRole.LOGISTICS, ParserUtil.parseVolunteer("lOGISTICS"));
        assertEquals(VolunteerRole.FIRST_AID, ParserUtil.parseVolunteer("FIRST AID"));
        assertEquals(VolunteerRole.BOOTH_MANNER, ParserUtil.parseVolunteer("BOOTH MANNER"));
    }

    @Test
    public void parseVolunteer_invalidVolunteer_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVolunteer("INVALID"));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() {
        Role expectedRole = new Role(VALID_ROLE_1);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE_1));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE_1 + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE_1);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseRoles_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRoles(null));
    }

    @Test
    public void parseRoles_collectionWithInvalidRoles_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRoles(Arrays.asList(VALID_ROLE_1, INVALID_ROLE)));
    }

    @Test
    public void parseRoles_emptyCollection_returnsEmptySet() {
        assertTrue(ParserUtil.parseRoles(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseRoles_collectionWithValidRoles_returnsRoleSet() {
        Set<Role> actualRoleSet = ParserUtil.parseRoles(Arrays.asList(VALID_ROLE_1, VALID_ROLE_2));
        Set<Role> expectedRoleSet = new HashSet<Role>(Arrays.asList(new Role(VALID_ROLE_1), new Role(VALID_ROLE_2)));

        assertEquals(expectedRoleSet, actualRoleSet);
    }

    @Test
    public void parseRole_noHyphen_success() {
        assertEquals(new Athlete(Faculty.COM, List.of(Sport.VOLLEYBALL_W)),
                ParserUtil.parseRole("Athlete-COM-Volleyball Women"));
    }
}
