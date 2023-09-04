package org.nghia;

import java.util.Scanner;
import org.nghia.service.ShirtService;
import org.nghia.service.ShirtServiceIml;

/**
 * Hello world!
 */
public class App {

  private final static Scanner scanner = new Scanner(System.in);
  private final static ShirtService service = new ShirtServiceIml(scanner);

  public static void main(String[] args) {
    service.menu();
  }
}
