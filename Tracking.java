package Tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Tracking.Range.Relation;

class Tracking {

    
	char status_code;
	int transfer_code;
	Range r;

	public Tracking(char status_code, int transfer_code, Range obj) {
		this.status_code = status_code;
		this.transfer_code = transfer_code;
		this.r = new Range(obj.getLo(), obj.getHi());
	}

	List<Tracking> classifyRecord(List<Tracking> records) {

		List<Tracking> newRecords = new ArrayList<Tracking>();
		for (Tracking t : records) {

			Relation rl = this.r.classify(t.r);
			if (rl == Relation.SAME) {				
				if (!newRecords.contains(this))
					newRecords.add(t);
			}

			else if (rl == Relation.SUPERSET) {
				if (!newRecords.contains(this))
					newRecords.add(this);
			}

			else if (rl == Relation.SUBSET) {
				if (!(this.transfer_code == t.transfer_code) || !(this.status_code == t.status_code)) {
					if (!newRecords.contains(this))
						newRecords.add(this);
					newRecords.add(
							new Tracking(t.status_code, t.transfer_code, new Range(t.r.getLo(), this.r.getLo() - 1)));
					newRecords.add(
							new Tracking(t.status_code, t.transfer_code, new Range(this.r.getHi() + 1, t.r.getHi())));
				}
			} else if (rl == Relation.MOREDISJOINT || rl == Relation.LESSDISJOINT) {
				if (!newRecords.contains(this))
					newRecords.add(this);
				newRecords.add(t);
			} else if (rl == Relation.LESSOVERLAP) {
				newRecords
						.add(new Tracking(t.status_code, t.transfer_code, new Range(this.r.getHi() + 1, t.r.getHi())));
				if (!newRecords.contains(this))
					newRecords.add(this);
			} else if (rl == Relation.MOREOVERLAP) {
				newRecords
						.add(new Tracking(t.status_code, t.transfer_code, new Range(t.r.getLo(), this.r.getLo() - 1)));
				if (!newRecords.contains(this))
					newRecords.add(this);
			}

		}

		return newRecords;
	}

	private static List<Tracking> addRecords(List<Tracking> records, Tracking t) {
		if (records.isEmpty()) {
			records.add(t);
			return records;
		}
		return (t.classifyRecord(records));

	}
        private static List<Tracking> mergeRecords(List<Tracking> records) {
            List<Tracking> newRecords = new ArrayList<>();
            int i = 0;
            newRecords.add(new Tracking( records.get(i).status_code, records.get( i ).transfer_code, 
                        new Range(records.get( i).r.getLo(), records.get( i).r.getHi())) );
            i = 1;
            while (i < records.size()) {
            
                if ( records.get(i).status_code == records.get(i - 1).status_code && records.get(i).transfer_code == records.get(i - 1).transfer_code) {
                   newRecords.get(newRecords.size() - 1 ).r.setHi(records.get(i).r.getHi());
                }
                else {
                    newRecords.add(new Tracking( records.get( i ).status_code, records.get(i).transfer_code, 
                        new Range(records.get( i).r.getLo(), records.get( i).r.getHi())));
                }
                i++;
            }
            return newRecords;
        }
        
	private static void sortRecords(List<Tracking> records) {
		Collections.sort(records, new Comparator<Tracking>() {
			public int compare(Tracking t1, Tracking t2) {
				return t1.r.getLo() - t2.r.getLo();
			}
		});
	}
        

	private static void printRecords(List<Tracking> records) {
		for (Tracking t : records) {
			System.out.println(t.r.getLo() + " " + t.r.getHi() + " " + t.status_code + " " + t.transfer_code);
		}
	}

	public static void main(String[] args) {
		List<Tracking> records = new ArrayList<Tracking>();
		Tracking t = new Tracking('A', 1, new Range(1,10000));
		records = addRecords(records, t);;
		Tracking t3 = new Tracking('C', 2, new Range(12000, 12999));
		records = addRecords(records, t3);
		Tracking t2 = new Tracking('B', 2, new Range(12345,12345));
		records = addRecords(records, t2);
		sortRecords(records);
                records = mergeRecords(records);
                printRecords(records);
	}
}
