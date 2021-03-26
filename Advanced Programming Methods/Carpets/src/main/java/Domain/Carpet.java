package Domain;

import java.io.Serializable;

public class Carpet implements Comparable<Carpet>,Identifiable<Integer>, Serializable {

    private int ID;
    private String name;
    private int dimension;
    private String color;
    private int price;

    //CONSTRUCTORS
    public Carpet(int ID, String name, int dimension, String color, int price){
        this.ID=ID;
        this.name=name;
        this.dimension=dimension;
        this.color=color;
        this.price=price;
    }
    public Carpet(){
        this.ID=0;
        this.name="";
        this.dimension=0;
        this.color="";
        this.price=0;
    }

    //GETTERS
    public Integer getID(){ return this.ID; }
    public String getName() { return this.name; }
    public int getDimension() { return this.dimension; }
    public String getColor() { return this.color; }
    public int getPrice() { return this.price; }

    //SETTERS
    public void setID(Integer ID){ this.ID = ID; }
    public void setName(String name) { this.name = name; }
    public void setDimension(int dimension) {this.dimension = dimension; }
    public void setColor(String color) {this.color=color; }
    public void setPrice(int price) {this.price=price;}


    @Override
    public String toString(){
        return this.ID + " - " + this.name + " - " + this.dimension + " - " + this.color + " - " + this.price;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Carpet) {
            Carpet c =(Carpet) obj;
            return this.name.equals(c.name) && this.dimension == (c.dimension) && this.color.equals(c.color) && this.price == c.price;
        }
        return false;
    }

    public int compareTo(Carpet c){
        if (this.price > c.price)
            return 1;
        if (this.price < c.price)
            return -1;
        return 0;
    }




}
