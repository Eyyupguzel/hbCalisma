Feature: HepsiBurada- Api - Case

  @test
  Scenario: post and get with id
    * start prepare request body
    * add request body key email value eve.holt@reqres.in
    * add request body key password value testtest
    * finish prepare request body
    When send post request to registerUrl
    Then the status code is 200
    * update url userUrl as newurl with response id
    * send get request to newurl
    * the status code is 200


  Scenario: Parallel Test
    Given paralel run scenario with the following scenarios
      | scenarioList                      |
      | Hepsiburadaa thumbsUp - Chrome    |
      | Like Random Product - Chrome      |
      | post and get with id              |