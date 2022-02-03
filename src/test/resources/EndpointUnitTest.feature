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

  @create-preset
  Scenario: create-preset
    Then I add a custom user preset
      | customPresetTest1 |
      | false |
      | 255   |
      | 255   |
      | 255   |
      | 1     |

  @create-group-with-light
    Scenario: create-group-with-light
    Given I add a group with one light
      | groupPresetTest |

  @apply-userpreset
  Scenario: apply-userpreset-endpoint
    And I apply the user preset to a group
      | customPresetTest1 |
      | groupPresetTest |

  @check-applied-userpreset
  Scenario: check-userpreset
    Then I check if the preset has been correctly applied to the group
      | customPresetTest1 |
      | groupPresetTest |

  @apply-defaultpreset
  Scenario: apply-defaultpreset-endpoint
    And I apply the default preset to a group
      | Cozy |
      | groupPresetTest |

  @check-applied-defaultpreset
  Scenario: check-default
    Then I check if the default preset has been correctly applied to the group
      | Cozy |
      | groupPresetTest |

  @create-preset
  Scenario: create-preset
    Then I add a custom user preset
      | customPresetTest2 |
      | false |
      | 255   |
      | 255   |
      | 255   |
      | 1     |

  @check-create-preset
  Scenario: check-preset
    And I check for the  "creation" for the preset
      | customPresetTest2 |

  @delete-preset
  Scenario: delete-preset
    Then I delete a custom user preset
      | customPresetTest2 |

  @check-delete-preset
  Scenario: check-preset
    And I check for the  "deletion" for the preset
      | customPresetTest2 |