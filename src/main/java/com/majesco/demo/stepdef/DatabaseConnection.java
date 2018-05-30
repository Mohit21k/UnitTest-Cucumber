package com.majesco.demo.stepdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.sql.*;

public class DatabaseConnection {

	Connection con;
	Statement stm;
	String query;
	ResultSet rs;

	@Given("^I connect to the database$")
	public void i_connect_to_the_database() throws Throwable {
		Class.forName("org.hsqldb.jdbcDriver");
		String dbURL = "jdbc:hsqldb:file:C:/InMemDB/DB";
		String username = "SA";
		String password = "";
		con = DriverManager.getConnection(dbURL, username, password);
	}

	@When("^I query using select statement$")
	public void i_query_using_select_statement() throws Throwable {
		query = "SELECT * FROM \"PUBLIC\".\"POLICY\"";
		stm = con.createStatement();
		rs = stm.executeQuery(query);
	}
	
	@When("^I query using update statement$")
	public void i_query_using_update_statement() throws Throwable {
		System.out.println("Update Transaction Started");
		query = "UPDATE \"PUBLIC\".\"POLICY\" SET STATUS='Inactive' WHERE POLICYNUMBER='54601A'";
		stm = con.createStatement();
		rs = stm.executeQuery(query);
	}

	@When("^I query using delete statement$")
	public void i_query_using_delete_statement() throws Throwable {
		System.out.println("Delete Transaction Started");
		query = "DELETE FROM \"PUBLIC\".\"POLICY\" WHERE POLICYNUMBER='54601B'";
		stm = con.createStatement();
		rs = stm.executeQuery(query);
	}
	
	@When("^I query using insert statement$")
	public void i_query_using_insert_statement() throws Throwable {
		System.out.println("Insert Transaction Started");
		query = "INSERT INTO \"PUBLIC\".\"POLICY\"\r\n" + 
				"( \"ID\", \"POLICYNUMBER\", \"PAYMENTOPTION\", \"POLICYEFFECTIVEDATE\", \"POLICYEXPIREDATE\", \"STATUS\" )\r\n" + 
				"VALUES ( 101, '54601A', 'Credit',DATE '2018-04-01' ,DATE '2019-04-01' , 'Active')";		
		stm = con.createStatement();
		rs = stm.executeQuery(query);
	}

	@Then("^I should be able to view the results$")
	public void i_should_be_able_to_view_the_results() throws Throwable {
		try {
			while (rs.next()) {
				String policynumber = rs.getString("POLICYNUMBER");
				Date policyEffectiveDate = rs.getDate("POLICYEFFECTIVEDATE");
				Date policyExpiryDate = rs.getDate("POLICYEXPIREDATE");
				String status = rs.getString("STATUS");

				System.out.println(policynumber + " " + policyEffectiveDate + " " + policyExpiryDate + " " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stm.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	@Given("^validate valid and invalid vehicle \"([^\"]*)\"$")
	public void validate_valid_and_invalid_vechicle_vin_no(String vinNo) throws Throwable {
			// Write code here that turns the phrase above into concrete actions
			int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9 };
			int[] weights = { 8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2 };

			String s=vinNo;
			s = s.replaceAll("-", "");
			s = s.toUpperCase();
			if (s.length() != 17)
				throw new RuntimeException("VIN number must be 17 characters");

			int sum = 0;
			for (int i = 0; i < 17; i++) {
				char c = s.charAt(i);
				int value;
				int weight = weights[i];

				// letter
				if (c >= 'A' && c <= 'Z') {
					value = values[c - 'A'];
					if (value == 0)
						throw new RuntimeException("Illegal character: " + c);
				}

				// number
				else if (c >= '0' && c <= '9')
					value = c - '0';

				// illegal character
				else
					throw new RuntimeException("Illegal character: " + c);

				sum = sum + weight * value;

			}

			// check digit
			sum = sum % 11;
			char check = s.charAt(8);
			if (check != 'X' && (check < '0' || check > '9'))
				throw new RuntimeException("Illegal check digit: " + check);
			if (sum == 10 && check == 'X')
				System.out.println("Valid");
			else if (sum == check - '0')
				System.out.println("Valid");
			else
				System.out.println("Invalid");

		}
	
}

