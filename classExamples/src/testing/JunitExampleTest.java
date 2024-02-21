package testing;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class JunitExampleTest {

    @BeforeAll
    static void createDatabase() {
        // Create the test database
    }

    @BeforeEach
    void addTestRecord() {
        // insert test record
    }

    @AfterEach
    void deleteTestRecord() {
        // delete test record
    }

    @AfterAll
    static void deleteDatabase() {
        // delete the test database
    }

    @Test
    void examples() {
        assertEquals(2, 1 + 1);
        assertNotEquals(true, !true);
        assertTrue(true);
        assertNull(null);
        assertDoesNotThrow(() -> true);
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    void append() {
        var example = new JunitExample("aaa");
        assertEquals("aaa", example.toString());
        example.append("bbb");
        assertEquals("aaabbb", example.toString());
    }

    @Test
    void appendEmpty() {
        var example = new JunitExample("");
        example.append("");
        assertEquals("", example.toString());
    }

    @Test
    @Disabled
    @DisplayName("AlwaysFail")
    void failure() {
        fail("missing something important");
    }
}