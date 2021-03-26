package domain;

import java.io.Serializable;

public class Car implements Comparable<Car>, Identifiable<Integer>, Serializable {
    private int ID;
    private String manufacturer;
    private String model;
    private int maxspeed;
    private int year;
    private double price;

    //CONSTRUCTORS
    public Car()
    {
        this.ID=0;
        this.manufacturer="";
        this.model="";
        this.maxspeed=0;
        this.year=0;
        this.price=0;
    }
    public Car(int ID, String manufacturer, String model, int maxspeed, int year, double price)
    {
        this.ID=ID;
        this.manufacturer=manufacturer;
        this.model=model;
        this.maxspeed=maxspeed;
        this.year=year;
        this.price=price;
    }

    //GETTERS
    public Integer getID(){return this.ID;}
    public String getManufacturer()
    {
        return this.manufacturer;
    }
    public String getModel()
    {
        return this.model;
    }
    public int getMaxspeed(){
        return this.maxspeed;
    }
    public int getYear() { return this.year; }
    public double getPrice(){return this.price;}

    //SETTERS
    public void setID(Integer ID){this.ID=ID;}
    public void setManufacturer(String manufacturer){
        this.manufacturer=manufacturer;
    }
    public void setModel(String model){
        this.model=model;
    }
    public void setMaxspeed(int maxspeed){
        this.maxspeed=maxspeed;
    }
    public void setYear(int year){
        this.year=year;
    }
    public void setPrice(double price){this.price=price;}

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Car)
        {
            Car c=(Car)obj;
            return this.ID==c.ID && this.manufacturer.equals(c.manufacturer) && this.model.equals(c.model) && this.maxspeed==c.maxspeed && this.year==c.year && this.price==c.price;
        }
        return false;
    }

    @Override
    public String toString(){
        return "" + this.ID + " - " + this.manufacturer + " - " + this.model + " - " + this.maxspeed + " - " + this.year + " - " + this.price;
    }

    public int compareTo(Car c){
        if(c.maxspeed>this.maxspeed)
            return -1;
        else if(c.maxspeed<this.maxspeed)
            return 1;
        return 0;
    }

    public int compareByPrice(Car c){
        if(c.price>this.price)
            return -1;
        else if(c.price<this.price)
            return 1;
        return 0;
    }
}
