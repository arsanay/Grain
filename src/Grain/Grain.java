package Grain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Grain {
    int[] lfsr = new int[80];
    int[] nfsr = new int[80];
    int[] keystream = new int[240];
    int[] plaintext_array = new int[80];
    public int fx;
    int gx;
    int hx;
    int z;
    private final int [] filter = new int[160];
    String binarykeystream="";
    String hasil;
    String result_string;
    
   public Grain(){
       this.lfsr = new int[160];
       this.nfsr = new int[160];
    }
   
   public void init(String iv,String key){
    //   String iv_result = this.hexToBinary(iv);
    //   String key_result = this.hexToBinary(key);
       String iv_result = this.stringToBinary(iv);
       String key_result = this.stringToBinary(key);
       System.out.println("iv : " +iv_result);
       System.out.println("Key : "+key_result);
       //mengisi iv_result kedalam array
       for (int i = 0; i < iv_result.length(); i++) {
           lfsr[i] = Byte.parseByte(String.valueOf(iv_result.charAt(i)));
       }
       //mengisi jika panjang iv tidak sampai 64
       if (iv_result.length()<64) {
           for (int i = iv_result.length(); i < 64; i++) {
               lfsr[i]=0;
           }
           
       }
       //mengisi index 64 sampai 80 dengan nilai 1
       for (int i = 64; i < 80; i++) {
           lfsr[i] =1 ;
       }

       
       //memasukan key_result kedalam array
       for (int j = 0; j < key_result.length(); j++) {
           nfsr[j] = Byte.parseByte(String.valueOf(key_result.charAt(j)));
       }
       if (key_result.length()<80) {
           for (int j = key_result.length(); j < 80; j++) {
               nfsr[j]=0;
           }
       }
    
       
   }
   public void initTest(){
        for (int i = 0; i < 64;i++) {
            lfsr[i] = 0;
        }
	for (int i = 64; i < 80; i++) {
            lfsr[i] = 1;
        }
        for (int i = 0; i < 80; i++) {
            nfsr[i] = 0;
        }
        
        System.out.print("Binary LFSR : ");
        for (int i = 0; i < 80; i++) {
            System.out.print(lfsr[i]);
        }
        System.out.println();
        System.out.print("Binary NFSR : ");
        for (int i = 0; i < 80; i++) {
            System.out.print(nfsr[i]);
        }
       System.out.println("");
   }
  public static String stringToBinary(String string){
       String result = "";
       String tmpStr;
       int tmpInt;
       char[] messChar = string.toCharArray();
       for (int i = 0; i < messChar.length; i++) {
           tmpStr = Integer.toBinaryString(messChar[i]);
        
           tmpInt = tmpStr.length();
          
           if (tmpInt !=8){
               tmpInt = 8-tmpInt;
               if (tmpInt ==8){
                   result += tmpStr;
               } else if (tmpInt > 0 ){
                   for (int j = 0; j < tmpInt; j++) {
                       result += "0";
                      
                   }
               } else { 
                   System.out.println("arguments bits to small ...");
               }
       }            
               result += tmpStr;
             // 
               }
       result += "";

       
       return result;
       
     }

   public String stringBinaryToHex(String string){

       return new BigInteger(string,2).toString();
   }
   public String hexToBinary(String hex){
      String bin = "";
    String binFragment = "";
    int iHex;
    hex = hex.trim();
    hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
   }
   
   public void fx(){
       int[] data = this.lfsr;
       
       this.fx = (byte) (data[62] ^ data [51] ^ data [38] ^ data [23]^ data [13]^ data [0] );
       
     
    
   }
   public int gx(){
       int[] data = this.nfsr;
       int[] data2 = this.lfsr;
        this.gx = (byte)(
                    data2[0] ^ data[62] ^ data[60] 
                   ^data[52] ^ data[45] ^ data[37] 
                   ^data[33] ^ data[28] ^ data[21]
                    ^data[14] ^ data[9] ^ data[0]
                   ^ (data[63] & data[60])^ (data[37] & data[33])
                   ^ (data[15] & data[9]) ^ (data[60] & data[52] & data[45])
                   ^ (data[33] & data[28] & data[21])
                   ^ (data[63] & data[45] & data[28] & data[9])
                   ^ (data[60] & data[52] & data[37] & data[33])
                   ^ (data[63] & data[60] & data[21] & data[15])
                   ^ (data[63] & data[60] & data[52] & data[45] & data[37]) 
                   ^ (data[33] & data[28] & data[21] & data[15] & data[9])
                   ^ (data[52] & data[45] & data[37] & data[33] & data [28] & data[21])
                   );
       return this.gx ;
   }
   
   public int hx(){
       int[] data = this.nfsr;
       int[] data2 = this.lfsr;
       int xor;
       
       int x0 = this.lfsr[3];
       int x1 = this.lfsr[25];
       int x2 = this.lfsr[46];
       int x3 = this.lfsr[64];
       int x4 = this.nfsr[63];
        hx  =  (x1 ^ x4 ^ (x0 & x3) ^ (x2 & x3) ^ (x3 & x4) ^ (x0 & x1 & x2 )
              ^ (x0 & x2 & x3) ^ (x0 & x2 & x4) ^ (x1 & x2 & x4) ^ (x2 & x3 & x4));
   
       return hx;
      
   }
   public int z(){
      
       int z0 = this.nfsr[1];
       int z1 = this.nfsr[2];
       int z2 = this.nfsr[4];
       int z3 = this.nfsr[10];
       int z4 = this.nfsr[31];
       int z5 = this.nfsr[43];
       int z6 = this.nfsr[56];
       
       z = z0 ^ z1 ^ z2 ^ z3 ^ z4 ^ z5 ^ z6;
       return z;
   }
      public void inisialisasikunci(){
         // initTest();
       int  new_nfsr;
       int new_lfsr;
       int new_filter;
     
              for (int i = 0; i < 160; i++) {
              fx();
              gx();
              hx();
              z();
              new_filter = hx ^ z;
              new_nfsr = gx ^ new_filter;             
              new_lfsr = fx ^ new_filter; 
              for (int j = 0; j < 159; j++) {
                  this.lfsr[j] = this.lfsr[j+1];            
              }
              lfsr[79] = new_lfsr;
              for (int k = 0; k < 159; k++) {
                 this.nfsr[k] = this.nfsr[k+1];    
              }
             nfsr[79] = new_nfsr;
          }  
   }
      
     
      
      
  public String keystream(String input){
        
       int  new_nfsr = 0;
       int new_lfsr = 0;
       int new_filter;
       int cipher;
       String temp = stringToBinary(input);
         System.out.println("PlainText = "+temp);
         System.out.print("Keystream = ");
          for (int i = 0; i < temp.length(); i++) {
              fx();
              gx();
              hx();
              z();
              new_filter = hx ^ z;
            keystream[i] = new_filter; 
              System.out.print(keystream[i]);
              for (int j = 0; j < 79; j++) {
                this.lfsr[j] = this.lfsr[j+1];            
           }
             lfsr[79] = fx;
              for (int k = 0; k < 79; k++) {
                  nfsr[k] = nfsr[k+1];    
              }
              nfsr[79] = gx;
          }  
        System.out.println("");
   
        
       //  System.out.println("temp adalah "+temp);
         encrypt(temp);
          return temp;
  }
   
   
   public String encrypt(String input){
      // String result = this.stringToBinary(input);
       String result = input;
       String result_string = "";
       Byte[] result_array = new Byte[result.length()];
       
       Byte[] result_xor_array = new Byte[result.length()];
   
      
      for (int i = 0; i < result_array.length; i++) {
           result_array[i] = Byte.parseByte(String.valueOf(result.charAt(i)));
           result_xor_array[i] = (byte) (this.keystream[i] ^ result_array[i]);
           result_string += result_xor_array[i];
           binarykeystream += keystream[i];
           
       }
      
     // stringBinaryToHex(result_string);
       System.out.println("Cipher Text = "+result_string);
     
      // System.out.println("");
    //   System.out.println("Hasil Enkripsi = \n"+result_string);
      decrypt(binarykeystream,result_string);
    //   hasil = result_string;
     //  hasil();
       //return this.stringBinaryToHex(result);
  //   result_string = result_string;
      return result_string;
      
   }
   
   public String decrypt (String keystream , String cipher){
       
       Byte[] a_array = new Byte[cipher.length()];
       Byte[] b_array = new Byte[keystream.length()];
      
       String plain_binary = "";
       String plain = "";
       Byte[] hasil = new Byte[cipher.length()];
      
       for (int i = 0; i < a_array.length; i++) {
           a_array[i] =Byte.parseByte(String.valueOf(cipher.charAt(i)));
        
       }
      
        for (int i = 0; i < b_array.length; i++) {
           b_array[i] =Byte.parseByte(String.valueOf(keystream.charAt(i)));
           
       }
   
        for (int i = 0; i < cipher.length(); i++) {
            hasil[i] = (byte) (b_array[i] ^ a_array[i]);
          //  System.out.println(a_array[i]+" "+b_array[i]);
          //  System.out.println(hasil[i]);
            plain_binary += hasil[i];
        
       }
        System.out.println("Dekripsi = "+plain_binary);
        System.out.println("");
       for (int i = 0; i < plain_binary.length(); i += 8) {
           int k = Integer.parseInt(plain_binary.substring(i,i+8),2);
           plain += (char)k;   
       }
       return plain;
   }
   
//public String hasilE() {
//        int decimal = Integer.parseInt(hasilE, 2);
//        String hexStr = Integer.toString(decimal, 16);
//        return hasilE;
//    }
}
