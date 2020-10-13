package com.crio.qcommerce.contract.insights;

public class SaleInsightsForVendorFactory {
  
  public static SaleInsightsForVendor getSaleInsightsForVendor(String provider) {
    if (provider.equalsIgnoreCase("FLIPKART")) {
      return new FlipkartSales();
    } else if (provider.equalsIgnoreCase("AMAZON")) {
      return new AmazonSales();
    } else {
      return new EbaySales();
    }
  }
}