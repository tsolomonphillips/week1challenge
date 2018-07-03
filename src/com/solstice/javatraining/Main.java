package com.solstice.javatraining;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args) throws Exception
    {

        // will parse the json file and insert it into your database

//        File jsonFile = new File("week1-stocks.json").getAbsoluteFile();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try
//        {
//            List<Stock> stockList = objectMapper.readValue(jsonFile, new TypeReference<List<Stock>>(){});
//            Stock stock = new Stock();
//
//            for (int i = 0; i < stockList.size(); i++)
//            {
//                stock.setSymbol(stockList.get(i).getSymbol());
//                stock.setPrice(stockList.get(i).getPrice());
//                stock.setVolume(stockList.get(i).getVolume());
//                stock.setDate(stockList.get(i).getDate());
//
//                StockManager.insert(stock);
//            }
//
//            System.out.println("Data has been successfully inserted");
//
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//            e.getMessage();
//        }

        // Collecting user input

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a date range you would like to search (i.e, 2018-04-12): ");
        String stockDate = scanner.nextLine();

        System.out.print("Enter a stock symbol to lookup: ");
        String stockSymbol = scanner.nextLine();

        // correcting the user input if they put in a lowercase stock symbol
        stockSymbol.toUpperCase();

        System.out.println("\n" + "Highest price " + StockManager.getMaxPrice(Date.valueOf(stockDate), stockSymbol));

        System.out.println("Lowest price " + StockManager.getMinPrice(Date.valueOf(stockDate), stockSymbol));

        System.out.println("Total Volume " + StockManager.getTotalVolume(Date.valueOf(stockDate), stockSymbol));

        System.out.println("Closing price "+ StockManager.getClosingPrice(Date.valueOf(stockDate), stockSymbol));

    }

}
