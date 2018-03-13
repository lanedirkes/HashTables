/**
 *  This is the HashObject class. This class is responsible for the creation and maintenance of objects within a hash
 *  table.
 */
public class HashObject<V> {
    V key;              // generic key held by a HashObject
    int numDuplicates;  // number of duplicates of the current object that have attempted to be added to the table
    int numProbes;      // number of times the object's index has been probed
    /**
     * Constructor method for the HashObject object.
     * @param key: generic key
     */
    public HashObject(V key){
        this.key = key;
        this.numDuplicates = 0;
        this.numProbes = 0;
    }

    /**
     * This method compares two HashObjects to see if they contain identical keys
     * @param o: object to be compared to this one
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        // if this is the exact same object
        if(o == this){
            return true;
        }
        // if the parameter object is not a HashObject
        else if(!(o instanceof HashObject)){
            return false;
        }
        // check if the two objects contain the same key
        else{
            HashObject hashObject = (HashObject) o;
            if(this.key.equals(hashObject.key)){
                return true;
            }
        }
        // if none then return false
        return false;
    }

    /**
     * This method returns the HashObject's key, number of duplicates, and number of probes
     * @return information string
     */
    @Override
    public String toString() {
        return key.toString() + " " + numDuplicates + " " + numProbes;
    }

    /**
     * This method returns the HashObject's key
     * @return key
     */
    public V getKey() {
        return key;
    }
}