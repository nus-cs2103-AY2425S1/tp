package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.member.Member;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.ModelStub;

public class AddMemberCommandTest {

    @Test
    public void constructor_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(null));
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemberAdded modelStub = new ModelStubAcceptingMemberAdded();
        Member validMember = new MemberBuilder().build();

        CommandResult commandResult = new AddMemberCommand(validMember).execute(modelStub);

        assertEquals(
                String.format(
                        AddMemberCommand.MESSAGE_SUCCESS,
                        validMember.getName().fullName,
                        validMember.getRoom().value,
                        validMember.getTelegram().value
                ),
                commandResult.getFeedbackToUser()
        );
        assertEquals(Arrays.asList(validMember), modelStub.membersAdded);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        ModelStub modelStub = new ModelStubWithMember(validMember);

        assertThrows(CommandException.class,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER, () -> addMemberCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Member alice = new MemberBuilder().withName("Alice").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        AddMemberCommand addBobCommand = new AddMemberCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMemberCommand addAliceCommandCopy = new AddMemberCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different member -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddMemberCommand addMemberCommand = new AddMemberCommand(ALICE);
        String expected = AddMemberCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addMemberCommand.toString());
    }

    /**
     * A Model stub that contains a single member.
     */
    private class ModelStubWithMember extends ModelStub {
        private final Member member;

        ModelStubWithMember(Member member) {
            requireNonNull(member);
            this.member = member;
        }

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return this.member.isSameMember(member);
        }
    }

    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingMemberAdded extends ModelStub {
        final ArrayList<Member> membersAdded = new ArrayList<>();

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return membersAdded.stream().anyMatch(member::isSameMember);
        }

        @Override
        public void addMember(Member member) {
            requireNonNull(member);
            membersAdded.add(member);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
