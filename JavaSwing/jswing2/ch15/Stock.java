// Stock.java
// A simple aggregate class for storing stock market information on a single
// stock (symbol, price, etc.).
//
import java.util.*;

  public class Stock {
    String symbol;
    double price;
    double delta;
    Date lastUpdate;

    public Stock(String s, double p) {
      symbol = s;
      price = p;
      lastUpdate = new Date();
    }

    public void update(double d) {
      delta = d;
      price += delta;
    }

    public void print() {
      System.out.println(symbol + ": " + price + " (" + delta 
			 + ") last updated " + lastUpdate);
    }
  }

