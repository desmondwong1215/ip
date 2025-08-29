package helperbot.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test <code>Command</code>.
 */
public class CommandTest {

    @Test
    public void isExit_nonExitCommand_false() {
        assertFalse(new AddCommand("todo", "").isExit());
        assertFalse(new CheckCommand(new String[]{}).isExit());
        assertFalse(new DeleteCommand(new String[]{}).isExit());
        assertFalse(new ListCommand().isExit());
        assertFalse(new MarkCommand(new String[]{}, false).isExit());
    }

    @Test
    public void isExit_exitCommand_true() {
        assertTrue(new ExitCommand().isExit());
    }
}
