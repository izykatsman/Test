package Model;

public class Product {
    private int id;
    private String name;
    private int count;
    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public int getCount() {return count;}
    public void setCount(int id){this.count = count;}

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    public Product(int id, int count, String name){
        this.id = id;
        this.count = count;
        this.name = name;
    }

    public Product(int count, String name){
        this.count = count;
        this.name = name;
    }
}
