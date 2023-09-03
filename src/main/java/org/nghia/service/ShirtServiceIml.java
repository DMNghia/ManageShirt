package org.nghia.service;

import static org.nghia.App.listShirt;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.nghia.dto.Function;
import org.nghia.entity.Shirt;

public class ShirtServiceIml implements ShirtService{

  private final String DATA_FILE = "data.txt";
  private final Scanner scanner;
  private final Map<String, Function> functions = new HashMap<>();

  public ShirtServiceIml(Scanner scanner) {
    this.scanner = scanner;
    getAllShirt();
  }

  private void getAllShirt() {
    try {
      FileInputStream inputStream = new FileInputStream(DATA_FILE);
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      listShirt = (List<Shirt>) objectInputStream.readObject();
      inputStream.close();
      objectInputStream.close();
    } catch (FileNotFoundException e) {
      System.out.println("Không thể tìm thấy file với tên: " + DATA_FILE);
    } catch (IOException e) {
//      System.out.println("Có lỗi: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println("Không tìm thấy class: " + e);
    }
  }

  private void displayFunctions(Map<String, Function> map) {
    map.forEach((s, function) -> {
      System.out.println(s + ". " + function.getDescription());
    });
  }

  private void exit() {
    System.out.println("Thoát...");
    scanner.close();
    Runtime.getRuntime().exit(0);
  }

  @Override
  public void show(List<?> list) {
    Gson gson = new Gson();
    System.out.println(gson.toJson(list));
  }

  @Override
  public void modify(){

  }

  @Override
  public void menu() {

    // Clear terminal và in ra thông báo menu
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        Runtime.getRuntime().exec("cls");
      }
      Runtime.getRuntime().exec("clear");
    } catch (IOException e) {
      System.err.println(e);
    }
    System.out.println("===============MENU===============");

    // Tạo danh sách các key của chức năng để xóa các key không tồn tại
    String[] listKeys = {"1", "2", "3", "4", "5", "6", "0"};

    // Clear functions, thêm các chức năng và key của nó
    functions.forEach((s, runnable) -> {
      if (!Arrays.asList(listKeys).contains(s)) {
        functions.remove(s);
      }
    });
    functions.put("1", new Function(() -> show(listShirt), "Hiển thị toàn bộ sản phẩm"));
    functions.put("2", new Function(this::findByName, "Tìm kiếm theo tên"));
    functions.put("3", new Function(this::add, "Thêm mới sản phẩm"));
    functions.put("4", new Function(this::modify, "Sửa đổi thông tin sản phẩm"));
    functions.put("5", new Function(this::delete, "Xóa sản phẩm"));
    functions.put("6", new Function(this::save, "Lưu toàn bộ sản phẩm"));
    functions.put("0", new Function(this::exit, "Thoát"));

    displayFunctions(functions);

    // Nhập chức năng
    String option;
    do {
      System.out.print("Nhập một chức năng: ");
      option = scanner.nextLine();
      if (functions.containsKey(option)) {
        functions.get(option).getRunnable().run();
      } else {
        System.out.println("Vui lòng chọn một trong các chức năng trên!");
        continue;
      }
      break;
    } while (true);
  }

  @Override
  public void save() {
    try {
      FileOutputStream outputStream = new FileOutputStream(DATA_FILE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(listShirt);
      outputStream.close();
      objectOutputStream.close();
      System.out.println("Lưu thành công");

    } catch (FileNotFoundException e) {
      System.out.println("Không thể tìm thấy file với tên: " + DATA_FILE);
    } catch (IOException e) {
      System.out.println("Có lỗi: " + e);
    }
  }

  @Override
  public void add() {

  }

  @Override
  public void delete() {

  }

  @Override
  public void findByName() {

  }
}
