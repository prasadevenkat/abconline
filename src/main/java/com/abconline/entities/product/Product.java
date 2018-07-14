package com.abconline.entities.product;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 */
public class Product {
  private Long id;
  private String name;
  private String sku;
  private String description;
  private Date createdAt;
  private Date updatedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id) &&
        Objects.equals(name, product.name) &&
        Objects.equals(sku, product.sku) &&
        Objects.equals(description, product.description) &&
        Objects.equals(createdAt, product.createdAt) &&
        Objects.equals(updatedOn, product.updatedOn);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, sku, description, createdAt, updatedOn);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("sku", sku)
        .append("description", description)
        .append("createdAt", createdAt)
        .append("updatedOn", updatedOn)
        .toString();
  }
}