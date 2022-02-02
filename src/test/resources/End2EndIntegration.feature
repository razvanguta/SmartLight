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
    | pivnita |
    | false |
    And I check for the  "creation" for the group
    | pivnita |
    Then I delete a group
    | 32 |
    And I check for the  "deletion" for the group
    | pivnita |


  @login+add-lightbulb+delete
Scenario: login + add-lightbulb + delete
    Given I authenticate user with next parameters
      | string |
      | string |
      | password |
      Then I add lightbulb
        | 13 |
      Then I move lightbulb to another group
        | 22 |
        | 40 |
      Then I delete lightbulb
        | 38 |
    #todo
    #@login+create-preset+check-preset+delete-preset+check-preset
    #@login+add-group+create-preset+apply-custompreset-and-check
    #@login+add-group+apply-defaultpreset-and-check