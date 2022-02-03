Feature: End to end for the smart light functionalities

  @login+add-energy-history-and-check+weather
Scenario: login+add-energy-history-and-check
  Given I authenticate user with following parameters
  | string2 |
  | string |
  | password |

   Then I add an energy consumption record with the following parameters
    | 1 |
    | test-date2 |

    And I check  the history of energy consumption to have the following parameters
    | 1 |
    | test-date2 |

    Then I check the outside light intensity in the city
    | Berlin |

 @login+get-lightgroups+add+delete
Scenario: login + get-lightgroups + add + delete
  Given I authenticate user with next parameters
    | string |
    | string |
    | password |
    Then I get list of lightgroups
    | dormitor |
    And I add a new group of lights
    | 2233 |
    | sufragerie |
    | false |
    And I check for the  "creation" for the group
    | sufragerie |
    Then I delete a group
    | 58 |
    And I check for the  "deletion" for the group
    | sufragerie |


  @login+add-lightbulb+delete
Scenario: login + add-lightbulb + delete
    Given I authenticate user with next parameters
      | string |
      | string |
      | password |
      Then I add lightbulb
        | 13 |
        | 185 |
        | 10 |
        | 20 |
        | 30 |
        | 1050 |
        | 60 |
        | true |
        | false |
        | true |
      Then I move lightbulb to another group
        | 22 |
        | 40 |
      Then I delete lightbulb
        | 38 |

  @login+create-preset+check-preset+delete-preset+check-preset
  Scenario: login + create-preset + check-preset + delete-preset + check-preset
    Given I authenticate user with following parameters
      | string |
      | string |
      | password |
    Then I add a custom user preset
      | testpreset1 |
      | false |
      | 255   |
      | 255   |
      | 255   |
      | 1     |
    And I check for the  "creation" for the preset
      | testpreset1 |
    Then I delete a custom user preset
      | testpreset1 |
    And I check for the  "deletion" for the preset
      | testpreset1 |

  @login+add-group+create-preset+apply-custompreset+check-applied-preset
  Scenario: login + add-group + create-preset + apply-custompreset + check-applied-preset
    Given I authenticate user with following parameters
      | string |
      | string |
      | password |
    Given I add a group with one light
      | grouptest2 |
    Then I add a custom user preset
      | testpreset2 |
      | false |
      | 255   |
      | 255   |
      | 255   |
      | 1     |
    And I apply the user preset to a group
      | testpreset2 |
      | grouptest2 |
    Then I check if the preset has been correctly applied to the group
      | testpreset2 |
      | grouptest2 |
    @login+add-group+apply-defaultpreset+check-applied-preset
    Scenario: login + add-group + create-preset + apply-defaultpreset + check-applied-preset
      Given I authenticate user with following parameters
        | string |
        | string |
        | password |
      Given I add a group with one light
        | grouptest3 |
      And I apply the default preset to a group
        | Cozy |
        | grouptest3 |
      Then I check if the default preset has been correctly applied to the group
        | Cozy |
        | grouptest3 |