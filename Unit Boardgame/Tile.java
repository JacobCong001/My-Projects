
public class Tile {

	
		private int xCoord;
		private int yCoord;
		private boolean cityBuilt;
		private boolean getImproved;
		private ListOfUnits tileUnits = new ListOfUnits();
		public Tile(int x, int y) {
			this.xCoord = x;
			this.yCoord = y;
			this.cityBuilt = false;
			this.getImproved = false;
		}
		public int getX() {
			return xCoord;
		}
		public int getY() {
			return yCoord;
		}
		public boolean isCity() {
			return cityBuilt;
		}
		public boolean isImproved() {
			return getImproved;
		}
		public void foundCity() {
			this.cityBuilt = true;
		}
		public void buildImprovement() {
			this.getImproved = true;
		}
		public boolean addUnit(Unit a) {
			if(a instanceof MilitaryUnit) {
				for(int i = 0; i<tileUnits.size(); i++) {
					if(tileUnits.get(i) instanceof MilitaryUnit&&!(tileUnits.get(i).getFaction().equals(a.getFaction()))) {
						return false;
					}
				}
				
			}
			this.tileUnits.add(a);
			return true;
		}
		public boolean removeUnit(Unit a) {
			boolean removed = tileUnits.remove(a);
			return removed;
		}
		public Unit selectWeakEnemy(String fac) {
			int i = 0;
			int j = 1;
			int enemyNum = 0;
			int s = tileUnits.size();
			int toReturn = 0;
			double lowestHP = 0.0;
			while(i<s) {
				if(!(tileUnits.get(i).getFaction().equals(fac))) {
					enemyNum ++;
					lowestHP = tileUnits.get(i).getHP();
				}
				i++;
			}
			if(enemyNum==0) {
				return null;
			}
			while(s-j>=0) {
				if(!tileUnits.get(s-j).getFaction().equals(fac)&&lowestHP>=tileUnits.get(s-j).getHP()) {
					lowestHP = tileUnits.get(s-j).getHP();
					toReturn = s-j;
				}
				j++;
			}
			return tileUnits.get(toReturn);
		}
		public static double getDistance(Tile a, Tile b) {
			double distance2 = (a.xCoord-b.xCoord)*(a.xCoord-b.xCoord)+(a.yCoord-b.yCoord)*(a.yCoord-b.yCoord);
			double distance = Math.sqrt(distance2);
			return distance;
		}
	

}
