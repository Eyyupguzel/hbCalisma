Feature: HepsiBurada- Ui - Case

  Background: Login HB
    Given go to hbLive page with Chrome
    Then i isDisplay element icon
    When i click element acceptCookies
    * wait 3 seconds
    * i isDisplay element girisYapveyaUyeOl
    * i hover element girisYapveyaUyeOl
    * i click element login
    Then i isDisplay element epostaArea
    When send keys lanelan669@buzblox.com to element epostaArea
    * send keys Testeyyup123 to element passwordArea
    * i click element girisYapButton
    Then i isDisplay element icon

  Scenario: Hepsiburadaa thumbsUp - Chrome
    * i forceClick element search
    * wait 3 seconds
    * force send keys cut to element searchArea
    * i forceClick element araButton
    When select product randomly
    Then verify page load
    When switch last window
    Then i isDisplay element addToCart
    When i scrollToElement element degerlendirmeler
    * i click element degerlendirmeler
    Then verify evaluations
    * driver quit

  Scenario: Like Random Product - Chrome
    * i forceClick element search
    * wait 3 seconds
    * force send keys cut to element searchArea
    * i forceClick element araButton
    When select product randomly
    Then verify page load
    When switch last window
    Then i isDisplay element addToCart
    When click if begen exist
    * wait 3 seconds
    Then click if begen exist
    * driver quit


  Scenario: Compare Price - Chrome
    * i forceClick element search
    * wait 3 seconds
    * force send keys cut to element searchArea
    * i forceClick element araButton
    When select product randomly
    Then verify page load
    When switch last window
    Then i isDisplay element addToCart
    When get and add datastore text element price with keyName detailPrice
    * i click element sepeteEkle
    * i click element sepeteGit
    * wait 1 seconds
    Then verify with datastore key detailPrice, element data sepetFiyat
    * driver quit


  Scenario: Parallel Test
    Given paralel run scenario with the following scenarios
      | scenarioList                      |
      | Hepsiburadaa thumbsUp - Chrome    |
      | Like Random Product - Chrome      |