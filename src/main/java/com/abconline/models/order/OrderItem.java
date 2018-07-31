package com.abconline.models.order;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

import com.abconline.models.basket.Basket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * models ordered items in an {@link Order}
 * @author etimbukudofia
 */
@Entity
@Embeddable
@Table(name = "order_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem {

  @Id
  @Column(name = "item_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long itemId;

  @Column(name = "item_quantity")
  private Integer itemQuantity;

  @Column(name = "item_name")
  private String itemName;

  @Column(name = "item_sku")
  private String itemSku;

  @Column(name = "item_description")
  private String itemDescription;

  @Column(name = "item_price")
  private String itemPrice;

  @Column(name = "date_of_purchase")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate dateOfPurchase;

  @JsonIgnore
  @ManyToOne
  private Order order;

  @JsonIgnore
  @ManyToOne
  private Basket basket;

  public OrderItem() {
    //lets be at peace with Jackson :-)
  }

  public OrderItem(Integer itemQuantity, String itemName, String itemSku,
      String itemDescription, String itemPrice, LocalDate dateOfPurchase) {
    this.itemQuantity = itemQuantity;
    this.itemName = itemName;
    this.itemSku = itemSku;
    this.itemDescription = itemDescription;
    this.itemPrice = itemPrice;
    this.dateOfPurchase = dateOfPurchase;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Integer getItemQuantity() {
    return itemQuantity;
  }

  public void setItemQuantity(Integer itemQuantity) {
    this.itemQuantity = itemQuantity;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getItemSku() {
    return itemSku;
  }

  public void setItemSku(String itemSku) {
    this.itemSku = itemSku;
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public String getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(String itemPrice) {
    this.itemPrice = itemPrice;
  }

  public LocalDate getDateOfPurchase() {
    return dateOfPurchase;
  }

  public void setDateOfPurchase(LocalDate dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(itemId, orderItem.itemId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(itemId);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("itemId", itemId)
        .append("itemQuantity", itemQuantity)
        .append("itemName", itemName)
        .append("itemSku", itemSku)
        .append("itemDescription", itemDescription)
        .append("itemPrice", itemPrice)
        .append("dateOfPurchase", dateOfPurchase)
        .toString();
  }
}