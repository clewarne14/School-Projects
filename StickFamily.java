import java.awt.*;
import java.util.*;

public class StickFamily
{
  public static void main(String[] args)
  {
    DrawingCanvas canvas = new DrawingCanvas(1000,500);
    Graphics g = canvas.getGraphics();

    Person Homer = new Person("Homer", 42, 300);
    Person Marge = new Person("Marge", 38, 275);
    Person Bart = new Person("Bart", 14, 150);

    Homer.drawPerson(g, 275, 375, Homer.getHeight(), Homer.getName(), Homer.getAge());
    Marge.drawPerson(g, 125, 375, Marge.getHeight(), Marge.getName(), Marge.getAge());
    Bart.drawPerson(g, 425, 375, Bart.getHeight(), Bart.getName(), Bart.getAge());
    




  }
}
