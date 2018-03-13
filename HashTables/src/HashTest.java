// Importing necessary libraries
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

/**
 * This is the driver class for the Hash Table project. It can test both linear and double hashing tables with the
 * following data sources:
 * 1. random integer
 * 2. current time in ms as a long
 * 3. word out of the word-list file
 */
public class HashTest {
    /**
     * Main test method
     * @param args: user arguments from the command line
     */
    public static void main(String[] args){
        Random rand;            // random number generator
        BufferedReader input;   // buffered reader for the word-list file
        int linearT = 0;        // int to set a hash table to linear hashing
        int doubleT = 1;        // int to set a hash table to double hashing
        int debugLevel = 0;     // default debug level
        boolean insertSuccess = true;   // boolean used to determine if table is full
        int dataSource = Integer.parseInt(args[0]); // user selected data source
        double loadFactor = Double.parseDouble(args[1]);    // user selected table load factor
        // if the user selected a debug level
        if(args.length == 3){
            debugLevel = Integer.parseInt(args[2]); // user selected debug level
        }
        // create linear and double hashing tables
        HashTable linearTable = new HashTable(linearT,loadFactor);
        HashTable doubleTable = new HashTable(doubleT,loadFactor);
        switch(dataSource){
            // integer key test
            case 1:
                rand = new Random();
                HashObject intObj = new HashObject(rand.nextInt());
                System.out.println("****** Using Linear Hashing ******");
                // while the table has not reached the load factor
                while(insertSuccess){
                    insertSuccess = linearTable.insert(intObj);
                    intObj = new HashObject(rand.nextInt());
                }
                linearTable.printStats();
                if(debugLevel == 1){
                    linearTable.debugLvlOneOutput("linear-dump.txt");
                }
                // resetting the boolean for the next while loop
                insertSuccess = true;
                System.out.println("****** Using Double Hashing ******");
                // while the table has not reached the load factor
                while(insertSuccess){
                    insertSuccess = doubleTable.insert(intObj);
                    intObj = new HashObject(rand.nextInt());
                }
                doubleTable.printStats();
                if(debugLevel == 1){
                    doubleTable.debugLvlOneOutput("double-dump.txt");
                }
                break;
            // long key test
            case 2:
                HashObject longObj = new HashObject(System.currentTimeMillis());
                System.out.println("****** Using Linear Hashing ******");
                // while the table has not reached the load factor
                while(insertSuccess){
                    insertSuccess = linearTable.insert(longObj);
                    longObj = new HashObject(System.currentTimeMillis());
                }
                linearTable.printStats();
                if(debugLevel == 1){
                    linearTable.debugLvlOneOutput("linear-dump.txt");
                }
                // resetting the boolean for the next while loop
                insertSuccess = true;
                System.out.println("****** Using Double Hashing ******");
                // while the table has not reached the load factor
                while(insertSuccess){
                    insertSuccess = doubleTable.insert(longObj);
                    longObj = new HashObject(System.currentTimeMillis());
                }
                doubleTable.printStats();
                if(debugLevel == 1){
                    doubleTable.debugLvlOneOutput("double-dump.txt");
                }
                break;
            // string key test
            case 3:
                try {
                    File textFile = new File("word-list");
                    // get a buffered reader for the word-list file
                    input = processText(textFile);
                    HashObject stringObj = new HashObject(input.readLine());
                    System.out.println("****** Using Linear Hashing ******");
                    // while the table has not reached the load factor and have not reached the end of the word-list file
                    while (insertSuccess && stringObj.getKey() != null) {
                        insertSuccess = linearTable.insert(stringObj);
                        stringObj = new HashObject(input.readLine());
                    }
                    linearTable.printStats();
                    if(debugLevel == 1){
                        linearTable.debugLvlOneOutput("linear-dump.txt");
                    }
                    // resetting the boolean for the next while loop
                    insertSuccess = true;
                    // closing the buffered reader, so that the next loop can start at the beginning of the word-list file
                    input.close();
                    // get a buffered reader for the word-list file
                    input = processText(textFile);
                    stringObj = new HashObject(input.readLine());
                    System.out.println("****** Using Double Hashing ******");
                    // while the table has not reached the load factor and have not reached the end of the word-list file
                    while (insertSuccess && stringObj.getKey() != null) {
                        insertSuccess = doubleTable.insert(stringObj);
                        stringObj = new HashObject(input.readLine());
                    }
                    doubleTable.printStats();
                    if(debugLevel == 1){
                        doubleTable.debugLvlOneOutput("double-dump.txt");
                    }
                }
                catch (IOException e){
                    System.out.println("There was an IOException");
                }
                break;
            default:
                System.out.println("User selected a data source that does not exist.\n");
                break;
        }
    }

    /**
     * Method for producing a buffered reader for a text file
     * @param fileName: name of the file to be read in
     * @return input: buffered reader
     */
    public static BufferedReader processText(File fileName) {
        BufferedReader input = null;
        // Buffered File Reader
        try {
            input = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e){
            System.out.println("The file name entered could not be found.");
        }
        return input;
    }
}
