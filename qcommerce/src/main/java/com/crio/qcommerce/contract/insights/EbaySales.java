package com.crio.qcommerce.contract.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.resolver.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EbaySales implements SaleInsightsForVendor {

  public SaleAggregate  calcSaleInsightsForVendor(DataProvider dataProvider, int year)
      throws IOException, AnalyticsException {
    
    
    Double totalSales = 0d;
    List<Double> salesByMonths = new ArrayList<>();
    for (int i = 0;i < 12;i++) {
      salesByMonths.add(0d);
      
    }
    File csvFile = dataProvider.resolveFile();
    String line = "";
    String splitBy = ",";
     
    FileInputStream fis = new FileInputStream(csvFile);
    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
    BufferedReader br = new BufferedReader(isr);
    try {
      while ((line = br.readLine()) != null) {
        String[] record = line.split(splitBy, -1);
        if (record[3].isEmpty() || record[4].isEmpty()) {
          AnalyticsException ex = new AnalyticsException("missing date or amount");
          throw ex;
        }
        if (record[2].isEmpty() || record[2].equals("complete") || record[2].equals("Delivered")) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
          LocalDate date = LocalDate.parse(record[3],formatter);
          if (date.getYear() == year) {
            totalSales = totalSales + Double.parseDouble(record[4]);
            Double currentMonthSales = salesByMonths.get(date.getMonthValue() - 1);
            currentMonthSales = currentMonthSales + Double.parseDouble(record[4]);
            salesByMonths.set(date.getMonthValue() - 1,currentMonthSales);
          }
        }
      }
    } finally {
      br.close();
    }
    
    SaleAggregate sales = new SaleAggregate();
    List<SaleAggregateByMonth> aggregateByMonths = new ArrayList<>();
    for (int i = 0;i < 12;i++) {
      aggregateByMonths.add(new SaleAggregateByMonth(i + 1, salesByMonths.get(i)));
    }
    sales.setTotalSales(totalSales);
    sales.setAggregateByMonths(aggregateByMonths);
    
    return sales;
  }
  
}