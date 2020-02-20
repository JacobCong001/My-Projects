
public abstract class MilitaryUnit extends Unit {


		private double atkDamage;
		private int atkRange;
		private int armor;
		public MilitaryUnit(Tile position, double hp, int range, String faction, double atkDamage, int atkRange, int armor) {
			super(position, hp, range, faction);
			this.atkDamage = atkDamage;
			this.atkRange = atkRange;
			this.armor = armor;
		}
		public void takeAction(Tile a) {
			
			Unit weakEnemy = a.selectWeakEnemy(super.getFaction());
			if(Tile.getDistance(super.getPosition(), a)>=atkRange+1||weakEnemy == null) {
				return;
			}else if(super.getPosition().isImproved()) {
				weakEnemy.receiveDamage(1.05*atkDamage);
			}else {
				weakEnemy.receiveDamage(atkDamage);
			}
		}
		public void receiveDamage(double dam) {
			double modifiedDam = 100*dam/(100+armor);
			super.receiveDamage(modifiedDam);
		}
	

}
