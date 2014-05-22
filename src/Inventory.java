public class Inventory {
	Block[] inv;
	
	public Inventory(){
		inv = new Block[3];
		
		for(int i = 0; i < inv.length; i++){
			inv[i] = new Block(0,0,64,64,0);
		}
	}
	
	public void addItem(int type){
		if(!isFull()){
			for(int i = 0; i < inv.length; i++){
				if(inv[i].type == 0){
					inv[i].type = type;
					break;
				}
			}
		}
	}
	
	private boolean isFull(){
		boolean full = true;
		
		for(Block i : inv){
			if(i.type == 0){
				full = false;
			}
		}
		
		return full;
	}
	
	public void printInventory(){
		for(Block i : inv){
			System.out.print(i.type + " ");
		}
		
		System.out.println();
	}
}

