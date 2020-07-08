package Element;

import java.util.Random;
public class RRandom extends Random{
	private static final long serialVersionUID=1L;
	int RanAngle;
	public int Ran0To180(){
		return super.nextInt(180);
	}
	public int Ran0To360() {
		return super.nextInt(360);
	}

}
