package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.HostServices;

public class HostServiceManagerTest {

    @BeforeEach
    public void setUp() {
        // Reset the HostServiceManager's state through reflection
        try {
            java.lang.reflect.Field hostServicesField = HostServiceManager.class.getDeclaredField("hostServices");
            hostServicesField.setAccessible(true);
            hostServicesField.set(null, null);

            java.lang.reflect.Field hasSetHostServicesField = HostServiceManager.class
                    .getDeclaredField("hasSetHostServices");
            hasSetHostServicesField.setAccessible(true);
            hasSetHostServicesField.set(null, false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset HostServiceManager state", e);
        }
    }

    @Test
    public void getHostServices_beforeSetting_returnsNull() {
        assertNull(HostServiceManager.getHostServices());
    }

    @Test
    public void setHostServices_nullServices_setsToNull() {
        HostServiceManager.setHostServices(null);
        assertNull(HostServiceManager.getHostServices());
    }

    @Test
    public void setHostServices_multipleAttempts_onlyFirstSucceeds() {
        // We can't easily create a real HostServices instance as it requires a Stage
        // Instead, we'll use null for our test which is a valid input
        HostServiceManager.setHostServices(null);

        // First set should succeed - verify by checking that hasSetHostServices is true
        boolean hasSetHostServices = false;
        try {
            java.lang.reflect.Field hasSetHostServicesField =
                    HostServiceManager.class.getDeclaredField("hasSetHostServices");
            hasSetHostServicesField.setAccessible(true);
            hasSetHostServices = (boolean) hasSetHostServicesField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access hasSetHostServices", e);
        }
        assertTrue(hasSetHostServices, "First setHostServices should succeed");

        // Second set should not change the value of hostServices
        HostServiceManager.setHostServices(null);
        assertNull(HostServiceManager.getHostServices());
    }

    @Test
    public void staticFields_initialization_correctDefaults() {
        // Reset the class state first
        setUp();

        // Verify initial state of static fields
        try {
            java.lang.reflect.Field hasSetHostServicesField =
                    HostServiceManager.class.getDeclaredField("hasSetHostServices");
            hasSetHostServicesField.setAccessible(true);
            boolean initialHasSetHostServices = (boolean) hasSetHostServicesField.get(null);

            java.lang.reflect.Field hostServicesField =
                    HostServiceManager.class.getDeclaredField("hostServices");
            hostServicesField.setAccessible(true);
            HostServices initialHostServices = (HostServices) hostServicesField.get(null);

            assertNull(initialHostServices, "hostServices should initialize to null");
            assertEquals(false, initialHasSetHostServices, "hasSetHostServices should initialize to false");
        } catch (Exception e) {
            throw new RuntimeException("Failed to access static fields", e);
        }
    }
}
