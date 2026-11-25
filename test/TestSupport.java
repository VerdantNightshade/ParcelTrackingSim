import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;

// A tiny test helper. It counts checks and prints a clear message when one fails.
// A test class calls TestSupport.runAll(MyTest.class).
// Every public static method whose name starts with "test" is one test.
public class TestSupport {

    private static int tests = 0;
    private static int checks = 0;
    private static int failures = 0;
    private static String currentTest = "";

    // Runs all test methods of a class, prints the result and exits with 0 or 1
    public static void runAll(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        Arrays.sort(methods, (a, b) -> a.getName().compareTo(b.getName()));

        for (Method method : methods) {
            if (!method.getName().startsWith("test")) {
                continue;
            }
            tests++;
            currentTest = method.getName();
            try {
                method.invoke(null);
            } catch (Exception e) {
                // An unexpected exception inside a test counts as one failure
                Throwable cause = e.getCause() != null ? e.getCause() : e;
                fail("unexpected exception " + cause);
            }
        }

        System.out.println("RESULT " + testClass.getName() + " tests=" + tests
                + " checks=" + checks + " failed=" + failures);
        System.exit(failures == 0 ? 0 : 1);
    }

    // Records one failed check. The test goes on, so we see all failures
    public static void fail(String message) {
        checks++;
        failures++;
        System.out.println("  FAIL " + currentTest + ": " + message);
    }

    private static void pass() {
        checks++;
    }

    public static void assertTrue(String label, boolean condition) {
        if (condition) {
            pass();
        } else {
            fail(label + " (expected true, got false)");
        }
    }

    public static void assertFalse(String label, boolean condition) {
        if (!condition) {
            pass();
        } else {
            fail(label + " (expected false, got true)");
        }
    }

    public static void assertEquals(String label, Object expected, Object actual) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            pass();
        } else {
            fail(label + " (expected: " + expected + ", actual: " + actual + ")");
        }
    }

    public static void assertEquals(String label, int expected, int actual) {
        assertEquals(label, Integer.valueOf(expected), Integer.valueOf(actual));
    }

    public static void assertEquals(String label, double expected, double actual) {
        if (Math.abs(expected - actual) < 0.0001) {
            pass();
        } else {
            fail(label + " (expected: " + expected + ", actual: " + actual + ")");
        }
    }

    public static void assertNull(String label, Object actual) {
        if (actual == null) {
            pass();
        } else {
            fail(label + " (expected: null, actual: " + actual + ")");
        }
    }

    public static void assertNotNull(String label, Object actual) {
        if (actual != null) {
            pass();
        } else {
            fail(label + " (expected: not null, actual: null)");
        }
    }

    public static void assertContains(String label, String text, String part) {
        if (text != null && text.contains(part)) {
            pass();
        } else {
            fail(label + " (expected text to contain: \"" + part + "\", actual text: \"" + text + "\")");
        }
    }

    // Removes the data files, so a test starts with a clean folder
    public static void deleteDataFiles() {
        new File("manifest.txt").delete();
        new File("status-log.txt").delete();
    }
}
