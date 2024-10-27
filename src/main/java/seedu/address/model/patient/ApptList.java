package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import seedu.address.model.ApptSorter;

public class ApptList {
    private final List<Appt> appts;

    public ApptList() {
        this.appts = new ArrayList<>();
    }

    public ApptList(List<Appt> appts) {
        this.appts = appts;
    }

    public void addAppt(Appt appt) {
        appts.add(appt);
        ApptSorter.sortAppointments(appts);
    }

    public void deleteAppt(Appt appt) {
        appts.remove(appt);
    }

    public List<Appt> getAppts() {
        return Collections.unmodifiableList(appts);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appt appt : appts) {
            sb.append(appt.toString()).append("\n");
        }
        return sb.toString();
    }

}
