package com.sauzny.sparkbaby.demolog.ml.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JavaDocument implements Serializable {

  private long id;
  private String text;

  public JavaDocument(long id, String text) {
    this.id = id;
    this.text = text;
  }

  public long getId() {
    return this.id;
  }

  public String getText() {
    return this.text;
  }
}