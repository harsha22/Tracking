package Tracking;

class TrackingRecord {
	char status_code;
	int transfer_code;
	Range r;

	public TrackingRecord(int lo, int hi, char status_code, int transfer_code) {
		r = new Range(lo, hi);
		this.status_code = status_code;
		this.transfer_code = transfer_code;
	}

	String compare(TrackingRecord t) {
		return this.r.classify(t.r);
	}		

	void print() {	
			System.out.println(this.r.getLo() +" "+ this.r.getHi() +" "+ this.status_code +" "+ this.transfer_code);
	}
}
