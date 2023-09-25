package general;

import java.util.ArrayList;

public class Mountains {
  ArrayList<String> mountains = new ArrayList<>();

  public Mountains() {
    mountains.add("Nebo");
    mountains.add("Timpanogos");
    mountains.add("Lone Peak");
    mountains.add("Cascade");
    mountains.add("Provo");
    mountains.add("Spanish Fork");
    mountains.add("Santaquin");
  }

  public void printList() {
    System.out.println(mountains);
  }
}
