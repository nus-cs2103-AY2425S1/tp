package hallpointer.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.model.Model;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import javafx.collections.ObservableList;

/**
 * A default model stub that has all the methods failing.
 */
public class ModelStub implements Model {
    private final UniqueMemberList members = new UniqueMemberList();
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getHallPointerFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHallPointerFilePath(Path hallPointerFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public ReadOnlyHallPointer getHallPointer() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHallPointer(ReadOnlyHallPointer hallPointer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasMember(Member member) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteMember(Member target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMember(Member target, Member updatedMember) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return members.asUnmodifiableObservableList();
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public int countMemberOccurrences(Member member) {
        throw new AssertionError("This method should not be called.");
    }
}

