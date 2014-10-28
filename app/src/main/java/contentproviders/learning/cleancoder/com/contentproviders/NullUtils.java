package contentproviders.learning.cleancoder.com.contentproviders;

/**
 * Created by Leonid on 29.10.2014.
 */
public class NullUtils {

    public static <T> T getFirstNotNull(T... list) {
        for (T each : list) {
            if (each != null) {
                return each;
            }
        }
        return null;
    }

}
