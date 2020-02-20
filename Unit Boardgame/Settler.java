
public class Settler extends Unit{

	
		public Settler(Tile position, double hp, String faction) {
			super(position, hp, 2, faction);
		}
		public void takeAction(Tile a) {
			if(a.equals(super.getPosition())&&a.isCity()==false) {
				a.foundCity();
				a.removeUnit(this);
			}else {
				return;
			}
		}
		public boolean equals(Object a) {
			if(!(a instanceof Settler)) {
				return false;
			}
			return super.equals(a);
		}
	

}
