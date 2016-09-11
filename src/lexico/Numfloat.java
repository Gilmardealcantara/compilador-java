package lexico;

public class Numfloat extends Token{
	public final float value;
	public Numfloat(float value) {
		super(Tag.FLOAT);
		this.value = value;
		
	}
	
	public String toString(){
		return "" + this.value;
	}

}
