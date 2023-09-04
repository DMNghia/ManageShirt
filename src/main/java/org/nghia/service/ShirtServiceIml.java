package org.nghia.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.nghia.dto.Function;
import org.nghia.entity.Shirt;
import org.nghia.entity.Size;
import org.nghia.entity.Type;

public class ShirtServiceIml implements ShirtService {

  private static Map<Integer, Shirt> storage = new HashMap<>();
  private final String DATA_FILE = "data.txt";
  private final Scanner scanner;
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static Map<String, Function> functions = new HashMap<>();

  public ShirtServiceIml(Scanner scanner) {
    this.scanner = scanner;
    getAllShirt();
  }

  private void getAllShirt() {
    try {
      FileInputStream inputStream = new FileInputStream(DATA_FILE);
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      storage = (Map<Integer, Shirt>) objectInputStream.readObject();
      inputStream.close();
      objectInputStream.close();
    } catch (FileNotFoundException e) {
      System.out.println("Không thể tìm thấy file với tên: " + DATA_FILE);
    } catch (IOException ignored) {
    } catch (ClassNotFoundException e) {
      System.out.println("Không tìm thấy class: " + e);
    }
  }

  private void displayFunctions(Map<String, Function> map) {
    map.forEach((s, function) ->
        System.out.println(s + ". " + function.getDescription()));
  }

//  private void clearTerminal() {
//    // Clear terminal
//    String os = System.getProperty("os.name").toLowerCase();
//    try {
//      if (os.contains("windows")) {
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//      }
//      // For Unix-like systems (including macOS, Linux, and Zsh)
//      Runtime.getRuntime().exec("clear");
//    } catch (IOException | InterruptedException e) {
//      System.err.println(e.getMessage());
//    }
//  }

  private Shirt createProduct() {
    String name, clb, materials_raw;
    Size size;
    Type type;
    List<String> materials;
    System.out.print("Nhập tên sản phẩm: ");
    name = scanner.nextLine();
    System.out.print("Nhập tên CLB: ");
    clb = scanner.nextLine();
    do {
      try {
        System.out.print("Nhập size: ");
        size = Size.valueOf(scanner.nextLine().toUpperCase());
        break;
      } catch (IllegalArgumentException e) {
        System.out.println("Size phải là một trong các size sau đây: M, L, XL, XXL, XXXL");
      }
    } while (true);
    do {
      try {
        System.out.print("Nhập loại: ");
        type = Type.valueOf(scanner.nextLine().toUpperCase());
        break;
      } catch (IllegalArgumentException e) {
        System.out.println("Loại phải là: long -> áo dài; short -> áo ngắn");
      }
    } while (true);
    System.out.print(
        "Nhập các loại nguyên liệu của áo (các nguyên liệu ngăn cách bằng dấu \";\" hoặc \"-\"): ");
    materials_raw = scanner.nextLine();
    String[] mater = materials_raw.split("[;|-]");
    materials = Arrays.asList(mater);
    return new Shirt(name, clb, size, type, materials);
  }

  private void clearFunctions(String[] listKeys) {
    // Clear functions
    var iterator = functions.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, Function> entry = iterator.next();
      String key = entry.getKey();
      if (!Arrays.asList(listKeys).contains(key)) {
        // Remove the entry from the map using the iterator's remove() method
        iterator.remove();
      }
    }
  }

  private void performFunction(Map<String, Function> listFunctions) {
    // Nhập chức năng
    String option;
    do {
      System.out.print("Nhập một chức năng: ");
      option = scanner.nextLine();
      if (listFunctions.containsKey(option)) {
        listFunctions.get(option).getRunnable().run();
      } else {
        System.out.println("Vui lòng chọn một trong các chức năng trên!");
        continue;
      }
      break;
    } while (true);
  }

  private void exit() {
    System.out.println("Thoát...");
    scanner.close();
    Runtime.getRuntime().exit(0);
  }

  @Override
  public void show(List<?> list) {
    if (list.isEmpty()) {
      System.out.println("Kho sản phẩm trống");
    } else {
      System.out.println(gson.toJson(list));
    }
    menu();
  }

  @Override
  public void modify() {
    System.out.println("==========Sửa đổi thông tin==========");
    int id;
    do {
      try {
        System.out.print("Nhập id của sản phẩm cần sửa đổi: ");
        id = Integer.parseInt(scanner.nextLine());
        break;
      } catch (NumberFormatException e) {
        System.out.println("Vui lòng nhập id là số và nhập đúng id của sản phẩm");
      }
    } while (true);
    Shirt shirtFound = storage.get(id);
    if (shirtFound != null) {
      System.out.println(gson.toJson(shirtFound));
      Shirt newShirt = createProduct();
      newShirt.setId(id);
      storage.replace(id, newShirt);
      System.out.println("Chỉnh sửa thành công");
      menu();
    }

    System.out.println("Không tìm thấy sản phẩm");
    String[] listKeys = {"1", "2", "3"};
    clearFunctions(listKeys);
    functions.put("1", new Function(() -> show(new ArrayList<>(storage.values())),
        "Hiển thị toàn bộ sản phẩm"));
    functions.put("2", new Function(this::modify, "Tiếp tục"));
    functions.put("3", new Function(this::menu, "Trở về menu"));
    displayFunctions(functions);
    performFunction(functions);
  }

  @Override
  public void menu() {
//    clearTerminal();
    System.out.println("===============MENU===============");

    // Tạo danh sách các key của chức năng để xóa các key không tồn tại
    String[] listKeys = {"1", "2", "3", "4", "5", "6", "0"};

    clearFunctions(listKeys);
    functions.put("1", new Function(() -> show(new ArrayList<>(storage.values())),
        "Hiển thị toàn bộ sản phẩm"));
    functions.put("2", new Function(this::findByName, "Tìm kiếm theo tên"));
    functions.put("3", new Function(this::add, "Thêm mới sản phẩm"));
    functions.put("4", new Function(this::modify, "Sửa đổi thông tin sản phẩm"));
    functions.put("5", new Function(this::delete, "Xóa sản phẩm"));
    functions.put("6", new Function(this::save, "Lưu toàn bộ sản phẩm"));
    functions.put("0", new Function(this::exit, "Thoát"));

    displayFunctions(functions);

    performFunction(functions);
  }

  @Override
  public void save() {
    try {
      FileOutputStream outputStream = new FileOutputStream(DATA_FILE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(storage);
      outputStream.close();
      objectOutputStream.close();
      System.out.println("Lưu thành công");
      menu();
    } catch (FileNotFoundException e) {
      System.out.println("Không thể tìm thấy file với tên: " + DATA_FILE);
    } catch (IOException e) {
      System.out.println("Có lỗi: " + e);
    }
  }

  @Override
  public void add() {
//    clearTerminal();
    System.out.println("==========Thêm sản phẩm==========");
    Shirt newShirt = createProduct();
    Integer newId = storage.size() + 1;
    newShirt.setId(newId);
    storage.put(newId, newShirt);
    System.out.println("Thêm thành công");
    System.out.println(gson.toJson(newShirt));
    menu();
  }

  @Override
  public void delete() {
    System.out.println("==========Xóa sản phẩm==========");
    int id;
    do {
      try {
        System.out.print("Nhập id của sản phẩm cần xóa: ");
        id = Integer.parseInt(scanner.nextLine());
        break;
      } catch (NumberFormatException e) {
        System.out.println("Vui lòng nhập id là số và nhập đúng id của sản phẩm");
      }
    } while (true);
    Shirt shirtDelete = storage.get(id);
    if (shirtDelete != null) {
      System.out.println(gson.toJson(shirtDelete));
      storage.remove(shirtDelete.getId());
      menu();
    }

    System.out.println("Không tìm thấy sản phẩm");
    String[] listKeys = {"1", "2", "3"};
    clearFunctions(listKeys);
    functions.put("1", new Function(() -> show(new ArrayList<>(storage.values())),
        "Hiển thị toàn bộ sản phẩm"));
    functions.put("2", new Function(this::delete, "Tiếp tục"));
    functions.put("3", new Function(this::menu, "Trở về menu"));
    displayFunctions(functions);
    performFunction(functions);
  }

  @Override
  public void findByName() {
    System.out.println("==========Tìm kiếm theo tên=========");
    String name;
    System.out.print("Nhập tên: ");
    name = scanner.nextLine();
    var list = storage.values().stream()
        .filter(shirt -> shirt.getName().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
    show(list);
  }
}
