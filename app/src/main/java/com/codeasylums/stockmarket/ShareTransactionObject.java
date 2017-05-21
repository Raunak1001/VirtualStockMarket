package com.codeasylums.stockmarket;

/**
 * Created by raunak on 21/5/17.
 */

public class ShareTransactionObject {

  String companyName;
  String shareRate;
  String amountShares;
  boolean soldOrBought;

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getShareRate() {
    return shareRate;
  }

  public void setShareRate(String shareRate) {
    this.shareRate = shareRate;
  }

  public String getAmountShares() {
    return amountShares;
  }

  public void setAmountShares(String amountShares) {
    this.amountShares = amountShares;
  }

  public boolean isSoldOrBought() {
    return soldOrBought;
  }

  public void setSoldOrBought(boolean soldOrBought) {
    this.soldOrBought = soldOrBought;
  }
}
