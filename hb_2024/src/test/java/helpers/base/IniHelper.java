package helpers.base;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class IniHelper {

    public IniHelper() {
    }


    public final String filePath = "src/test/java/config/config.ini";
    public final String sectionName = "Datastore";

    /**
     * INI dosyasını okuyan yöntem.
     * @return Okunan INI dosyasının özellikleri (Properties nesnesi)
     * @throws IOException Giriş/çıkış hatası oluştuğunda fırlatılır
     */
    public Properties readIniFile() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInput = new FileInputStream(filePath);
        properties.load(fileInput);
        fileInput.close();
        return properties;
    }

    /**
     * INI dosyasına yazan yöntem.
     * @param key INI dosyasında ayarın anahtarı
     * @param value INI dosyasında ayarın değeri
     * @throws IOException Giriş/çıkış hatası oluştuğunda fırlatılır
     */
    public void writeIniFile(String key, String value) throws IOException {
        Properties properties = new Properties();
        File iniFile = new File(filePath);
        try (InputStream inputStream = Files.newInputStream(iniFile.toPath())) {
            properties.load(inputStream);
        }
        String sectionKey = String.format("%s.%s", sectionName, key);
        properties.setProperty(sectionKey, value);

        try (OutputStream outputStream = Files.newOutputStream(iniFile.toPath())) {
            properties.store(outputStream, null);
        }
    }

    /**
     * INI dosyasındaki bir değeri getiren yöntem.
     * @param key INI dosyasında aranan ayarın anahtarı
     * @return INI dosyasında belirtilen anahtara karşılık gelen değer
     * @throws IOException Giriş/çıkış hatası oluştuğunda fırlatılır
     */
    public String getIniFileValue(String key) throws IOException {
        Properties properties = new Properties();
        File iniFile = new File(filePath);
        try (InputStream inputStream = Files.newInputStream(iniFile.toPath())) {
            properties.load(inputStream);
        }
        String sectionKey = String.format("%s.%s", sectionName, key);
        return properties.getProperty(sectionKey);
    }

}
