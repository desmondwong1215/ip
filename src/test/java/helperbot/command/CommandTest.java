package helperbot.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test <code>Command</code>.
 */
public class CommandTest {

    @Test
    public void isExit_nonExitCommand_false() {
        assertFalse(new AddCommand("todo", "").isExit());
        assertFalse(new CheckCommand(new String[]{"check"}).isExit());
        assertFalse(new DeleteCommand(new String[]{"delete"}).isExit());
        assertFalse(new ListCommand().isExit());
        assertFalse(new MarkCommand(new String[]{"mark"}, false).isExit());
    }

    @Test
    public void isExit_exitCommand_true() {
        assertTrue(new ExitCommand().isExit());
    }
}
