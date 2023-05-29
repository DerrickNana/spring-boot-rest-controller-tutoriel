package ca.derrick.springbootrestcontrollertutoriel.controller;

import ca.derrick.springbootrestcontrollertutoriel.model.Car;
import ca.derrick.springbootrestcontrollertutoriel.service.CarService;
import ca.derrick.springbootrestcontrollertutoriel.service.CarServiceImpl;
import ca.derrick.springbootrestcontrollertutoriel.util.Mapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for cars
 */
@RestController
@Validated
@RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarRestController {

    private final CarService carService;
    Logger logger = LogManager.getLogger(CarRestController.class);

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars(){
        List<Car> carsList =  carService.getAllCars();
        logger.info("CarRestController:getAllCars response from service {}", Mapper.mapToJsonString(carsList));
        return  carsList;
    }

    @GetMapping(params = {"minPrice", "maxPrice"})
    public List<Car> getAllCarsFilteredByprice(
            @RequestParam @Positive(message = "minPrice parameter must be greater than zero")
                    Double minPrice,
            @RequestParam @Positive(message = "maxPrice parameter must be greater than zero")
                    Double maxPrice){
        return carService.getCarWithPriceFilter(minPrice,maxPrice);
    }

    @GetMapping("/{id}")
    public Car getById(@PathVariable @Positive(message = "id parameter must be greater than zero") Long id){
        logger.info("CarRestController:getById fetch car by id {}", Mapper.mapToJsonString(id));
        Car carFound = carService.getById(id);
        logger.info("CarRestController:getById fetch car response {}", Mapper.mapToJsonString(carFound));
        return carFound;
    }

    @PostMapping
    public Car createCar(@Valid @RequestBody Car car){
        logger.info("CarRestController:createCar persist car request {}", Mapper.mapToJsonString(car));
        Car carCreated =  carService.createCar(car);
        logger.info("CarRestController:createCar response from service {}", Mapper.mapToJsonString(carCreated));
        return carCreated;
    }

    @PutMapping("/{id}")
    public Car updateCar(@Valid @RequestBody Car car,
                         @PathVariable @Positive(message = "id parameter must be greater than zero")
                                 Long id){
        return  carService.updateCar(id, car);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable @Positive(message = "id parameter must be greater than zero")
                                      Long id){
        carService.deleteCar(id);
    }
}
