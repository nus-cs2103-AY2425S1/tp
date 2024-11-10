package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.tag.Tag;

public class SellerTest {
    @Test
    public void getRole_sellerRole_returnsSeller() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        Email email = new Email("johndoe@example.com");
        Set<Tag> tags = Collections.singleton(new Tag("VIP"));
        Appointment appointment = new Appointment(
                new Date("01-01-24"),
                new From("0800"),
                new To("0900")
        );

        Seller seller = new Seller(name, phone, email, tags, appointment);

        assertEquals(Role.SELLER, seller.getRole(), "getRole should return Role.SELLER for a Seller instance");
    }
}

