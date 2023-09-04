package org.nghia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

  public static void main(String[] args) {
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    String jsonString = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
////    JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
//    System.out.println(gson.toJson(jsonString));
    service.menu();
  }
}
