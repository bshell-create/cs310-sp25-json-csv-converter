package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.*;
import java.util.*;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
                     // INSERT YOUR CODE HERE
        // Read CSV data
        CSVReader reader = new CSVReader(new StringReader(csvString));
        List<String[]> csvData = reader.readAll();

        // Create JSON objects
        JsonObject jsonObject = new JsonObject();

        JsonArray prodNums = new JsonArray();
        JsonArray colHeadings = new JsonArray();
        JsonArray data = new JsonArray();

        // Process CSV data if not empty
        if (!csvData.isEmpty()) {
            // First row contains column headings
            String[] headers = csvData.get(0);
            for (String header : headers) {
                colHeadings.add(header);
            }

            // Remaining rows contain data
            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                if (row.length > 0) {
                    prodNums.add(row[0]);
                }

                JsonArray rowData = new JsonArray();
                for (int j = 1; j < row.length; j++) {
                    rowData.add(row[j]);
                }
                data.add(rowData);
            }
        }

        // Add arrays to JSON object
        jsonObject.put("ColHeadings", colHeadings);
        jsonObject.put("ProdNums", prodNums);
        jsonObject.put("Data", data);

        // Convert JSON object to string
        result = jsonObject.toJson();

    } catch (Exception e) {
        e.printStackTrace();
    
    
    }
        return result.trim();
    }
    @SuppressWarnings("unchecked")
public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            // INSERT YOUR CODE HERE
            
            JsonObject jsonObject = Jsoner.deserialize(jsonString, new JsonObject());
            
            List<String[]> csvData = new ArrayList<>();
            
            JsonArray colHeadings = (JsonArray) jsonObject.get("ColHeadings");
            JsonArray prodNums = (JsonArray) jsonObject.get("ProdNums");
            JsonArray data = (JsonArray) jsonObject.get("Data");
        
            String[] header = new String[colHeadings.size()];
                for (int i = 0; i < colHeadings.size(); i++) {
                header[i] = (String) colHeadings.get(i);
                }
            csvData.add(header);
        
            for (int i = 0; i < data.size(); i++) {
                JsonArray rowData = (JsonArray) data.get(i);
                String[] row = new String[rowData.size() + 1];
                row[0] = (String) prodNums.get(i);
                for (int j = 0; j < rowData.size(); j++) {
                    row[j + 1] = rowData.get(j).toString();
                }
                csvData.add(row);
        }
        
        StringBuilder csvFile = new StringBuilder();
        
        String csvString = csvFile.toString().trim();
        
        CSVReader reader = new CSVReader(new StringReader(csvString));
        List<String[]> full = reader.readAll();
        
        if (!full.isEmpty()) {
            Iterator<String[]> iterator = full.iterator();
            String[] line = iterator.next();
                for (String field : line) {
                System.out.println(field);
                }
        } else {
            System.out.println("CSV data is empty.");
        }


        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
        csvWriter.writeAll(csvData);
        result = writer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
}
       
}          
   

