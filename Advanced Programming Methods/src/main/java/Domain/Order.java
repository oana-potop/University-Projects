package Domain;
import java.io.Serializable;

public class Order implements Identifiable<Integer>{

    private int ID;
    private String namePers;
    private String address;
    private String date;
    private Carpet carpet;

    //CONSTRUCTORS
    public Order(int ID, String namePers, String address, String date, Carpet carpet){
        this.ID=ID;
        this.namePers=namePers;
        this.address=address;
        this.date=date;
        this.carpet=carpet;
    }
    public Order(){
        this.ID=0;
        this.namePers="";
        this.address="";
        this.date="";
        this.carpet=new Carpet();
    }

    //GETTERS
    public Integer getID(){ return this.ID; }
    public String getNamePers(){return this.namePers;}
    public String getAddress(){return this.address;}
    public String getDate(){return this.date;}
    public Carpet getCarpet() { return this.carpet; }

    //SETTERS
    public void setID(Integer ID) { this.ID=ID; }
    public void setNamePers(String namePers) {this.namePers=namePers;}
    public void setAddress(String address) {this.address=address;}
    public void setDate(String date){this.date=date;}
    public void setCarpet(Carpet carpet){this.carpet=carpet;}

    @Override
    public String toString(){
        return this.ID + "; " + this.namePers + "; " + this.address + "; " + this.date + "; " + this.carpet.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Order) {
            Order o =(Order) obj;
            return this.namePers.equals(o.namePers) && this.address.equals(o.address) && this.date.equals(o.date) && this.carpet == o.carpet;
        }
        return false;
    }

}
