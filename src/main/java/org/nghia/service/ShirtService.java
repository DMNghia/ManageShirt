package org.nghia.service;

import java.io.IOException;
import java.util.List;

public interface ShirtService {
  void show(List<?> list);
  void modify();
  void menu();
  void save();
  void add();
  void delete();
  void findByName();
}
