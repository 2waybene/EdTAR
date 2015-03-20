// MYOSM.java
// Make Your Own Stock Market: A simple stock market simulator that contains a
// few stocks and their current prices (and deltas).  It randomly adjusts the 
// prices on stocks to give a dynamic feel to the data.
//
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MYOSM extends JFrame implements Runnable {

  Stock[] market = {
    new Stock("JTree", 14.57),
    new Stock("JTable", 17.44),
    new Stock("JList", 16.44),
    new Stock("JButton", 7.21),
    new Stock("JComponent", 27.40)
  };    
  boolean monitor;
  Random rg = new Random();
  Thread runner;

  public MYOSM() {
    // Not meant to be shown as a real frame
    super("Thread only version . . .");
    runner = new Thread(this);
    runner.start();
  }

  // This version creates a real frame so that you can see how the typical
  // stocks get updated.  It's not meant to be used with other programs,
  // but rather as a debugging tool to make sure the market runs ok.
  public MYOSM(boolean monitorOn) {
    super("Stock Market Monitor");
    setSize(400, 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    monitor = monitorOn;

    getContentPane().add(new JLabel("Trading is active.  " + 
          "Close this window to close the market."), 
          BorderLayout.CENTER);
    runner = new Thread(this);
    runner.start();
  }

  // Here's the heart of our stock market. In an infinite loop, just pick a
  // random stock and update its price.  To make the program interesting, we'll
  // update a price every second.
  public void run() {
    while(true) {
      int whichStock = Math.abs(rg.nextInt()) % market.length;
      double delta = rg.nextDouble() - 0.4;
      market[whichStock].update(delta);
      if (monitor) {
        market[whichStock].print();
      }
      try {
        Thread.sleep(1000);
      }
      catch(InterruptedException ie) {
      }
    }
  }

  public Stock getQuote(int index) {
    return market[index];
  }

  // This method returns the list of all the symbols in the market table
  public String[] getSymbols() {
    String[] symbols = new String[market.length];
    for (int i = 0; i < market.length; i++) {
      symbols[i] = market[i].symbol;
    }
    return symbols;
  }

  public static void main(String args[]) {
    MYOSM myMarket = new MYOSM(args.length > 0);
    myMarket.setVisible(true);
  }
}
