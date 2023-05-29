package ca.derrick.springbootrestcontrollertutoriel.model;

import ca.derrick.springbootrestcontrollertutoriel.encrypt.MaskData;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class Car {
    private Long id;
    private String model;
    @MaskData
    private String brand;
    private Integer horses;
    private Double price;

    public Car() {
    }

    public Car(Long id, String model, String brand, Integer horses, Double price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.horses = horses;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "Model must not be null")
    @NotEmpty(message = "Model must have value")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull(message = "Brand must not be null")
    @NotEmpty(message = "Brand must have value")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotNull(message = "Horses must not be null")
    @Positive(message = "Horses must be positive")
    public Integer getHorses() {
        return horses;
    }

    public void setHorses(Integer horses) {
        this.horses = horses;
    }

    @NotNull(message = "Price must not be null")
    @Positive
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", horses=" + horses +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id.equals(car.id) && model.equals(car.model) && brand.equals(car.brand) && horses.equals(car.horses) && price.equals(car.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, brand, horses, price);
    }

}
