// importing the necessary dependencies
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is responsible for the creation and maintanence of hash tables.
 * @param <V>
 */
public class HashTable<V> {
    HashObject[] table; // array of hash objects
    int n;              // number of keys we are inserting into the hash table
    int m = 95791;      // size of the hash table
    int mLow = 95789;   // size of the hash table minus 2, used for double hash function
    int hashType = 0;   // int used to switch hash type between linear and double
    int collisions = 0; // number of collisions that have occurred
    int newInserts = 0; // number of new insertions into the table
    long totalInserts = 0; // number of total insertions
    double loadFactor = 0.0; // stores the load factor

    /**
     * Constructor method for the HashTable class
     * @param hashSelect: 0 for linear hash, 1 for double hash
     * @param loadFactor: load factor for the table as a percentage
     */
    public HashTable(int hashSelect, double loadFactor){
        double temp = m * loadFactor;
        n = (int) temp; // number of items to be added to the table
        // double hash, else defaults to linear hash
        if(hashSelect == 1){
            this.hashType = hashSelect;
        }
        // initialize the table
        table = new HashObject[m];
        this.loadFactor = loadFactor;
    }

    /**
     * Main hash method, determines type of hash to be done and returns the index the object should be placed at.
     * @param o: object to be hashed
     * @param i: how many times this object has been hashed already
     * @return index: index the object should be placed at within the table
     */
    public int hash(HashObject<V> o, int i) {
        int index;
        // double hash the object
        if(this.hashType == 1){
            index = doubleHash(o.getKey(), i);
        }
        // linear hash the object
        else{
            index = linearHash(o.getKey(), i);
        }
        return index;
    }

    /**
     * This method executes a linear hash on a key,
     * @param key: key to be hashed
     * @param i: how many times this key has been hashed already
     * @return index: index the object should be placed at within the table
     */
    public int linearHash(V key, int i){
        int newKey = Math.abs(key.hashCode());
        double index = ((newKey % m) + i) % m;
        return (int)index;
    }

    /**
     * This method executes a double hash on a key,
     * @param key: key to be hashed
     * @param i: how many times this key has been hashed already
     * @return index: index the object should be placed at within the table
     */
    public int doubleHash(V key, int i){
        int newKey = Math.abs(key.hashCode());
        double index = ((newKey % m) + (i * (newKey % mLow))) % m;
        return (int)index;
    }

    /**
     * Main insert method, will continue to attempt to insert and object into the table until successful or the table is
     * determined to be full.
     * @param o: object to be placed into the table
     * @return true if there is still room in the table, false if full
     */
    public boolean insert(HashObject o){
        int i = 0;
        boolean stop = false; // boolean used to determine if the loop should stop
        while(i < m && stop == false && newInserts <= n){
            stop = insertObject(o, i);
            i++;
        }
        // if the table is not full and we have not reached the load factor
        if(i < m && newInserts < n){
            return true;
        }
        return false;
    }

    /**
     * This method attempts to insert an object into the table. Tells the calling method whether or not it was
     * successful.
     * @param o: object to be inserted
     * @param i: how many attempts to insert this object
     * @return true if inserted/duplicate, false if not inserted
     */
    public boolean insertObject(HashObject o, int i){
        // get index from hash
        int index = hash(o,i);
        totalInserts++;
        // if the index is invalid
        if(index >= m){
            System.out.println("Hash failed, index was out of bounds\n");
            return false;
        }
        // if the index is empty, insert object
        else if(table[index] == null){
            table[index] = o;
            table[index].numProbes++;
            newInserts++;
            return true;
        }
        // else, the index is not empty
        else{
            // if the objects are the same, count as a duplicate
            if(o.equals(table[index])){
                table[index].numDuplicates++;
                table[index].numProbes = 1;
                collisions++;
                return true;
            }
            // else, count as only a probe
            else{
                table[index].numProbes++;
                collisions++;
                return false;
            }
        }
    }

    /**
     * This method prints the statistics for the hash table
     */
    public void printStats(){
        long totalDuplicates = getTotalDuplicates();
        double avgProbes = this.getAvgProbes();
        System.out.println("Input " + totalInserts + " elements, of which " + totalDuplicates + " are duplicates.");
        System.out.println("Number of items placed within the table " + newInserts);
        System.out.println("Load Factor = " + loadFactor + ", Avg. no. of probes was " + avgProbes + "\n");
    }

    /**
     * Counts the total number of duplicates within the hash table
     * @return sum: number of total duplicates
     */
    public long getTotalDuplicates(){
        int i = 0;
        long sum = 0;
        for(i = 0; i <= n; i++){
            if(table[i] != null) {
                sum += (long)table[i].numDuplicates;
            }
        }
        return sum;
    }

    /**
     * Calculates the average number of probes per object in the hash table
     * @return average number of probes
     */
    public double getAvgProbes(){
        int i = 0;
        int totalProbes = 0;
        for(i=0; i <= n; i++){
            if(table[i] != null) {
                totalProbes += table[i].numProbes;
            }
        }
        return (double)totalProbes/n;
    }

    /**
     * Outputs all objects' keys, number of duplicates, and number of probes to a text file.
     * @param fileName: name of the output file
     */
    public void debugLvlOneOutput(String fileName){
        int i = 0;
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for(i=0; i < m; i++){
                if(table[i] != null) {
                    writer.println("table[" + i + "}:\t" + table[i].toString());
                }
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("There was an IO exception.");
        }
    }
}