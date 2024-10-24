package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.testutil.TypicalMembers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Session;
import hallpointer.address.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMemberCommand}.
 */
public class AddMemberCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMember_success() {
        Member validMember = new MemberBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addMember(validMember);

        assertCommandSuccess(new AddMemberCommand(validMember), model,
                String.format(AddMemberCommand.MESSAGE_SUCCESS,
                        validMember.getName().fullName,
                        validMember.getRoom().value,
                        validMember.getTelegram().value),
                expectedModel);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member memberInList = model.getAddressBook().getMemberList().get(0);
        assertCommandFailure(new AddMemberCommand(memberInList), model,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        // Same name but different details
        Member duplicateMember = new MemberBuilder().withName(memberInList.getName().toString())
                .withTelegram(memberInList.getTelegram().toString())
                .withRoom(memberInList.getRoom().toString())
                .withTags("NewTag123")
                .withSessions(memberInList.getSessions().toArray(new Session[0]))
                .build();
        assertCommandFailure(new AddMemberCommand(duplicateMember), model,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

}
