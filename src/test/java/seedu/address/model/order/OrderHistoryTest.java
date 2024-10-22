package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class OrderHistoryTest {

    @Test
    void toStringMethodTest() {
        Person p = new PersonBuilder().build();
        LocalDateTime time = LocalDateTime.of(2023, 12, 12, 1, 54);
        OrderHistory history = new OrderHistory(new Order("cake"), time, p);
        assertEquals(history.toString(), "Tuesday, December 12, 2023 at 01:54: Ordered cake");
    }
}