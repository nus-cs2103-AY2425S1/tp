package seedu.address.model;


import java.util.ArrayList;
import java.util.List;

import seedu.address.model.appointment.Appointment;


/**
 * A utility class to manage the list of appointments
 */
public class AppointmentManager {
    private static AppointmentManager instance;
    private List<Appointment> appointments = new ArrayList<>();

    private AppointmentManager() {}

    public static AppointmentManager getInstance() {
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    public void reset() {
        appointments.clear();
    }
}
