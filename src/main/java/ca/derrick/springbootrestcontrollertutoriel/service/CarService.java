package ca.derrick.springbootrestcontrollertutoriel.service;

import ca.derrick.springbootrestcontrollertutoriel.model.Car;

import java.util.List;

public interface CarService {

    /**
     * Retrieves all cars currently existings
     * @return List<Car>
     */
    List<Car> getAllCars();

    /**
     * Retrieves all cars with price filter
     * @param min
     * @param max
     * @return List<Car>
     */
    List<Car> getCarWithPriceFilter(Double min, Double max);

    /**
     * Retrieve a car by his id
     * @param id id use to retrieve a car
     * @return Car
     */
    Car getById(Long id);

    /**
     * *
     * @param id id use to update a car
     * @param carRequest
     * @return Car
     */
    Car updateCar(Long id, Car carRequest);

    /**
     * *
     * @param car the car object to be created
     * @return Car the car object that was created
     */
    Car createCar(Car car);

    /**
     * *
     * @param id
     */
    void deleteCar(Long id);
}
