package helperbot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test <code>HelperBotArgumentException</code>.
 */
class HelperBotArgumentExceptionTest {

    @Test
    void constructor_validMessage_messageIsStored() {
        String testMessage = "Test message for invalid argument.";
        HelperBotArgumentException e = new HelperBotArgumentException(testMessage);
        assertEquals(testMessage, e.getMessage());
    }

    @Test
    void toString_validMessage_correctFormat() {
        String testMessage = "Task description cannot be empty.";
        HelperBotArgumentException e = new HelperBotArgumentException(testMessage);
        String expectedString = "Invalid Argument: " + testMessage;
        assertEquals(expectedString, e.toString());
    }

    @Test
    void exception_isThrown_correctMessage() {
        String testMessage = "Argument not found.";
        Exception thrown = assertThrows(HelperBotArgumentException.class, () -> {
            throw new HelperBotArgumentException(testMessage);
        });

        assertEquals(testMessage, thrown.getMessage());
    }
}