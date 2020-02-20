
public class Worker extends Unit{

	
		private int jobs;
		public Worker(Tile position, double hp, String faction) {
			super(position, hp, 2, faction);
			this.jobs = 0;
		}
		public void takeAction(Tile a) {
			if(a.equals(this.getPosition())&&a.isImproved() == false) {
				a.buildImprovement();
				this.jobs++;
				if(jobs==10) {
					a.removeUnit(this);
				}
			}
		}
		public boolean equals(Object a) {
			if(!(a instanceof Worker)) {
				return false;
			}else if(((Worker)a).jobs!=jobs) {
				return false;
			}
			return super.equals(a);
		}
	

}
