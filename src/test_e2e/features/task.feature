Feature: Tasks

  Scenario: Managing Tasks
    When I add a task
    Then I can find the task

  Scenario:
    When I add a task
    And I delete the task
    Then I can not found the task