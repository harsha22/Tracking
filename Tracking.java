package Tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Tracking.Range.Relation;

class Tracking{
	char status_code;
	int transfer_code;
	Range r;
	
	public Tracking(char status_code, int transfer_code, Range obj){
		this.status_code = status_code;
		this.transfer_code = transfer_code;
		this.r = new Range(obj.getLo(),obj.getHi());
	}
	
	List<Tracking> classifyRecord(List<Tracking> records){
		
		List<Tracking> newRecords = new ArrayList<Tracking>();
		for(Tracking t : records){
		
			Relation rl = this.r.classify(t.r);
			if(rl == Relation.SAME){
				t.status_code = this.status_code;
				t.transfer_code = this.transfer_code;
				newRecords.add(t);
			}
			
			else if(rl == Relation.SUPERSET){
				newRecords.add(this);
			}
			
			else if(rl == Relation.SUBSET){
				if(!(this.transfer_code == t.transfer_code) || !(this.status_code == t.status_code)){
					newRecords.add(this);
					newRecords.add(new Tracking(t.status_code,t.transfer_code,new Range(t.r.getLo(),this.r.getLo() - 1)));
					newRecords.add(new Tracking(t.status_code,t.transfer_code,new Range(this.r.getHi() + 1,t.r.getHi())));
				}
			}
			else if(rl == Relation.MOREDISJOINT || rl == Relation.LESSDISJOINT ){
				newRecords.add(this);
				newRecords.add(t);
			}
			else if(rl == Relation.LESSOVERLAP){
				newRecords.add(new Tracking(t.status_code,t.transfer_code,new Range(this.r.getHi() + 1, t.r.getHi())));
				newRecords.add(this);
			}
			else if(rl == Relation.MOREOVERLAP){
				newRecords.add(new Tracking(t.status_code,t.transfer_code,new Range(t.r.getLo(), this.r.getLo() - 1)));
				newRecords.add(this);
			}
		}
		return newRecords;
	}
	
	private static List<Tracking> addToList(List<Tracking> records, Tracking t) {
		if(records.isEmpty()){
			records.add(t);
			return records;
		}
		return(t.classifyRecord(records));
		
	}
	private static void sortList(List<Tracking> records){
		Collections.sort(records, new Comparator<Tracking>() {
	        public int compare(Tracking t1, Tracking t2) {
	            return t1.r.getLo() - t2.r.getLo();
	        }
	     });
	}
	private static void printList(List<Tracking> records){
		for(Tracking t : records){
			System.out.println(t.r.getLo() + " " + t.r.getHi() + " " + t.status_code + " " + t.transfer_code);
		}
	}
	
	public static void main(String[] args) {
		List<Tracking> records = new ArrayList<Tracking>();
		Tracking t = new Tracking('A', 1, new Range(80,90));
		records = addToList(records,t);
		Tracking t2 = new Tracking('A', 2, new Range(70,85));
		records = addToList(records,t2);
	    sortList(records);
	    printList(records);
	}
}	