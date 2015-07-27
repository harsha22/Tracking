package Tracking;

public class Range {

    private int lo;
    private int hi;
    
    public int getLo() {
		return lo;
	}
	
	public void setLo(int lo) {
		this.lo = lo;
	}

	public void setHi(int hi) {
		this.hi = hi;
	}

	public int getHi() {
		return hi;
	}
	
	public Range(int lo, int hi){
    	this.lo = lo;
    	this.hi = hi;
    }
    public boolean contains(int x) {
        return this.lo <= x && x <= this.hi;
    }
    public boolean contains(Range r) {
        return this.lo <= r.lo && r.hi <= this.hi;
    }
    public boolean equals(Range r) {
        return this.lo == r.lo && this.hi == r.hi;
    }
    public boolean isDisjoint(Range r) {
        return this.lo > r.hi || this.hi < r.lo;
    }
    public boolean isOverlapping(Range r) {
        return !(this.isDisjoint(r));
    } 
    public boolean lessThan(Range r) {
        return this.lo < r.lo;
    }
    public enum Relation {
        SUBSET, SUPERSET, LESSOVERLAP, MOREOVERLAP, LESSDISJOINT, MOREDISJOINT, SAME;
    }
    public Relation classify(Range r) {
    	
        if (this.equals(r))
            return Relation.SAME;
        
        if (this.contains(r)) 
            return Relation.SUPERSET;
        
        if (r.contains(this))
            return Relation.SUBSET;
        
        if (this.isDisjoint(r))
            if (this.lo > r.hi)
                return Relation.MOREDISJOINT;
            else
                return Relation.LESSDISJOINT;
                
        if (this.lessThan(r))
            return Relation.LESSOVERLAP;
        	return Relation.MOREOVERLAP;
    }
}