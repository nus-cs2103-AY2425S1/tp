package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getRoleSet;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nickname;
import seedu.address.model.contact.Role;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;

public class SampleDataUtilTest {
    private Contact[] sampleContacts =
        new Contact[] {
            new Contact(new Name("Alexandra Haas"), new TelegramHandle("jason1185"),
                new Email("lynchamanda@example.org"),
                new StudentStatus("undergraduate 6"),
                getRoleSet("President"),
                new Nickname("Matthew Owens")),
            new Contact(new Name("Lori Werner"), new TelegramHandle("roger4548"),
                        new Email("levyjon@example.com"),
                        new StudentStatus("undergraduate 5"),
                        getRoleSet("Vice President", "Admin"),
                        new Nickname("")),
            new Contact(new Name("Kevin Williams"), new TelegramHandle("david6718"),
                        new Email("valenciabenjamin@example.com"),
                        new StudentStatus("undergraduate 3"),
                        getRoleSet("Events (internal)"),
                        new Nickname("")),
            new Contact(new Name("Aaron Gray @ Li Zheng Da"), new TelegramHandle("elizabeth3027"),
                        new Email("millerlisa@example.net"),
                        new StudentStatus("undergraduate 2"),
                        getRoleSet("Events (external)", "Events (internal)"),
                        new Nickname("")),
            new Contact(new Name("Alex Ray (Chen Zi Ning)"), new TelegramHandle("mandy6034"),
                        new Email("zmoore@example.com"),
                        new StudentStatus("undergraduate 2"),
                        getRoleSet("Events (internal)", "Admin"),
                        new Nickname("")),
            new Contact(new Name("Adam Ijaaz S/O Ismail"), new TelegramHandle("samantha6699"),
                        new Email("gibsondaniel@example.com"),
                        new StudentStatus("undergraduate 5"),
                        getRoleSet("Marketing", "Admin"),
                        new Nickname("")),
            new Contact(new Name("Avika D/O Thangarajan"), new TelegramHandle("jose7636"),
                        new Email("zsims@example.com"),
                        new StudentStatus("undergraduate 2"),
                        getRoleSet("External Relations", "Events (external)"),
                        new Nickname("Frank Erickson")),
            new Contact(new Name("Keith Noble"), new TelegramHandle("william8711"),
                        new Email("knoxmatthew@example.com"),
                        new StudentStatus("masters"),
                        getRoleSet("Events (external)"),
                        new Nickname("")),
            new Contact(new Name("Jim Tran"), new TelegramHandle("shannon1526"),
                        new Email("phillip83@example.com"),
                        new StudentStatus("masters"),
                        getRoleSet("Events (External)"),
                        new Nickname("")),
            new Contact(new Name("Brendan Wang"), new TelegramHandle("vanessa2181"),
                        new Email("jdavis@example.com"),
                        new StudentStatus("undergraduate 6"),
                        getRoleSet("Events (external)"),
                        new Nickname("")),
            new Contact(new Name("Vanessa Williams"), new TelegramHandle("candice7805"),
                        new Email("duranjonathan@example.com"),
                        new StudentStatus("undergraduate 4"),
                        getRoleSet("Vice President"),
                        new Nickname("")),
            new Contact(new Name("Brittany Terry"), new TelegramHandle("mitchell7917"),
                        new Email("houstonhunter@example.org"),
                        new StudentStatus("undergraduate 6"),
                        getRoleSet("Events (external)", "Marketing"),
                        new Nickname("Robert Smith")),
            new Contact(new Name("Joseph Frey"), new TelegramHandle("sharon8286"),
                        new Email("ruben29@example.net"),
                        new StudentStatus("masters"),
                        getRoleSet("Vice President", "External Relations"),
                        new Nickname("Cristina Torres")),
            new Contact(new Name("Edward Clark"), new TelegramHandle("tammy7451"),
                        new Email("melissa15@example.com"),
                        new StudentStatus("undergraduate 1"),
                        getRoleSet("Marketing", "Admin"),
                        new Nickname("")),
            new Contact(new Name("Christopher Dixon"), new TelegramHandle("clifford1773"),
                        new Email("jennifer79@example.net"),
                        new StudentStatus("undergraduate 1"),
                        getRoleSet("Events (external)", "Marketing"),
                        new Nickname("")),
            new Contact(new Name("Patty Molina MD"), new TelegramHandle("ricky8381"),
                        new Email("mgriffith@example.com"),
                        new StudentStatus("undergraduate 5"),
                        getRoleSet("Admin", "Vice President"),
                        new Nickname("")),
            new Contact(new Name("Amy Gould"), new TelegramHandle("lance3830"),
                        new Email("riosbrad@example.com"),
                        new StudentStatus("undergraduate 4"),
                        getRoleSet("Vice President", "Admin"),
                        new Nickname("James Martin")),
            new Contact(new Name("Kevin Sharp"), new TelegramHandle("alexis8526"),
                        new Email("jcole@example.org"),
                        new StudentStatus("undergraduate 6"),
                        getRoleSet("Events (external)"),
                        new Nickname("Donna Roth")),
            new Contact(new Name("Jacob Glenn"), new TelegramHandle("mary7800"),
                        new Email("teresajones@example.com"),
                        new StudentStatus("undergraduate 1"),
                        getRoleSet("External Relations", "Vice President"),
                        new Nickname("")),
            new Contact(new Name("Edwin Howell"), new TelegramHandle("michael6567"),
                        new Email("ronald19@example.org"),
                        new StudentStatus("undergraduate 5"),
                        getRoleSet("Events (internal)", "Admin"),
                        new Nickname(""))};

    @Test
    public void getRoleSet_success() {
        Role[] roles = {new Role("Admin"), new Role("Vice President")};

        assertTrue(List.of(roles).containsAll(getRoleSet("Admin", "Vice President")));
        assertTrue(List.of(roles).containsAll(getRoleSet("Vice President", "Admin")));
        assertTrue(List.of(roles).containsAll(getRoleSet("Vice President", "Admin", "Admin")));
    }

    @Test
    public void getSampleContacts_success() {
        assertTrue(List.of(sampleContacts).containsAll(List.of(SampleDataUtil.getSampleContacts())));
    }
}
