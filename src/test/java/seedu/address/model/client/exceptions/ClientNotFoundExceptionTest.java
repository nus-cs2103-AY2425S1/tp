package seedu.address.model.client.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ClientNotFoundExceptionTest {

    @Test
    public void clientNotFoundException_isThrown() {
        // Test that the ClientNotFoundException can be thrown
        assertThrows(ClientNotFoundException.class, () -> {
            throw new ClientNotFoundException();
        });
    }
}
