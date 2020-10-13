package com.crio.qcommerce.contract.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.resolver.DataProvider;
import java.io.IOException;

public class SaleInsightsImpl implements SaleInsights {
  
  public SaleAggregate getSaleInsights(DataProvider dataProvider, int year)
      throws IOException, AnalyticsException {
    
    SaleInsightsForVendorFactory saleInsightsForVendorFactory = new 
            SaleInsightsForVendorFactory();
    String vendorName = dataProvider.getProvider();
    SaleInsightsForVendor saleInsightsForVendor = saleInsightsForVendorFactory
            .getSaleInsightsForVendor(vendorName);
    return saleInsightsForVendor.calcSaleInsightsForVendor(dataProvider, year);
  }
}