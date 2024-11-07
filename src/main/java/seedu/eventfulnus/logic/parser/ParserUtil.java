package seedu.eventfulnus.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.commons.util.StringUtil;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.PersonContainsKeywordsPredicate;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.model.person.role.athlete.Athlete;
import seedu.eventfulnus.model.person.role.athlete.Sport;
import seedu.eventfulnus.model.person.role.committee.Branch;
import seedu.eventfulnus.model.person.role.committee.CommitteeMember;
import seedu.eventfulnus.model.person.role.committee.FacultySportCommitteeMember;
import seedu.eventfulnus.model.person.role.committee.Position;
import seedu.eventfulnus.model.person.role.sponsor.Sponsor;
import seedu.eventfulnus.model.person.role.volunteer.Volunteer;
import seedu.eventfulnus.model.person.role.volunteer.VolunteerRole;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static FilteredList<Person> filteredPersonList;
    private static FilteredList<Event> filteredEventList;

    public static void setFilteredPersonList(FilteredList<Person> filteredPersonList) {
        ParserUtil.filteredPersonList = filteredPersonList;
    }

    public static void setFilteredEventList(FilteredList<Event> filteredEventsList) {
        ParserUtil.filteredEventList = filteredEventsList;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String faculty} into a {@code Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Faculty parseFaculty(String faculty) throws ParseException {
        return switch(faculty.toUpperCase()) {
        case "BIZ" -> Faculty.BIZ;
        case "CDE" -> Faculty.CDE;
        case "COM" -> Faculty.COM;
        case "DEN" -> Faculty.DEN;
        case "FASS" -> Faculty.FASS;
        case "LAW" -> Faculty.LAW;
        case "MED" -> Faculty.MED;
        case "NUSC" -> Faculty.NUSC;
        case "SCI" -> Faculty.SCI;
        case "YNC" -> Faculty.YNC;
        default -> throw new ParseException("Invalid faculty: " + faculty);
        };
    }

    /**
     * Parses a {@code String sport} into a {@code Sport}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sport} is invalid.
     */
    public static Sport parseSport(String sport) throws ParseException {
        return switch(sport.toUpperCase()) {
        case "BADMINTON" -> Sport.BADMINTON;
        case "BASKETBALL MEN" -> Sport.BASKETBALL_M;
        case "BASKETBALL WOMEN" -> Sport.BASKETBALL_W;
        case "BOULDERING MEN" -> Sport.BOULDERING_M;
        case "BOULDERING WOMEN" -> Sport.BOULDERING_W;
        case "CHESS" -> Sport.CHESS;
        case "CONTACT BRIDGE" -> Sport.CONTACT_BRIDGE;
        case "DODGEBALL" -> Sport.DODGEBALL;
        case "FLOORBALL MEN" -> Sport.FLOORBALL_M;
        case "FLOORBALL WOMEN" -> Sport.FLOORBALL_W;
        case "HANDBALL MEN" -> Sport.HANDBALL_M;
        case "HANDBALL WOMEN" -> Sport.HANDBALL_W;
        case "LEAGUE OF LEGENDS" -> Sport.LEAGUE_OF_LEGENDS;
        case "NETBALL" -> Sport.NETBALL;
        case "REVERSI" -> Sport.REVERSI;
        case "SOCCER MEN" -> Sport.SOCCER_M;
        case "SOCCER WOMEN" -> Sport.SOCCER_W;
        case "SQUASH" -> Sport.SQUASH;
        case "SWIMMING MEN" -> Sport.SWIMMING_M;
        case "SWIMMING WOMEN" -> Sport.SWIMMING_W;
        case "TABLE TENNIS" -> Sport.TABLE_TENNIS;
        case "TCHOUKBALL" -> Sport.TCHOUKBALL;
        case "TENNIS" -> Sport.TENNIS;
        case "TOUCH RUGBY" -> Sport.TOUCH_RUGBY;
        case "TRACK MEN" -> Sport.TRACK_M;
        case "TRACK WOMEN" -> Sport.TRACK_W;
        case "ULTIMATE FRISBEE" -> Sport.ULTIMATE_FRISBEE;
        case "VALORANT" -> Sport.VALORANT;
        case "VOLLEYBALL MEN" -> Sport.VOLLEYBALL_M;
        case "VOLLEYBALL WOMEN" -> Sport.VOLLEYBALL_W;
        default -> throw new ParseException("Invalid sport: " + sport);
        };
    }

    /**
     * Parses a {@code String branch} into a {@code Branch}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code branch} is invalid.
     */
    public static Branch parseBranch(String branch) throws ParseException {
        return switch (branch.toUpperCase()) {
        case "MARKETING" -> Branch.MARKETING;
        case "PUBLICITY" -> Branch.PUBLICITY;
        case "SPORTS" -> Branch.SPORTS;
        default -> throw new ParseException("Unexpected branch: " + branch);
        };
    }

    /**
     * Parses a {@code String position} into a {@code Position}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code position} is invalid.
     */
    public static Position parsePosition(String position) throws ParseException {
        return switch (position.toUpperCase()) {
        case "PROJECT DIRECTOR" -> Position.PROJECT_DIRECTOR;
        case "VICE PROJECT DIRECTOR" -> Position.VICE_PROJECT_DIRECTOR;
        case "SPORTS DIRECTOR" -> Position.SPORTS_DIRECTOR;
        case "VICE SPORTS DIRECTOR" -> Position.VICE_SPORTS_DIRECTOR;
        case "MEMBER" -> Position.MEMBER;
        default -> throw new ParseException("Unexpected position: " + position);
        };
    }

    /**
     * Parses a {@code String volunteer} into a {@code VolunteerRole}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code volunteer} is invalid.
     */
    public static VolunteerRole parseVolunteer(String volunteer) throws ParseException {
        return switch(volunteer.toUpperCase()) {
        case "PHOTOGRAPHER" -> VolunteerRole.PHOTOGRAPHER;
        case "EMCEE" -> VolunteerRole.EMCEE;
        case "USHER" -> VolunteerRole.USHER;
        case "LOGISTICS" -> VolunteerRole.LOGISTICS;
        case "FIRST AID" -> VolunteerRole.FIRST_AID;
        case "BOOTH MANNER" -> VolunteerRole.BOOTH_MANNER;
        default -> throw new ParseException("Unexpected volunteer role: " + volunteer);
        };
    }

    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRoleName(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        String[] tagSplit = trimmedRole.split("-");
        String roleType = tagSplit[0];
        return switch (roleType.toLowerCase()) {
        case "athlete", "referee" -> {
            Faculty faculty = parseFaculty(tagSplit[1]);
            List<Sport> sports = Arrays.stream(tagSplit[2].split(", ")).map(ParserUtil::parseSport).toList();
            yield new Athlete(faculty, sports);
        }
        case "committee" -> {
            Branch branch = parseBranch(tagSplit[1]);
            Position position = parsePosition(tagSplit[2]);
            if (branch.equals(Branch.SPORTS) && !tagSplit[3].isEmpty()) {
                Faculty faculty = parseFaculty(tagSplit[3]);
                yield new FacultySportCommitteeMember(faculty, position);
            }
            yield new CommitteeMember(branch, position);
        }
        case "sponsor" -> new Sponsor(tagSplit[1]);
        case "volunteer" -> {
            VolunteerRole volunteerRole = parseVolunteer(tagSplit[1]);
            yield new Volunteer(volunteerRole);
        }
        default -> new Role(trimmedRole);
        };
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            roleSet.add(parseRole(roleName));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String faculty} into a {@link Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Pair<Faculty, Faculty> parseTeams(List<String> teams) throws ParseException {
        requireNonNull(teams);
        if (teams.size() != 2) {
            throw new ParseException("Exactly 2 teams must be provided.");
        } else if (teams.get(0).equals(teams.get(1))) {
            throw new ParseException("Teams must be different.");
        }
        return new Pair<>(parseFaculty(teams.get(0)), parseFaculty(teams.get(1)));
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String} {@code dateTime} into a {@link LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        try {
            return LocalDateTime.parse(trimmedDateTime, Event.DATE_TIME_PARSE_FORMATTER);
        } catch (Exception e) {
            throw new ParseException(Event.MESSAGE_CONSTRAINTS, e);
        }
    }

    /**
     * Retrieves all {@link Person}s in the {@link AddressBook} of the
     * {@link ModelManager} that match the {@link Sport}and {@link Faculty}
     * provided, and returns them in a defensive copy of a{@link Set}<{@link Person}>.
     */
    public static Set<Person> parseDefaultParticipants(Sport sport, Pair<Faculty, Faculty> teams) {
        PersonContainsKeywordsPredicate isAthleteOfGivenTeams = new PersonContainsKeywordsPredicate(
                List.of("athlete", teams.getKey().toString(), teams.getValue().toString()));
        PersonContainsKeywordsPredicate isAthleteOfGivenSport = new PersonContainsKeywordsPredicate(
                List.of("athlete", sport.toString()));
        Set<Person> defaultParticipants = new HashSet<>(
                filteredPersonList.filtered(isAthleteOfGivenTeams).filtered(isAthleteOfGivenSport));
        return new HashSet<>(defaultParticipants);
    }

    /**
     * Parses a {@code String} {@code participantName} into a {@link Person}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code participantName} is not found.
     */
    public static Person parseParticipant(String participantName) throws ParseException {
        requireNonNull(participantName);
        String trimmedParticipantName = participantName.trim();

        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of(trimmedParticipantName));

        if (filteredPersonList.filtered(predicate).isEmpty()) {
            throw new ParseException("Participant " + trimmedParticipantName + " does not exist in the address book.");
        } else if (filteredPersonList.filtered(predicate).size() > 1) {
            throw new ParseException("Multiple participants found with name " + trimmedParticipantName + ".\n"
                    + "Please specify a more specific participant "
                    + "with their name, phone, email, or roles if necessary.");
        }

        return filteredPersonList.filtered(predicate).get(0);
    }

    /**
     * Parses {@code Collection<String> participants} into a {@code Set<Person>}.
     *
     * @throws ParseException if the given {@code participant} is invalid (i.e. not in the addressbook).
     */
    public static Set<Person> parseParticipants(Collection<String> participantNames) throws ParseException {
        requireNonNull(participantNames);
        final Set<Person> participantSet = new HashSet<>();

        for (String participantName : participantNames) {
            participantSet.add(parseParticipant(participantName));
        }

        return participantSet;
    }
}
