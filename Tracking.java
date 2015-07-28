package Tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Tracking {

	static List<TrackingRecord> records;

	public Tracking() {
		records = new ArrayList<TrackingRecord>();
	}

	private void add(TrackingRecord tr) {
		if (records.isEmpty()) {
			records.add(tr);
		}
		classify(tr);
		sort();
		merge();
	}

	private void classify(TrackingRecord tr) {

		List<TrackingRecord> newRecords = new ArrayList<TrackingRecord>();

		for (TrackingRecord t : records) {

			String rl = tr.compare(t);
			if (rl == "SAME" || rl == "SUPERSET") {
				if (!newRecords.contains(tr))
					newRecords.add(tr);
			} else if (rl == "SUBSET") {
				if (!(tr.transfer_code == t.transfer_code)
						|| !(tr.status_code == t.status_code)) {
					if (!newRecords.contains(tr))
						newRecords.add(tr);
					newRecords.add(new TrackingRecord(t.r.getLo(),
							tr.r.getLo() - 1, t.status_code, t.transfer_code));
					newRecords.add(new TrackingRecord(tr.r.getHi() + 1, t.r
							.getHi(), t.status_code, t.transfer_code));
				}
			} else if (rl == "MOREDISJOINT" || rl == "LESSDISJOINT") {
				if (!newRecords.contains(tr))
					newRecords.add(tr);
				newRecords.add(t);
			} else if (rl == "LESSOVERLAP") {
				newRecords.add(new TrackingRecord(tr.r.getHi() + 1,
						t.r.getHi(), t.status_code, t.transfer_code));
				if (!newRecords.contains(tr))
					newRecords.add(tr);
			} else if (rl == "MOREOVERLAP") {
				newRecords.add(new TrackingRecord(t.r.getLo(),
						tr.r.getLo() - 1, t.status_code, t.transfer_code));
				if (!newRecords.contains(tr))
					newRecords.add(tr);
			}
		}
		records = newRecords;
	}

	private void sort() {
		Collections.sort(records, new Comparator<TrackingRecord>() {
			public int compare(TrackingRecord t1, TrackingRecord t2) {
				return t1.r.getLo() - t2.r.getLo();
			}
		});
	}

	private static void merge() {
		List<TrackingRecord> newRecords = new ArrayList<TrackingRecord>();
		int i = 0;
		newRecords.add(new TrackingRecord(records.get(i).r.getLo(), records
				.get(i).r.getHi(), records.get(i).status_code,
				records.get(i).transfer_code));
		i = 1;
		while (i < records.size()) {

			if (records.get(i).status_code == records.get(i - 1).status_code
					&& records.get(i).transfer_code == records.get(i - 1).transfer_code) {
				newRecords.get(newRecords.size() - 1).r.setHi(records.get(i).r
						.getHi());
			} else {
				newRecords.add(new TrackingRecord(records.get(i).r.getLo(),
						records.get(i).r.getHi(), records.get(i).status_code,
						records.get(i).transfer_code));
			}
			i++;
		}
		records = newRecords;
	}

	private void print() {
		for (TrackingRecord tr : records) {
			tr.print();
		}
	}

	public static void main(String args[]) {

		Tracking t = new Tracking();
		t.add(new TrackingRecord(5, 6, 'A', 1));
		t.add(new TrackingRecord(8, 9, 'A', 1));
		t.print();
	}

}
