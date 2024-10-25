package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AgentAssist agentAssist;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given agentAssist and userPrefs.
     */
    public ModelManager(ReadOnlyAgentAssist agentAssist, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(agentAssist, userPrefs);

        logger.fine("Initializing with address book: " + agentAssist + " and user prefs " + userPrefs);

        this.agentAssist = new AgentAssist(agentAssist);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.agentAssist.getPersonList());
    }

    public ModelManager() {
        this(new AgentAssist(), new UserPrefs());
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
    public Path getAgentAssistFilePath() {
        return userPrefs.getAgentAssistFilePath();
    }

    @Override
    public void setAgentAssistFilePath(Path agentAssistFilePath) {
        requireNonNull(agentAssistFilePath);
        userPrefs.setAgentAssistFilePath(agentAssistFilePath);
    }

    //=========== AgentAssist ================================================================================

    @Override
    public void setAgentAssist(ReadOnlyAgentAssist agentAssist) {
        this.agentAssist.resetData(agentAssist);
    }

    @Override
    public ReadOnlyAgentAssist getAgentAssist() {
        return agentAssist;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return agentAssist.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        agentAssist.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        agentAssist.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        agentAssist.setPerson(target, editedPerson);
    }

    //=========== Selected Person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        requireNonNull(person);
        if (!filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (this.getSelectedPerson() == null) {
                return;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                this.setSelectedPerson(change.getFrom() > 0 ? filteredPersons.get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAgentAssist}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        return agentAssist.equals(otherModelManager.agentAssist)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
