package org.nghia.dto;

public class Function {

  private Runnable runnable;
  private String description;

  public Function() {
  }

  public Function(Runnable runnable, String description) {
    this.runnable = runnable;
    this.description = description;
  }

  public Runnable getRunnable() {
    return runnable;
  }

  public void setRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
