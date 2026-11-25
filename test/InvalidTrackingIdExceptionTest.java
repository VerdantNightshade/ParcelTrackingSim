public class InvalidTrackingIdExceptionTest {

    public static void testGetMessage() {
        InvalidTrackingIdException e = new InvalidTrackingIdException("bad id");
        TestSupport.assertEquals("message", "bad id", e.getMessage());
    }

    // A checked exception extends Exception but not RuntimeException
    public static void testIsCheckedException() {
        Object e = new InvalidTrackingIdException("x");
        TestSupport.assertTrue("is an Exception", e instanceof Exception);
        TestSupport.assertFalse("is not a RuntimeException", e instanceof RuntimeException);
    }

    public static void main(String[] args) {
        TestSupport.runAll(InvalidTrackingIdExceptionTest.class);
    }
}
