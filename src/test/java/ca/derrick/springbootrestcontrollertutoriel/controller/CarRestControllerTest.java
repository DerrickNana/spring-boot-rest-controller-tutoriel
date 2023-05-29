package ca.derrick.springbootrestcontrollertutoriel.controller;

import ca.derrick.springbootrestcontrollertutoriel.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Test getting all cars")
    void getAllCarsTest() throws Exception {
        List<Car> expectedResult = objectMapper.readValue(
              TestHelper.readFile("/get_all_cars.json"), List.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<Car> actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting car with id 1")
    void getByIdTest() throws Exception {
        Car expectedResult = objectMapper.readValue(
                TestHelper.readFile("/get_car_id_1.json"), Car.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Car actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    @DisplayName("Test getting car with id 4 - does not exist")
    void getByIdNotExistTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test update price of car with id 1 to 19000")
    @DirtiesContext
    void updateCarTest() throws Exception {
        String requestBody = TestHelper.readFile("/update_car_with_id_1_request.json");
        String responseBody = TestHelper.readFile("/update_car_with_id_1_response.json");
        Car expectedResult = objectMapper.readValue(responseBody, Car.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cars/1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Car actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test creating a new car")
    @DirtiesContext
    void createCarTest() throws Exception {
        String requestBody = TestHelper.readFile("/create_lamborghini_gallardo_request.json");
        String responseBody = TestHelper.readFile("/create_lamborghini_gallardo_response.json");
        Car expectedResult = objectMapper.readValue(responseBody, Car.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Car actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test Delete an existing car")
    @DirtiesContext
    void deleteAnExistingCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/3"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test Delete an non existing car")
    @DirtiesContext
    void deleteAnNonExistingCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("[\"Id 5 inexistant\"]"));
    }

    @Test
    @DisplayName("Test getting all cars with price between 10000 and 20000 euro")
    void getCarsFilteredByPrice() throws Exception {
        List<Car> expectedResult = objectMapper.readValue(
                TestHelper.readFile("/get_cars_by_price.json"), List.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cars?minPrice=10000&maxPrice=20000"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<Car> actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
