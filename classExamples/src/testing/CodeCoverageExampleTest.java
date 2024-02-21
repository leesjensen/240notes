package testing;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CodeCoverageExampleTest {

    @Test
    void branchTest() {
        assertTrue(CodeCoverageExample.branch(Boolean.TRUE));
//        assertFalse(CodeCoverageExample.branch(Boolean.FALSE));
//        assertThrows(RuntimeException.class, () -> CodeCoverageExample.branch(null));
    }
}