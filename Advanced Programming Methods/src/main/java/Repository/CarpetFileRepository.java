package Repository;

import Domain.Carpet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class CarpetFileRepository extends AbstractRepo<Integer, Carpet>{
    public String filename;

    public CarpetFileRepository(String filename){
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile(){
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while((line = buffer.readLine()) != null)
            {
                String[] el = line.split(" - ");
                if (el.length != 5)
                {
                    System.err.println("Line is not valid!" + line);
                    continue;
                }

                try{
                    int id = Integer.parseInt(el[0]);
                    int dimension = Integer.parseInt(el[2]);
                    int price = Integer.parseInt(el[4]);
                    String name = el[1];
                    String color = el[3];
                    //boolean bool_prop = Boolean.parseBoolean(el[5]);
                    Carpet c = new Carpet(id, name, dimension, color, price);
                    super.add(c);
                    System.out.println("Carpet added successfully");
                }catch(NumberFormatException exception){
                    System.err.println("Invalid id format!" + el[0]);
                }
            }
        }catch (IOException exception){
            throw new RepositoryException("File reading error!" + exception);
        }
    }

    private void writeToFile(){
        try ( PrintWriter p = new PrintWriter(filename)){
            for (Carpet c: findAll()){
                String s = c.getID() + " - " + c.getName() + " - " + c.getDimension() + " - " + c.getColor() + " - " + c.getPrice();
                p.println(s);
            }
        }
        catch (IOException exception){throw new RepositoryException("Writing error" + exception );}
    }

    @Override
    public void add(Carpet o)
    {
        try{
            super.add(o);
            writeToFile();
        }
        catch(RuntimeException e)
        { throw new RepositoryException (e);}
    }

    @Override
    public void delete(Integer id)
    {
        try{
            super.delete(id);
            writeToFile();
        }catch(RuntimeException e){throw new RepositoryException(e);}

    }

    @Override
    public void update(Carpet o, Integer id)
    {
        try{
            super.update(o, id);
            writeToFile();
        }catch(RuntimeException ex){throw new RepositoryException(ex);}
    }




}

