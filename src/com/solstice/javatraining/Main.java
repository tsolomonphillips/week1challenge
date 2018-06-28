package com.solstice.javatraining;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class Main
{

    public static void main(String[] args) throws SQLException, IOException
    {

        StockManager.displayAllRows();

        File jsonFile = new File("week1-stocks.json").getAbsoluteFile();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonFactory jsonFactory = objectMapper.getFactory();
        JsonParser jParser;

        try
        {
            jParser = jsonFactory.createParser(jsonFile);
            JsonNode node = objectMapper.readTree(jParser);


            //int count = node.get("count").asInt();
            for (int i = 0; i < node.size(); i++ )
            {
                Stock stock = new Stock();
                //System.out.print( "City: " + node.get("list").get(i).get("name").asText() );
                //System.out.println( " , Absolute temperature: " +
                        //node.get("list").get(i).get("main").get("temp").asText() );

                String symbol = node.get(i).get("symbol").toString();
                Double price = node.get(i).get("price").asDouble();
                Integer volume = node.get(i).get("volume").asInt();

                stock.setSymbol(symbol);
                stock.setPrice(price);
                stock.setVolume(volume);

                try
                {
                    StockManager.insert(stock);
                    //System.out.println(node.get(i));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            jParser.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
