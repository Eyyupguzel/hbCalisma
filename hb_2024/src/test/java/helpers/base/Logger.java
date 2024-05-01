package helpers.base;

import org.apache.log4j.PropertyConfigurator;

public class Logger {
    private static boolean root = false;
    private static final Object lock = new Object();

    /**
     * Belirtilen sınıfa ait bir `Logger` nesnesi döndüren yöntem.
     * @param cls `Logger` nesnesinin ait olacağı sınıfın sınıf referansı
     * @return Belirtilen sınıfa ait bir `Logger` nesnesi
     */
    public static org.apache.log4j.Logger getLogger(Class<?> cls) {
        synchronized (lock) {
            if (!root) {
                PropertyConfigurator.configure("src/test/resources/properties/log4j.properties");
                root = true;
            }
        }

        return org.apache.log4j.Logger.getLogger(cls.getName());
    }
}
