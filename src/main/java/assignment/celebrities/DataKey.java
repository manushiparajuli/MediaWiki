package assignment.celebrities;

public class DataKey {
	private String celebrityName;
	private int celebrityCountry;

	// default constructor
	public DataKey() {
		this(null, 0);
	}
        
	public DataKey(String name, int location) {
		celebrityName = name;
		celebrityCountry = location;
	}

	public String getCelebrityName() {
		return celebrityName;
	}

	public int getCelebrityCountry() {
		return celebrityCountry;
	}

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
            if (this.getCelebrityCountry() == k.getCelebrityCountry()) {
                int compare = this.celebrityName.compareTo(k.getCelebrityName());
                if (compare == 0){
                     return 0;
                } 
                else if (compare < 0) {
                    return -1;
                }
            }
            else if(this.getCelebrityCountry() < k.getCelebrityCountry()){
                    return -1;
            }
            return 1;
            
	}
}
