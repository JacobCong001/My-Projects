
public abstract class Unit {

	
		
		private Tile position;
		private double hp;
		private int range;
		private String faction;
		public Unit (Tile pos, double health, int ran, String fac) {
			this.position = pos;
			this.hp = health;
			this.range = ran;
			this.faction = fac;
			boolean added = this.position.addUnit(this);
			if(added == false) {
				throw new IllegalArgumentException("The input isn't correct.");
			}
		}
		public final Tile getPosition() {
			return this.position;
		}
		public final double getHP() {
			return this.hp;
		}
		public final String getFaction() {
			return this.faction;
		}
		public boolean moveTo(Tile a) {
			if(Tile.getDistance(this.position, a)>=range+1) {
				return false;
			}
			boolean moved = a.addUnit(this);
			if(moved) {
				this.position.removeUnit(this);
				this.position = a;
				return true;
			}
			return false;
		}
		public void receiveDamage(double dam){
			if(position.isCity()) {
				this.hp -= 0.9*dam;
			}else {
				this.hp -= dam;
			}
			if(this.hp<=0) {
				position.removeUnit(this);
			}
		}
		public abstract void takeAction(Tile a);
		
		public boolean equals(Object a) {
			if(!(a instanceof Unit)) {
				return false;
			}
			if(((Unit)a).getPosition().equals(position)&&((Unit)a).getHP() == hp&&((Unit)a).getFaction().equals(faction)) {
				return true;
			}
			return false;
		}
	

}
