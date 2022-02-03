package com.is.smartlight;

import com.is.smartlight.controllers.AuthController;
import com.is.smartlight.controllers.EnergyController;
import com.is.smartlight.controllers.LightGroupController;
import com.is.smartlight.controllers.WeatherController;
import com.is.smartlight.dtos.*;
import com.is.smartlight.models.*;
import com.is.smartlight.services.DefaultPresetService;
import com.is.smartlight.services.UserPresetService;
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
    public final UserPresetService userPresetService;
    public final DefaultPresetService defaultPresetService;
    private static final String BASE_URL = "http://localhost:8080";

    private static String token;
    private static Response response;
    @Autowired
    public StepDefs(AuthController authController, EnergyController energyController,
                    WeatherController weatherController, LightGroupController lightGroupController, UserPresetService userPresetService, DefaultPresetService defaultPresetService) {
        this.authController = authController;
        this.energyController = energyController;
        this.weatherController = weatherController;
        this.lightGroupController = lightGroupController;
        this.userPresetService = userPresetService;
        this.defaultPresetService = defaultPresetService;
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
        response = request.body("{\"id\":" + groupParams.get(0) + ",\"name\":\"" + groupParams.get(1) +
                "\",\"deleted\":" + groupParams.get(2) + "}").post("/lightgroups/add-group");
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

    @Then("^I add lightbulb")
    public void addLightbulb(List<String> params){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request
                .body("{\"id\":" + params.get(1) + ",\"redValue\":" + params.get(2) +
                        ",\"greenValue\": " + params.get(3) +",\"blueValue\":" + params.get(4) +
                        ",\"maxIntensity\":"+ params.get(5) + ",\"intensityPercentage\":" + params.get(6) +
                        ",\"turnedOn\":" + params.get(7) +",\"deleted\":" + params.get(8) +
                        ",\"working\":" + params.get(9) + "}")
                .post("/lightbulbs/add-lightbulb/" + params.get(0));

    }
    @Then("^I delete lightbulb")
    public void deleteLightbulb(Long id){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.delete("/lightbulbs/" + id.toString());


    }

    @Then("^I move lightbulb to another group$")
    public void moveLightbulb(List<Long> params){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.put("/lightgroups/move-lightbulb/" + params.get(0).toString() + "/" + params.get(1).toString());
        response = request.get("/lightgroups");
        Assert.assertTrue(response.jsonPath().get().toString().contains(params.get(1).toString()));
    }

    /*
    @Then("^I check for the existence of the preset$")
    public void getUserPresets(String presetName){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/userpresets");
        Assert.assertTrue(response.jsonPath().get().toString().contains(presetName));
    }
    */
    @Then("^I add a group with one light$")
    public void addGroupWithLight(String groupName) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.body("{\"id\":0" +
                ",\"name\":" + "\"" + groupName + "\"" +
                ",\"deleted\":false}").post("/lightgroups/add-group");

        LightGroupDto group = request.get("/lightgroups/getGroup/" + groupName).getBody().as(LightGroupDto.class);

        response = request.body("{\"id\":0,\"redValue\":0,\"greenValue\":0,\"blueValue\":0,\"maxIntensity\":1200,\"intensityPercentage\":0,\"turnedOn\":true,\"deleted\":false,\"working\":true}")
                .post("/lightbulbs/add-lightbulb/" + group.getId());
    }

    @Then("^I add a custom user preset$")
    public void addUserPreset(List<String> presetParams) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{\"id\":0"
                + ",\"name\":" + "\"" + presetParams.get(0) + "\""
        + ",\"deleted\":" + presetParams.get(1)
        + ",\"redValue\":" + Integer.parseInt(presetParams.get(2))
        + ",\"greenValue\":" + Integer.parseInt(presetParams.get(3))
        + ",\"blueValue\":" + Integer.parseInt(presetParams.get(4))
        + ",\"intensityPercentage\":" + Float.parseFloat(presetParams.get(5)) + "}").post("/userpresets/add-preset");
    }

    @Then("^I delete a custom user preset$")
    public void deleteUserPreset(String name){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        PresetDto up = request.get("/userpresets/getPreset/" + name).getBody().as(PresetDto.class);
        response = request.delete("/userpresets/" + up.getId().toString());
    }

    @And("^I check for the  \"(creation|deletion)\" for the preset$")
    public void checkForPreset(String operation, String presetName){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/userpresets");
        if(operation.equals("creation"))
            Assert.assertTrue(response.jsonPath().get().toString().contains(presetName));
        else
            Assert.assertTrue(!response.jsonPath().get().toString().contains(presetName));
    }

    @And("^I apply the user preset to a group$")
    public void applyUserPreset(List<String> params){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        PresetDto up = request.get("/userpresets/getPreset/" + params.get(0)).getBody().as(PresetDto.class);
        LightGroupDto group = request.get("/lightgroups/getGroup/" + params.get(1)).getBody().as(LightGroupDto.class);

        response = request.put("/userpresets/apply-preset/" + up.getId() + "/" + group.getId());
    }

    @Then("^I check if the preset has been correctly applied to the group$")
    public void checkAppliedCustomPreset(List<String> params) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        PresetDto up = request.get("/userpresets/getPreset/" + params.get(0)).getBody().as(PresetDto.class);
        LightGroupDto group = request.get("/lightgroups/getGroup/" + params.get(1)).getBody().as(LightGroupDto.class);

        Boolean presetIsApplied = true;

        for(LightbulbDto l : group.getLightbulbs()){
            if(!l.getRedValue().equals(up.getRedValue()) ||
            !l.getGreenValue().equals(up.getGreenValue()) ||
            !l.getBlueValue().equals(up.getBlueValue()) ||
            !l.getIntensityPercentage().equals(up.getIntensityPercentage())){
                presetIsApplied = false;
                break;
            }
        }

        Assert.assertTrue(presetIsApplied);
    }



    @And("^I apply the default preset to a group$")
    public void applyDefaultPreset(List<String> params){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        DefaultPreset defaultPreset = defaultPresetService.getDefaultPresetByName(params.get(0));
        LightGroupDto group = request.get("/lightgroups/getGroup/" + params.get(1)).getBody().as(LightGroupDto.class);

        response = request.put("/defaultpresets/apply-preset/" + defaultPreset.getId() + "/" + group.getId());
    }

    @Then("^I check if the default preset has been correctly applied to the group$")
    public void checkAppliedDefaultPreset(List<String> params) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        if(!params.get(0).equals("Random")){
            DefaultPreset defaultPreset = defaultPresetService.getDefaultPresetByName(params.get(0));
            LightGroupDto group = request.get("/lightgroups/getGroup/" + params.get(1)).getBody().as(LightGroupDto.class);

            Boolean presetIsApplied = true;


            for(LightbulbDto l : group.getLightbulbs()){
                if(!l.getRedValue().equals(defaultPreset.getRedValue()) ||
                        !l.getGreenValue().equals(defaultPreset.getGreenValue()) ||
                        !l.getBlueValue().equals(defaultPreset.getBlueValue()) ||
                        !l.getIntensityPercentage().equals(defaultPreset.getIntensityPercentage())){
                    presetIsApplied = false;
                    break;
                }
            }

            Assert.assertTrue(presetIsApplied);
        }
        else {
            Assert.assertTrue(true);
        }

    }
}



