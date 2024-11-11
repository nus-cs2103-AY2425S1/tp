package seedu.eventfulnus.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.model.util.SampleDataUtil.getRoleSet;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.UserPrefs;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.testutil.EventBuilder;
import seedu.eventfulnus.testutil.PersonBuilder;

public class SampleDataUtilTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    void getSamplePersons_returnsNonEmptyArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertTrue(samplePersons.length > 0);
    }

    @Test
    void getSampleAddressBook_containsAllSamplePersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        AddressBook tempAddressBook = new AddressBook();
        tempAddressBook.setPersons(Arrays.asList(samplePersons));
        model.setAddressBook(tempAddressBook);

        Person expectedPersonAlice = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
                .withEmail("alice@example.com").withRoles("ath - com - vbw, fbw").build();
        Person expectedPersonBenson = new PersonBuilder().withName("Benson Meier").withPhone("98765432")
                .withEmail("johnd@example.com").withRoles("vol - mc", "ath - den - smm").build();
        Person expectedPersonCarl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
                .withEmail("heinz@example.com").withRoles("ref - med - tcb, sqh").build();
        Person expectedPersonDaniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                .withEmail("cornelia@example.com").withRoles("spon - Oatside").build();
        Person expectedPersonElle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
                .withEmail("werner@example.com").withRoles("comm - mkt - pd").build();
        Person expectedPersonFiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                .withEmail("lydia@example.com").withRoles("comm - pub - pd").build();
        Person expectedPersonGeorge = new PersonBuilder().withName("George Best").withPhone("9482442")
                .withEmail("anna@example.com").withRoles("comm - spo - vsd - biz").build();

        assertTrue(Stream.of(expectedPersonAlice, expectedPersonBenson, expectedPersonCarl,
                expectedPersonDaniel, expectedPersonElle, expectedPersonFiona, expectedPersonGeorge)
                .allMatch(model::hasPerson));
    }

    @Test
    void getSampleEvents_returnsNonEmptyArray() {
        Event[] sampleEvents = SampleDataUtil.getSampleEvents();
        assertTrue(sampleEvents.length > 0);
    }

    @Test
    void getSampleAddressBook_containsAllSampleEvents() {
        Event[] sampleEvents = SampleDataUtil.getSampleEvents();
        AddressBook tempAddressBook = new AddressBook();
        tempAddressBook.setEvents(Arrays.asList(sampleEvents));
        model.setAddressBook(tempAddressBook);

        Event expectedEventChess = new EventBuilder().withSport("che").withTeams("com", "nusc")
                .withVenue("UT AUD2").withDateTime("2024 09 20 1000")
                .withParticipants("Alice", "Elle").build();
        Event expectedEventSquash = new EventBuilder().withSport("sqh").withTeams("fass", "ync")
                .withVenue("Arena").withDateTime("2024 09 20 1400")
                .withParticipants("Carl").build();
        Event expectedEventSwimming = new EventBuilder().withSport("smm").withTeams("med", "den")
                .withVenue("Pool").withDateTime("2024 09 20 1600")
                .withParticipants("Benson", "Fiona").build();
        Event expectedEventTableTennis = new EventBuilder().withSport("tbt").withTeams("law", "biz")
                .withVenue("Court").withDateTime("2024 09 21 1000")
                .withParticipants("Daniel", "George").build();

        assertTrue(Stream.of(expectedEventChess, expectedEventSquash, expectedEventSwimming, expectedEventTableTennis)
                .allMatch(model::hasEvent));
    }

    @Test
    void getRoleSet_withValidRoles_returnsCorrectSet() {
        Set<Role> roles = getRoleSet("Athlete - COM - Volleyball Women, Tennis", "Volunteer - Photographer");
        assertEquals(2, roles.size());
        assertTrue(roles.contains(ParserUtil.parseRole("Athlete - COM - Volleyball Women, Tennis")));
        assertTrue(roles.contains(ParserUtil.parseRole("Volunteer - Photographer")));
    }
}
