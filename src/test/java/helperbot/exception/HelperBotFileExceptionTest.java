package helperbot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test the <code>HelperBotFileException</code>
 */
class HelperBotFileExceptionTest {

    @Test
    void constructor_validMessage_messageIsStored() {
        String testMessage = "Test message for invalid file argument.";
        HelperBotFileException e = new HelperBotFileException(testMessage);
        assertEquals(testMessage, e.getMessage());
    }

    @Test
    void toString_validMessage_correctFormat() {
        String testMessage = "Task description cannot be empty.";
        HelperBotFileException e = new HelperBotFileException(testMessage);
        String expectedString = "File Corrupted: " + testMessage;
        assertEquals(expectedString, e.toString());
    }

    @Test
    void exception_isThrown_correctMessage() {
        String testMessage = "File argument not found.";
        Exception thrown = assertThrows(HelperBotFileException.class, () -> {
            throw new HelperBotFileException(testMessage);
        });

        assertEquals(testMessage, thrown.getMessage());
    }
}