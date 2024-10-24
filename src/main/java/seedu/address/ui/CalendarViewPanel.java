package seedu.address.ui;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of appointments.
 */
public class CalendarViewPanel extends UiPart<Region> {
    private static final String FXML = "CalendarViewPanel.fxml";

    @FXML
    private final CalendarView calendarView;

    private final Calendar calendar;

    /**
     * Creates a {code CalendarViewPanel} with the given {@code ObservableList}.
     */
    public CalendarViewPanel(ObservableList<Appointment> appointmentList) {
        super(FXML);
        calendarView = new CalendarView();
        calendar = new Calendar("Appointments");
        setUpCalendarView(appointmentList);
        bindAppointmentListToCalendar(appointmentList);
    }

    private void setUpCalendarView(ObservableList<Appointment> appointmentList) {
        calendarView.setShowToolBar(false);
        calendarView.setEntryFactory(param -> null);
        calendarView.setEntryDetailsCallback(param -> null);
        calendarView.setEntryContextMenuCallback(param -> null);
        calendarView.setContextMenuCallback(param -> null);

        calendar.setStyle(Calendar.Style.STYLE2);
        calendar.setReadOnly(true);

        for (Appointment appointment : appointmentList) {
            addAppointmentToCalendar(appointment);
        }

        CalendarSource source = new CalendarSource("My Calendars");
        source.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(source);
    }

    private void bindAppointmentListToCalendar(ObservableList<Appointment> appointmentList) {
        appointmentList.addListener((ListChangeListener<? super Appointment>) change -> {
            while (change.next()) {
                if (change.wasReplaced()) {
                    change.getRemoved().forEach(this::removeAppointmentFromCalendar);
                    change.getAddedSubList().forEach(this::addAppointmentToCalendar);
                } else if (change.wasAdded()) {
                    calendar.clear();
                    appointmentList.forEach(this::addAppointmentToCalendar);
                } else if (change.wasRemoved()) {
                    change.getRemoved().forEach(this::removeAppointmentFromCalendar);
                }
            }
        });
    }

    private void addAppointmentToCalendar(Appointment appointment) {
        Entry<String> entry = new Entry<>(appointment.name().fullName);
        entry.setInterval(appointment.date(), appointment.startTime(),
                appointment.date(), appointment.endTime());
        calendar.addEntry(entry);
    }

    private void removeAppointmentFromCalendar(Appointment appointment) {
        calendar.findEntries(appointment.name().fullName).stream()
                .filter(entry -> entry.getStartDate().equals(appointment.date())
                        && entry.getStartTime().equals(appointment.startTime()))
                .forEach(calendar::removeEntry);
    }

    protected CalendarView getCalendarView() {
        return calendarView;
    }
}
