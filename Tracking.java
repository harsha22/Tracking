package Tracking;

import java.util.ArrayList;
import java.util.List;

import Tracking.Range.Relation;

class Tracking{
	char status_code;
	int transfer_code;
	Range r;
	
	public Tracking(char status_code, int transfer_code, Range obj){
		
	}
	
	void classifyRecord(List<Tracking> records){
		
		for(Tracking t: records){
		if(this.r.classify(t.r) == Relation.SAME){
			t.status_code = this.status_code;
			t.transfer_code = this.transfer_code;
		}
			
		
		}
	}
	void merge(){
		
	}
	
	private static void addToList(List<Tracking> records, Tracking t) {
		if(records.isEmpty()){
			records.add(t);
			return;
		}
		t.classifyRecord(records);
		
		
	}
	public static void main(String[] args) {
		List<Tracking> records = new ArrayList<Tracking>();
		Tracking t = new Tracking('A', 1, new Range(1,100000));
		addToList(records,t);
		Tracking t2 = new Tracking('A', 1, new Range(12345,12345));
		addToList(records,t2);
		
	}
}	