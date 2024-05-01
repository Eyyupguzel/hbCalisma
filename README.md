# Genel Bilgi.
Bu projede, api için restassured ui test otomasyonları için selenium cucumber Java dilinde kullanılmıştır.
Proje temel yapısı, belirli bir yazılım veya uygulamanın test süreçlerini otomatikleştirmek için kullanılabilir.

## Gereksinimler

- Java JDK (sürüm 20.0.1 veya daha yüksek)
- Diğer gereksinimler için bkz. : pom.xml

## Proje Yapısı

    automation_structure/
    ├── config/
    │   └── config.ini
    ├── helpers/
    │   ├── api/
    │   ├── ui/
    │   ├── base/
    │   └── project/
    ├
    ├── options/
    ├── stepdefs/
    │   ├── api/
    │   └── ui/
    ├── utils/
    ├── feature/
    │   ├── api/
    │   └── ui/
    ├── properties/
    └── testResults/

- `src/test/java/config`: Bu klasör, driver,extension ve config.ini gibi proje sabiti konularını içerir.
- `src/test/java/helpers`: Bu klasör, stepdefs içerisinde kullanımı kolaylaştıran yardımcı methodları içerir.
- `src/test/java/options`: Bu klasör, testlerin çalıştırılmasını gerçekleştiren runner dosyalarını içerir.
- `src/test/java/stepdefs`: Bu klasör, feature cümlelerinin yazılması ile çalışacak methodları içerir.
- `src/test/java/utils`: Bu klasör, işlerin daha hızlı yapılması için yardımcı methodlar içerir.
- `src/test/resources/features`: Bu klasör, test cümlelerini içerir.
- `src/test/resources/propeties`: Bu klasör, log ve rapor yapılandırma dosyalarını içerir.
- `src/test/resources/testResults`: Bu klasör, test sonuçlarının çıktılarını içerir.
- `pom.xml`: Bu dosya, Maven proje yapılandırma dosyasıdır.

## Test Çalıştırma

1. Test senaryolarını `src/test/resources/feature` klasöründe bulabilirsiniz.
2. Bir test senaryosunu çalıştırmak için, ilgili feature dosyasını açın ve run butonuna tıklayın.
3. "Hepsiburadaa thumbsUp - Chrome" senaryo websitesine gidip random bir ürünün yorumunu begenmeyi bunu chrome ile yapar.
5. "Like Random Product - Chrome" senaryo websitesine gidip random bir ürünü begenir bunu chrome ile yapar.
6. "Compare Price - Chrome" senaryo websitesine gidip random bir ürünün fiyatını sepetteki fiyatıyla karşılaştırmayı bunu chrome ile yapar.
7. "post and get with id" senaryosu post ile bir kullanıcı oluşturur ve responsedan o kullanıcı idsini alıp get ile bilgilerini getirir.


NOT : Locatorları  utils altındaki webUtils/elements/hbLive.json içersinden almaktadır.
Allure report  aksiyon almasını isterseniz Runnerdan senaryoları tetiklemeniz gerekmektedir.
'allure serve -h localhost' komutu ile allure report'u görüntüleyebilirsiniz.

## Katkılar

1. eyyupguzel47@gmail.com
