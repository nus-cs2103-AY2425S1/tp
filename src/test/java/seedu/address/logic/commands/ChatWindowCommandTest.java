package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import seedu.address.model.Model;
import seedu.address.ui.StatusBarFooter;

public class ChatWindowCommandTest {
    @Test
    public void execute_chatWindowOpened_success() {
        Model model = Mockito.mock(Model.class);
        ChatWindowCommand command = new ChatWindowCommand();

        try (MockedStatic<StatusBarFooter> mockedStatusBarFooter = Mockito.mockStatic(StatusBarFooter.class)) {
            CommandResult result = command.execute(model);

            assertEquals(ChatWindowCommand.SHOWING_CHAT_BOT_MESSAGE, result.getFeedbackToUser());
            mockedStatusBarFooter.verify(StatusBarFooter::handleChatButtonAction);
        }
    }
}

