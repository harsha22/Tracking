package Tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class Tracking {

	private List<TrackingRecord> records;

	public Tracking() {
		records = new ArrayList<TrackingRecord>();
	}

	private void add(TrackingRecord tr) {
		if (records.isEmpty()) {
			records.add(tr);
		}
		records = tr.classify(records);
		this.sort();
	}
	
	private void sort() {
		Collections.sort(records, new Comparator<TrackingRecord>() {
			public int compare(TrackingRecord t1, TrackingRecord t2) {
				return t1.r.getLo() - t2.r.getLo();
			}
		});
	}
	
    private void print(){
    	for(TrackingRecord tr : records){
    		tr.print(); //add print function
    	}
    }
    
	public static void main(String args[]) {

		Tracking t = new Tracking();
		t.add(new TrackingRecord(60,80, 'A', 1));
		t.add(new TrackingRecord(10, 119, 'B', 1));
		t.print();
	}

}

