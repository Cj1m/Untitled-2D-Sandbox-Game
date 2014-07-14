public class Inventory {
	Block[] inv;
	private int invCount = 0; //instead of having to iterate all the time!
	
	
	public Inventory(){
		inv = new Block[16];
		
		for(int i = 0; i < inv.length; i++){
			inv[i] = new Block(0,0,16,16,0);
		}
	}
	
	public void addItem(int type){
		if(!isFull()){
			inv[invCount].type = type;
			invCount++;
		}
	}
		
	private boolean isFull(){
		boolean full = false;
		if(invCount == inv.length) full = true;
		return full;
	}
	
	public void printInventory(){
		for(Block i : inv){
			System.out.print(i.type + " ");
		}
		
		System.out.println();
	}
}

