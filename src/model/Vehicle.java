package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Vehicle entity
 */
public class Vehicle implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String brand;
    private String model;

    public Vehicle() {}

    /**
     * Constructor for creating new Vehicle object
     * @param id: unique numeric id of a vehicle
     * @param brand: brand of a vehicle
     * @param model: specific model of a vehicle
     */
    public Vehicle(long id, String brand, String model)
    {
        super();
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public long getId()
    {
        return id;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    //overriding equals and hashCode methods
    //for proper using of Java Collections Framework
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(brand, vehicle.brand) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, brand, model);
    }

    @Override
    public String toString()
    {
        return "Vehicle #" + id + ": brand = " + brand + ", model = " + model + "";
    }
}
