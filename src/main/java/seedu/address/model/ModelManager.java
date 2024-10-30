package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.filteredappointment.FilteredAppointment.APPOINTMENT_COMPARATOR;

import java.nio.file.Path;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.patient.Patient;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClinicConnectSystem clinicConnectSystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private final TreeSet<FilteredAppointment> filteredAppts;

    /**
     * Initializes a ModelManager with the given clinicConnectSystem and userPrefs.
     */
    public ModelManager(ReadOnlyClinicConnectSystem clinicConnectSystem, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(clinicConnectSystem, userPrefs);

        logger.fine("Initializing with address book: " + clinicConnectSystem + " and user prefs " + userPrefs);

        this.clinicConnectSystem = new ClinicConnectSystem(clinicConnectSystem);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.clinicConnectSystem.getPatientList());
        this.filteredAppts = new TreeSet<>(APPOINTMENT_COMPARATOR);
    }

    public ModelManager() {
        this(new ClinicConnectSystem(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getClinicConnectSystemFilePath() {
        return userPrefs.getClinicConnectSystemFilePath();
    }

    @Override
    public void setClinicConnectSystemFilePath(Path clinicConnectSystemFilePath) {
        requireNonNull(clinicConnectSystemFilePath);
        userPrefs.setClinicConnectSystemFilePath(clinicConnectSystemFilePath);
    }

    //=========== ClinicConnectSystem ================================================================================

    @Override
    public void setClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem) {
        this.clinicConnectSystem.resetData(clinicConnectSystem);
    }

    @Override
    public ReadOnlyClinicConnectSystem getClinicConnectSystem() {
        return clinicConnectSystem;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return clinicConnectSystem.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        clinicConnectSystem.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        clinicConnectSystem.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        clinicConnectSystem.setPatient(target, editedPatient);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedClinicConnectSystem}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    @Override
    public void setFilteredAppts(TreeSet<FilteredAppointment> filteredAppointments) {
        this.filteredAppts.clear();
        this.filteredAppts.addAll(filteredAppointments);
    }

    @Override
    public TreeSet<FilteredAppointment> getFilteredAppts() {
        return this.filteredAppts;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return clinicConnectSystem.equals(otherModelManager.clinicConnectSystem)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPatients.equals(otherModelManager.filteredPatients);
    }

}
