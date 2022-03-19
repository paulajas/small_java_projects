/**
 *
 *  @author Jasińska Paulina S20442
 *
 */

package zad2;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Sweden");
    String weatherJson = s.getWeather("Stockholm");
    Double rate1 = s.getRateFor("PLN");
    Double rate2 = s.getNBPRate();
    Panel panel = new Panel();
    panel.run();

  }
}
