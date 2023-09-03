package org.nghia;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.nghia.entity.Shirt;
import org.nghia.service.ShirtService;
import org.nghia.service.ShirtServiceIml;

/**
 * Hello world!
 */
public class App {

  private final static Scanner scanner = new Scanner(System.in);
  private final static ShirtService service = new ShirtServiceIml(scanner);
  public static List<Shirt> listShirt = new ArrayList<>();

  public static void main(String[] args) {
    service.menu();
  }
}
