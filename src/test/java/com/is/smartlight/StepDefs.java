package com.is.smartlight;

import com.is.smartlight.config.AuthClient;
import com.is.smartlight.controllers.AuthController;
import com.is.smartlight.controllers.EnergyController;
import com.is.smartlight.controllers.WeatherController;
import com.is.smartlight.dtos.EnergyDto;
import com.is.smartlight.dtos.LoginDto;
import com.is.smartlight.dtos.TokenDto;
import com.is.smartlight.repositories.UserRepository;
import com.is.smartlight.services.AuthService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.af.En;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefs extends SpringIntegrationTest {
    private final AuthController authController;
    public final EnergyController energyController;
    public final WeatherController weatherController;
    private static final String BASE_URL = "http://localhost:8080";

    private static String token;
    private static Response response;
    @Autowired
    public StepDefs(AuthController authController, EnergyController energyController,
                    WeatherController weatherController) {
        this.authController = authController;
        this.energyController = energyController;
        this.weatherController = weatherController;
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

}
