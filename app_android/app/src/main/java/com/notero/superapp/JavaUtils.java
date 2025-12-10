public class JavaUtils {
    private static final String TAG = "JavaUtils";

    /**
     * Simple debug logger that can be called from either Java or Kotlin.
     */
    public static void debug(String message) {
        android.util.Log.d(TAG, message);
    }

    /**
     * Example method demonstrating interop with Kotlin.
     * @return the sum of two integers.
     */
    public static int add(int a, int b) {
        return a + b;
    }
}
