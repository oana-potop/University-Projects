package domain;

public class Reservation implements Identifiable<Integer> {
    private int ID;
    private Car car;
    private String nameOwner;
    private String phoneNumber;
    private String initDate;
    private String finDate;

    public Reservation(Integer ID, Car car, String nameOwner, String phoneNumber, String initDate, String finDate){
        this.ID = ID;
        this.car = car;
        this.finDate = finDate;
        this.initDate = initDate;
        this.phoneNumber = phoneNumber;
        this.nameOwner = nameOwner;
    }

    //GETTERS
    public Integer getID(){return this.ID;}
    public Car getCar(){return this.car;}
    public String getNameOwner(){return this.nameOwner;}
    public String getPhoneNumber(){return this.phoneNumber;}
    public String getInitDate(){return this.initDate;}
    public String getFinDate(){return this.finDate;}

    //SETTERS
    public void setID(Integer ID){this.ID=ID;}
    public void setCar(Car car){this.car=car;}
    public void setNameOwner(String nameOwner){this.nameOwner=nameOwner;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber=phoneNumber;}
    public void setInitDate(String initDate){this.initDate=initDate;}
    public void setFinDate(String finDate){this.finDate=finDate;}

    @Override
    public boolean equals(Object reference)
    {
        if(reference instanceof Reservation)
        {
            Reservation instance = (Reservation) reference;
            return this.ID == instance.ID;
        }
        return false;
    }
    @Override
    public String toString(){
        return "" + this.ID + " - " + this.nameOwner + " - " + this.phoneNumber + " - " + this.initDate + " - " + this.finDate;
    }

}
