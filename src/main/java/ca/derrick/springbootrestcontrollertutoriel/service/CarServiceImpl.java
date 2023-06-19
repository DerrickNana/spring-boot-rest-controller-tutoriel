package ca.derrick.springbootrestcontrollertutoriel.service;

import ca.derrick.springbootrestcontrollertutoriel.model.Car;
import ca.derrick.springbootrestcontrollertutoriel.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{

    Logger logger = LogManager.getLogger(CarServiceImpl.class);

    private List<Car> cars = new ArrayList<>(
            Arrays.asList(
                    new Car(1L,"Astra", "NANA Opel", 100,18000d),
                    new Car(2L,"Insignia", "Opel", 120,22000d),
                    new Car(3L,"Golf", "VW", 90,17000d)
            )
    );

    @Override
    public List<Car> getAllCars() {
        logger.info("CarService:getAllCars execution started ...");
        logger.info("CarService:getAllCars response {}", Mapper.mapToJsonString(cars));
        logger.info("CarService:getAllCars execution ended ...");
        return cars;
    }

    @Override
    public List<Car> getCarWithPriceFilter(Double min, Double max) {
        logger.info("CarService:getCarWithPriceFilter execution started ...");
        logger.info("CarService:getCarWithPriceFilter request min {} and max {}",
                Mapper.mapToJsonString(min), Mapper.mapToJsonString(max));
        return cars.stream().filter(car -> car.getPrice() >= min && car.getPrice() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public Car getById(Long id) {
        logger.info("CarService:getById execution started ...");
        Car carFound = cars.stream().filter(car -> car.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Id "+id+" inexistant"));
        logger.info("CarService:getById response {}", Mapper.mapToJsonString(carFound));
        logger.info("CarService:getById execution ended ...");
        return carFound;
    }

    @Override
    public Car updateCar(Long id, Car carRequest) {
        logger.info("CarService:updateCar execution started ...");
        logger.info("CarService:updateCar request id {} and payload {}", id, Mapper.mapToJsonString(carRequest));
        Car carToBeUpdated = getById(id);
        carToBeUpdated.setBrand(carRequest.getBrand());
        carToBeUpdated.setHorses(carRequest.getHorses());
        carToBeUpdated.setModel(carRequest.getModel());
        carToBeUpdated.setPrice(carRequest.getPrice());
        logger.info("CarService:updateCar response {}", Mapper.mapToJsonString(carToBeUpdated));
        logger.info("CarService:updateCar execution ended ...");
        return carToBeUpdated;
    }

    @Override
    public Car createCar(Car car) {
        logger.info("CarService:createCar execution started ...");
        logger.info("CarService:createCar request payload {}", Mapper.mapToJsonString(car));
        Long newId = cars.stream().mapToLong(car_ -> Long.valueOf(car_.getId()))
                .max().orElse(0L) + 1L;
        car.setId(newId);
        cars.add(car);
        logger.info("CarService:createCar response {}", Mapper.mapToJsonString(car));
        logger.info("CarService:createCar execution ended ...");
        return getById(car.getId());
    }

    @Override
    public void deleteCar(Long id) {
        boolean successFullDeletion = cars.removeIf(car-> car.getId().equals(id));
        if(!successFullDeletion){
            throw new NoSuchElementException("Id "+id+" inexistant");
        }
    }
}
