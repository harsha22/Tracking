package Tracking;

import java.util.ArrayList;
import java.util.List;

class TrackingRecord {
	char status_code;
	int transfer_code;
	Range r;

	public TrackingRecord(int lo, int hi, char status_code, int transfer_code) {
		r = new Range(lo, hi);
		this.status_code = status_code;
		this.transfer_code = transfer_code;
	}

	List<TrackingRecord> classify(List<TrackingRecord> records) {

		List<TrackingRecord> newRecords = new ArrayList<TrackingRecord>();
		for (TrackingRecord t : records) {

			String rl = this.r.classify(t.r);
			if (rl == "SAME") {
				newRecords.add(this);
			}

			else if (rl == "SUPERSET") {
				newRecords.add(this);
			}

			else if (rl == "SUBSET") {
				if (!(this.transfer_code == t.transfer_code)
						|| !(this.status_code == t.status_code)) {
					newRecords.add(this);
					newRecords.add(new TrackingRecord(t.r.getLo(), this.r.getLo() - 1,t.status_code, t.transfer_code));
					newRecords.add(new TrackingRecord(this.r.getHi() + 1, t.r.getHi(),t.status_code, t.transfer_code));
				}
			} else if (rl == "MOREDISJOINT" || rl == "LESSDISJOINT") {
				newRecords.add(this);
				newRecords.add(t);
			} else if (rl == "LESSOVERLAP") {
				newRecords.add(new TrackingRecord(this.r.getHi() + 1, t.r.getHi(),t.status_code, t.transfer_code));
				newRecords.add(this);
			} else if (rl == "MOREOVERLAP") {
				newRecords.add(new TrackingRecord(t.r.getLo(), this.r.getLo() - 1,t.status_code, t.transfer_code));
				newRecords.add(this);
			}
		}
		return newRecords;
	}

	void print() {	
			System.out.println(this.r.getLo() +" "+ this.r.getHi() +" "+ this.status_code +" "+ this.transfer_code);
	}
}
