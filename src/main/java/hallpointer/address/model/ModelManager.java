package hallpointer.address.model;

import static hallpointer.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.model.member.Member;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the hall pointer data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HallPointer hallPointer;
    private final UserPrefs userPrefs;
    private final FilteredList<Member> filteredMembers;


    /**
     * Initializes a ModelManager with the given hallPointer and userPrefs.
     */
    public ModelManager(ReadOnlyHallPointer hallPointer, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(hallPointer, userPrefs);

        logger.fine("Initializing with hall pointer: " + hallPointer + " and user prefs " + userPrefs);

        this.hallPointer = new HallPointer(hallPointer);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMembers = new FilteredList<>(this.hallPointer.getMemberList());
    }

    public ModelManager() {
        this(new HallPointer(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getHallPointerFilePath() {
        return userPrefs.getHallPointerFilePath();
    }

    @Override
    public void setHallPointerFilePath(Path hallPointerFilePath) {
        requireNonNull(hallPointerFilePath);
        userPrefs.setHallPointerFilePath(hallPointerFilePath);
    }

    //=========== HallPointer ================================================================================

    @Override
    public ReadOnlyHallPointer getHallPointer() {
        return hallPointer;
    }

    @Override
    public void setHallPointer(ReadOnlyHallPointer hallPointer) {
        requireNonNull(hallPointer);
        this.hallPointer.resetData(hallPointer);
    }

    @Override
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return hallPointer.hasMembers(member);
    }

    @Override
    public void deleteMember(Member target) {
        requireNonNull(target);
        hallPointer.removeMember(target);
    }

    @Override
    public void addMember(Member member) {
        requireNonNull(member);
        hallPointer.addMember(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void setMember(Member target, Member updatedMember) {
        requireAllNonNull(target, updatedMember);
        hallPointer.setMember(target, updatedMember);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Member} backed by the internal list of
     * {@code versionedHallPointer}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return filteredMembers;
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        requireNonNull(predicate);
        filteredMembers.setPredicate(predicate);
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
        return hallPointer.equals(otherModelManager.hallPointer)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredMembers.equals(otherModelManager.filteredMembers);
    }

}
