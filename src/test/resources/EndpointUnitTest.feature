Feature: Test endpoints

  @login-endpoint
Scenario: login-endpoint
    Given I authenticate user with following parameters
      | string |
      | string |
      | password |

  @energy-add-endpoint
  Scenario: energy-add-endpoint
    Then I add an energy consumption record with the following parameters
      | 1 |
      | test-date2 |

  @energy-get-endpoint
  Scenario: energy-get-endpoint
    And I check  the history of energy consumption to have the following parameters
      | 1 |
      | test-date2 |

  @get-outside-luminosity
  Scenario: get-outside-luminosity
    Then I check the outside light intensity in the city
      | Berlin |

  @get-lightgroups
  Scenario: get-lightgroups
    Then I get list of lightgroups
      | dormitor |

  @apply-userpreset
  Scenario: apply-userpreset-endpoint
    And I apply the user preset to a group
      | string |
      | test2 |

  @check-applied-userpreset
  Scenario: check-userpreset
    Then I check if the preset has been correctly applied to the group
    | string |
    | test2 |

  @apply-defaultpreset
  Scenario: apply-defaultpreset-endpoint
    And I apply the default preset to a group
      | Cozy |
      | test2 |

  @check-applied-defaultpreset
  Scenario: check-default
    Then I check if the default preset has been correctly applied to the group
      | Cozy |
      | test2 |