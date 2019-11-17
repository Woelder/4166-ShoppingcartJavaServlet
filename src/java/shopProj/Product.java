package shopProj;

import java.io.Serializable;
import java.text.NumberFormat;

public class Product implements Serializable {

    private String id;
    private String code;
    private String description;
    private double price;

    public Product() {
        id = "";
        code = "";
        description = "";
        price = 0;
    }
   public Product(String i,String c, String d, String pString) {
        id = i;
        code = c;
        description = d;
        price = new Double(pString);
      
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
      public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }
}