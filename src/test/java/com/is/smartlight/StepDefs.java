package com.is.smartlight;

import com.is.smartlight.controllers.AuthController;
import com.is.smartlight.controllers.EnergyController;
import com.is.smartlight.controllers.LightGroupController;
import com.is.smartlight.controllers.WeatherController;
import com.is.smartlight.dtos.LoginDto;
import com.is.smartlight.dtos.TokenDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefs extends SpringIntegrationTest {
    private final AuthController authController;
    public final EnergyController energyController;
    public final WeatherController weatherController;
    public final LightGroupController lightGroupController;
    private static final String BASE_URL = "http://localhost:8080";

    private static String token;
    private static Response response;
    @Autowired
    public StepDefs(AuthController authController, EnergyController energyController,
                    WeatherController weatherController, LightGroupController lightGroupController) {
        this.authController = authController;
        this.energyController = energyController;
        this.weatherController = weatherController;
        this.lightGroupController = lightGroupController;
    }
 @Given("^I authenticate user with following parameters$")
    public void authenticate(List<String> credentials) throws ParseException, JSONException {
        LoginDto loginDto = new LoginDto(credentials.get(0),credentials.get(1),credentials.get(2));
        token =  ((TokenDto) authController.login(loginDto).getBody()).getAccessToken();
 }
    @Then("^I add an energy consumption record with the following parameters$")
    public void addEnergy(List<String> energyParams){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.body("{\"pricePerkWh\":" + Float.parseFloat(energyParams.get(0)) +",\"date\":\""+energyParams.get(1)+"\"}")
                .post("/energy/add-energy");

    }

    @And("^I check  the history of energy consumption to have the following parameters$")
    public void getEnergyHistory(List<String> energyParams){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/energy/get-energy");
        Assert.assertTrue(response.jsonPath().get().toString().contains(energyParams.get(1)));

    }

    @Then("^I check the outside light intensity in the city$")
    public void getOutsideIntensity(String city){
        System.out.println(weatherController.getOutsideLuminosity(city));
    }

 @Given("^I authenticate user with next parameters$")
 public void auth(List<String> credentials) throws ParseException, JSONException {
     LoginDto loginDto = new LoginDto(credentials.get(0),credentials.get(1),credentials.get(2));
     token =  ((TokenDto) authController.login(loginDto).getBody()).getAccessToken();
 }
    @Then("^I get list of lightgroups$")
    public void getLightGroups(String room){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/lightgroups");
        Assert.assertTrue(response.jsonPath().get().toString().contains(room));
    }
    @And("^I add a new group of lights$")
    public void addLightGroup(List<String> groupParams){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.body("{\"id\":2233,\"name\":\"pivnita\",\"deleted\":false}").post("/lightgroups/add-group");
    }
    @Then("^I delete a group$")
    public void deleteGroup(Long id){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.delete("/lightgroups/" + id.toString());
    }

    @And("^I check for the  \"(creation|deletion)\" for the group$")
    public void checkForGroup(String operation, String room){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/lightgroups");
        if(operation.equals("creation"))
            Assert.assertTrue(response.jsonPath().get().toString().contains(room));
        else
            Assert.assertTrue(!response.jsonPath().get().toString().contains(room));
    }
}
