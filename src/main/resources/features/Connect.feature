@Functional
Feature: connect to database

	@DBTransact
  Scenario: Connect and select record from database
    Given I connect to the database
    When I query using select statement
    Then I should be able to view the results
    
  @DBTransact
  Scenario: Connect and insert record from database
    Given I connect to the database
    When I query using insert statement
    When I query using select statement
    Then I should be able to view the results
	
	@DBTransact
  Scenario: Connect and update record in database
    Given I connect to the database
    When I query using update statement
    When I query using select statement
    Then I should be able to view the results
	
	@DBTransact
  Scenario: Connect and delete record from database
    Given I connect to the database
    When I query using delete statement
    When I query using select statement
    Then I should be able to view the results 
    
   @Validation  
   Scenario Outline: Validate Vehicle vin No
   Given validate valid and invalid vehicle <vinNo>
Examples:
|vinNo|
|"1FA-CP45E-X-LF192944"|
|"1B4YEM9P4KP186543"|

