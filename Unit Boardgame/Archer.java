
public class Archer extends MilitaryUnit{

	
		private int arrows;
		public Archer(Tile position, double hp, String faction) {
			super(position, hp, 2, faction, 15.0, 2, 0);
			this.arrows = 5;
		}
		public void takeAction(Tile a) {
			if(arrows == 0) {
				arrows = 5;
				return;
			}else {
				arrows --;
				super.takeAction(a);
			}
		}
		public boolean equals(Object a) {
			if(!(a instanceof Archer)) {
				return false;
			}
			if(((Unit)a).getPosition().equals(((Unit)this).getPosition())&&((Unit)a).getHP() == ((Unit)this).getHP()&&((Unit)a).getFaction().equals(((Unit)this).getFaction())&&((Archer)a).arrows==arrows) {
				return true;
			}
			return false;
		}
	

}
