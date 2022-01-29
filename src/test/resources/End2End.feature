Feature: End to end for the smart light functionalities

  @login+add-energy-history-and-check+weather
Scenario: login+add-energy-history-and-check
  Given I authenticate user with following parameters
  | user@gmail.com |
  | razvan |
  | password |

   Then I add an energy consumption record with the following parameters
    | 1 |
    | test-date2 |

    And I check  the history of energy consumption to have the following parameters
    | 1 |
    | test-date2 |

    Then I check the outside light intensity in the city
    | Berlin |
