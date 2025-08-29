package helperbot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test <code>HelperBotCommandException</code>.
 */
class HelperBotCommandExceptionTest {

    @Test
    void constructor_validMessage_messageIsStored() {
        String testMessage = "blah is not a valid command.";
        HelperBotCommandException e = new HelperBotCommandException(testMessage);
        assertEquals(testMessage, e.getMessage());
    }

    @Test
    void toString_validMessage_correctFormat() {
        String testMessage = "Command cannot be empty.";
        HelperBotCommandException e = new HelperBotCommandException(testMessage);
        String expectedString = "Invalid Command: " + testMessage;
        assertEquals(expectedString, e.toString());
    }

    @Test
    void exception_isThrown_correctMessage() {
        String testMessage = "Command not found.";
        Exception thrown = assertThrows(HelperBotCommandException.class, () -> {
            throw new HelperBotCommandException(testMessage);
        });

        assertEquals(testMessage, thrown.getMessage());
    }
}