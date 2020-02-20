
public class ListOfUnits {

	
		private Unit[] unitList = new Unit[10];
		private int size;
		public ListOfUnits() {
			
		}
		public int size() {
			int i=1;
			while(i<=unitList.length&&unitList[unitList.length-i]== null) {
				i++;
			}
			return unitList.length-i+1;
		}
		public Unit[] getUnits() {
			return this.unitList;
		}
		public Unit get(int position) {
			if(position<0||position>unitList.length-1) {
				throw new IndexOutOfBoundsException();
			}else {
				return unitList[position];
			}
		}
		public void add(Unit a) {
			if(this.unitList[unitList.length-1]==null) {
				this.unitList[size] = a;
			}else {
				resizeList();
				this.unitList[size] = a;
			}
			size ++;
		}
		private void resizeList() {
			Unit[] temp = new Unit[unitList.length+(unitList.length/2)+1];
			for(int i=0; i<unitList.length; i++) {
				temp[i] = unitList[i];
			}
			unitList = temp;
		}
		public int indexOf(Unit a) {
			int i = 0;
			while(!unitList[i].equals(a)) {
				if(i==size-1) {
					return -1;
				}
				i++;
			}
			return i;
		}
		public boolean remove(Unit a) {
			int i = 0;
			if(size == 0) {
				return false;
			}
			while(i<size&&!(a.equals(unitList[i]))) {
				if(i == size-1) {
					return false;
				}
				i++;
			}
			for(int j = i; j<size-1; j++) {
				unitList[j] = unitList[j+1];
			}
			unitList[size-1] = null;
			size --;
			return true;
		}
		public MilitaryUnit[] getArmy(){
			MilitaryUnit[] temp = new MilitaryUnit[size];
			int j = 0;
			
			for(int i = 0; i<size; i++) {
				if(unitList[i] instanceof MilitaryUnit) {
					temp[j] = (MilitaryUnit) unitList[i];
					j ++;
					
				}
			}
			MilitaryUnit[] toReturn = new MilitaryUnit[j];
			for(int k = 0; k<j; k++) {
				toReturn[k] = temp[k];
			}
			return toReturn;
		}
	

}
