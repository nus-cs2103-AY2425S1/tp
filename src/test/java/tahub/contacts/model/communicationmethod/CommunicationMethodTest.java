package tahub.contacts.model.communicationmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommunicationMethodTest {

    @Test
    public void testEnumValues() {
        // Ensure the CommunicationMethod contains the correct values
        CommunicationMethod[] methods = CommunicationMethod.values();
        assertEquals(2, methods.length);
        assertEquals(CommunicationMethod.PHONE, methods[0]);
        assertEquals(CommunicationMethod.EMAIL, methods[1]);
    }

    @Test
    public void testValueOf() {
        assertEquals(CommunicationMethod.PHONE, CommunicationMethod.valueOf("PHONE"));
        assertEquals(CommunicationMethod.EMAIL, CommunicationMethod.valueOf("EMAIL"));
    }
}
