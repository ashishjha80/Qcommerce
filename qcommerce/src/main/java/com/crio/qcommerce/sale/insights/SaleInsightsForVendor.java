package com.crio.qcommerce.sale.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.insights.SaleAggregate;
import com.crio.qcommerce.contract.resolver.DataProvider;
import java.io.IOException;

public interface SaleInsightsForVendor {

  SaleAggregate calcSaleInsightsForVendor(DataProvider dataProvider, int year)
      throws IOException, AnalyticsException;   
}