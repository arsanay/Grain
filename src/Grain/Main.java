
package Grain;
public class Main {
private final Grain grain;


public Main(){
    this.grain = new Grain();

}
 public void run(){
     this.grain.init("sekripsi", "abcdefghij");
     this.grain.inisialisasikunci();
     String keystream = this.grain.keystream("paslon1");
     String cipher = this.grain.encrypt(keystream);

 }
    public static void main(String[] args) {
    
        for (int i = 0; i < 1; i++) {
            
        
             long start;
    long end;
 
        start = System.nanoTime();//menghitung proses dalam detik
 
        Main self = new Main();
        self.run();

        end = System.nanoTime();
        System.out.println("\nwaktu yang diperlukan selama proses : " + (end - start)+ " nano detik");

        }
 
        } 
    
}
