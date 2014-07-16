public class Inventory {
	Block[] inv;
	private int invCount = 1;
	
	
	public Inventory(){
		inv = new Block[16];
		
		for(int i = 0; i < inv.length; i++){
			inv[i] = new Block(0,0,16,16,0);
		}
	}
	
	public void addItem(int type){ 
		for(int i = 0; i < invCount; i++){
			if(inv[i].type == 0){
				inv[i].type = type;
				if(invCount < inv.length)invCount++;
				break;
			}
		}
	}
	
	public void removeItem(int id){
		inv[id].type = 0;
	}
	
	
	
	public void printInventory(){
		for(Block i : inv){
			System.out.print(i.type + " ");
		}
		
		System.out.println();
	}
}

