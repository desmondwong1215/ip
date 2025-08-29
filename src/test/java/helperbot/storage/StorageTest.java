package helperbot.storage;

import helperbot.exception.HelperBotFileException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Simplified version of <code>Storage</code>.
 */
class StorageStub {

    private final String filePath;

    public StorageStub(String filePath) {
        this.filePath = filePath;
    }

    public void load() throws HelperBotFileException, FileNotFoundException {
        if (!this.filePath.equals("/data/HelperBot.txt")) {
            throw new FileNotFoundException();
        }
    }

    public void write() throws IOException {
        if (!this.filePath.equals("/data/HelperBot.txt")) {
            throw new IOException();
        }
    }
}

/**
 * Test <code>Storage</code>.
 */
public class StorageTest {

    @Test
    public void load_validFilePath_success() {
        try {
            new StorageStub("/data/HelperBot.txt").load();
        } catch (HelperBotFileException | FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void load_invalidFilePath_exceptionThrow() {
        try {
            new StorageStub("./date/HelperBot.txt").load();
        } catch (HelperBotFileException e) {
            fail();
        } catch (FileNotFoundException ignored) {

        }
    }

    @Test
    public void write_validFilePath_success() {
        try {
            new StorageStub("/data/HelperBot.txt").write();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void write_invalidFilePath_exceptionThrow() {
        try {
            new StorageStub("./date/HelperBot.txt").write();
        }  catch (IOException ignored) {

        }
    }
}
