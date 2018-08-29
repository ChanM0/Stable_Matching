//Michael Chan 
//Comp 482 
// Mo _ We 12:30
import java.io.*;
import java.util.ArrayList;

public class MainCodeArea {

   public MainCodeArea(){
   }

//will read the file and put each line into a String array list
   public void readFile(){
      String line = null;
      ArrayList<String> array = new ArrayList<String>(); 
      try{
         FileReader fileReader = new FileReader("input1.txt");
         BufferedReader bufferedReader = new BufferedReader(fileReader);  
         while( ( line = bufferedReader.readLine() ) != null ){
            System.out.println(line);
            array.add(line);
         }
      }
      catch(FileNotFoundException exFileNotFound){
         System.out.println("input1.txt cant find");
      }
      catch(IOException exIO){
         System.out.println("cantReadFile");
      }
      initalizesHumans(array);
   }

   public int[] stringToIntArray(String arrayPrefString, int size){
      int[] prefrenceArray = new int[size];
      String [] arrOfStr = arrayPrefString.split(" ", 0);
      for(int j = 0; j < size; j++){
         prefrenceArray[j] = Integer.parseInt(arrOfStr[j]);
      }
      return prefrenceArray; 
   } 

   
   public void initalizesHumans(ArrayList<String> ar){
      int n = Integer.parseInt(ar.get(0)); // gets the n, which determines the amount of men and women  
      String prefString = ar.get((ar.size()-1)); //gets the 2n+1 permutation to test to see if the marrige is stable.
      int[] permuationAr = stringToIntArray( prefString , n ); 
      int[] prefArrayM, prefArrayW;
   
      int[][] man = new int[n][(n+1)] , woman = new int[n][(n+1)];
      int[][] wpa = new int[n][n], mpa = new int[n][n]; //initallizes both men and women processed array

      int temp;

      for(int i = 0; i < n ; i++){
         prefArrayM = stringToIntArray( ar.get((i+1)) , n ); 
         prefArrayW = stringToIntArray( ar.get((i+n+1)) , n ); 
         for(int j = 0; j < n ; j++){
            man[i][j] = (prefArrayM[j] - 1);
            woman[i][j] = ( prefArrayW[j] - 1);
            if( ( j+1 ) == (n) ){
               temp = (permuationAr[i] - 1) ;   
               man[i][n] = temp ;
               woman[temp][n] = i;
            }
         }
      }
      for(int i = 0; i < n ; i++){
         for(int j = 0; j < n ; j++){
            temp = woman[i][j];
            wpa[i][temp] = j;
            temp = man[i][j];
            mpa [i][temp] = j ;
         }
      
      }
      stablityCheck(man, woman , n, wpa, mpa );
   }

   //checks stability
   public void stablityCheck(int[][] man, int[][] woman, int n, int [][] wpa, int [][] mpa){
      int wife; // man is married to woman 

      int mPW; // mans perfered woman 
      int wPM; // womans perfered man
      int hMPW; // woman is married to Man 
   
      int wpa1; // mpw ranks current man as
      int wpa2; // wife ranks current man as 
   
      int mpa1; // husband of mans perfered woman ranks mans perfered woman as 
      int mpa2; // man ranks mans perfered woman as

      String output ="\nYes"; // out put
      boolean instabilityFound = false; // boolean for check 
   
      for(int i = 0; i < n; i++ ){// itterate through the men 
         if(instabilityFound == true )break;
         wife = man[i][n]; // man is married to this woman 
         for(int j = 0; j < n; j++ ){// itterate through man's pref list 
            mPW = man[i][j]; // mans perfered woman 
            wPM = woman[mPW][j];
            hMPW = woman[mPW][n];  //mPW is married to this man 
            // row is for woman and col is for man 
            wpa1 = wpa[mPW][i];   // mpw ranks current man as
            wpa2 = wpa[wife][i];  //wife ranks current man as
            // row is for man and col is for woman 
            mpa1 = mpa[hMPW][mPW]; // husband of mans perfered woman ranks mans perfered woman as 
            mpa2 = mpa[i][mPW]; // man ranks mans perfered woman as
            //check if man is married to his perfered woman && if Man's perfered woman Perfered Man is the husband of mans perfered woman 
            if( (wife == mPW) || (hMPW ==  wPM)){
               break;
            }
            //if man ranks mans perfered woman <= to the husband of mans perfered woman ranks Mans Perferred woman 
            //&& if man ranks mans perfered woman <= to the husband of mans perfered woman ranks Mans Perferred woman 
            else if( ( wpa1 <= wpa2 ) &&  (mpa2 <= mpa1) ) {

               output = "\nNo ("+(i+1) + ","+(j+1)+")\n";
               System.out.println();
               instabilityFound = true;
               break;
            }
         }
      }
      if(instabilityFound == true)
      	System.out.println(output);
      else
      	System.out.println(output);
   }
}


         
            