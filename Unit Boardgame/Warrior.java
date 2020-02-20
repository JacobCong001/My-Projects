
public class Warrior extends MilitaryUnit{

	
		public Warrior(Tile position, double hp, String faction) {
			super(position, hp, 1, faction, 20.0, 1, 25);
		}
		public boolean equals(Object a) {
			if(!(a instanceof Warrior)) {
				return false;
			}
			if(((Unit)a).getPosition().equals(((Unit)this).getPosition())&&((Unit)a).getHP() == ((Unit)this).getHP()&&((Unit)a).getFaction().equals(((Unit)this).getFaction())) {
				return true;
			}
			return false;
		}
	

}
