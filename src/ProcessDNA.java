/**
 * The ProcessDNA program makes use and tests a multitude of methods designed to process DNA.
 * The accuracy of each proccessing method is tested and their results evaluated to increase confidence in
 * the processes
 * 
 * @author (TSHIRELETSO MOHLAKWANE) 
 *  DATE: 14 Febuary 2024
 */
import edu.duke.*;
import java.io.File;
import java.util.*;

public class ProcessDNA {
    public int FindStopCodon (String dnaStr, int StartIndex, String StopCodon) {
        
        int currentIndex = dnaStr.indexOf(StopCodon,StartIndex + 3);
        
        while(currentIndex != -1) {
            int diff = currentIndex-StartIndex;
            //if statement
        if (diff % 3 ==0){
        return currentIndex ; 
        }//if statement end
        else{ 
            currentIndex = dnaStr.indexOf(StopCodon, currentIndex +1);
        }// Else statement END
        }//while loop end
         return -1;
    }
 
    /**Finds the location of the Start Codon and one of the three stop Codons which enclose a strand of DNA
     * It copies the original String from the start Codon index to the first Stop codon located 
     * returns the DNA as a string 
     * @param (String, int)*/
    public String FindGene (String dnaStr, int where) {
        int StartIndex = dnaStr.indexOf("atg",where);
        if (StartIndex == -1){
            return "";
        }
        
        int TAAIndex = FindStopCodon(dnaStr,StartIndex,"taa");
        int TAGIndex = FindStopCodon(dnaStr,StartIndex,"tag");
        int TGAIndex = FindStopCodon(dnaStr,StartIndex,"tga");
        int minIndex = 0;
        
        if (TAAIndex == -1 || TGAIndex != -1 && TGAIndex < TAAIndex) {
            minIndex = TGAIndex;
        }//If statement end
        else {
            minIndex = TAAIndex;
            
        }//else statement end
        if (minIndex == -1 || TAGIndex != -1 && TAGIndex < minIndex) {
           minIndex = TAGIndex; 
        }//If statement end
        
        if (minIndex== -1) {
        return"";    
        }//if statement end
        return dnaStr.substring(StartIndex ,minIndex+3);
    
    }
    
    /**The cgRatio method calculates the ratio of the letters C and G versus the length of a 
     * String of DNA. 
     * 
     * @param dnaStr
     * @return double
    */
    public double cgRatio (String dnaStr) {
        double CGRatio = 0;
        int StartIndex = 0;
        double iCountC = 0;
        double iCountG = 0;
        String currentGene = FindGene(dnaStr,StartIndex);
        int Cindex = currentGene.indexOf("c");
        int Gindex = currentGene.indexOf("g");
        while (Cindex != -1 && Gindex != -1) {
            
            int iLength = currentGene.length();
            if (currentGene.isEmpty()) {
                break;
            }//if statement end
            
           
            if (Cindex != -1 ) {
                iCountC = iCountC + 1;
                Cindex = currentGene.indexOf("c",Cindex + 1);
            }
            
            if(Gindex != -1 ) {
                iCountG=iCountG + 1;
                Gindex = currentGene.indexOf("g",Gindex + 1);
            }
            CGRatio= (iCountG + iCountC)/currentGene.length(); 
           
        }//while loop end
        
        return CGRatio;
    }
    
    public void TestCGRatio () {
        String dnaStr = "atgccgtaa";
        double Ratio =  cgRatio(dnaStr);
        System.out.println("CG Ratio : " + Ratio);
    }
    
    public int countCTG (String dnaStr) {
        int iCountCTG = 0;
        int StartIndex=0;
        String currentGene = FindGene(dnaStr,StartIndex);
        int CTGindex = currentGene.indexOf("ctg");
        
        while(CTGindex != -1) {
            if (currentGene.isEmpty()) {
                break;
            }//if statement end
            
            if (CTGindex != -1 ) {
                iCountCTG = iCountCTG + 1;
                CTGindex = currentGene.indexOf("ctg",CTGindex + 1);
            }
        }//While loop end
        return iCountCTG;
    }
    
     public void TestCTG () {
        String dnaStr = "atgctgctgctgtaa";
        int Count =  countCTG(dnaStr);
        System.out.println("CTG COUNT : " + Count);
    
    }
    /**The process gene method iterates through a large group of DNA from a file.
     * Depending on whether the String of DNA meets certain criteria
     * The resulting Strand of DNA will be printed out.
     * 
     * @return void
     */
    public void ProcessGenes (StorageResource sr) {
        //trial
        FileResource fr = new FileResource("brca1line.fa");
        String dnaStr = fr.asString();
        //CALL getAllGenes
        sr = getAllGenes(dnaStr);
        
        int StartIndex = 0;
        int iCountLength = 0;
        //Call CGratio
        int iCountCG = 0;
        int iCount60 = 0;
    
           
        for (String s : sr.data()) {
            String currentGene= FindGene(dnaStr,StartIndex);
            double CGRATIO = cgRatio(dnaStr);
            
            if (currentGene.isEmpty()) {
             break;  
            }//if statement end
            
            if (currentGene.length() > 9) {
                System.out.println(currentGene);
                iCountLength = iCountLength + 1;
                System.out.println("genes longer than 9 : " + iCountLength);
            }//if statement end
            
            if(CGRATIO > 0.35) {
             System.out.println(currentGene);
             iCountCG = iCountCG + 1;
             System.out.println("genes with CGRATIO > 0.35 : " + iCountCG);
            }//If statement end
            
            if(currentGene.length()> 60) {
                iCount60 = iCount60 + 1;
                System.out.println("Gene longer than 60 : " + currentGene);
                
                
            }//if statement end
        }//while loop end
    }


    /**The test method of the ProcessGenes() */
    public void TestProcessGene(String dnaStr) {
        StorageResource sr = new StorageResource();
    
        ProcessGenes(sr);
    }
    
    /** Locates multiple genes and prints them out until no other genes can be found 
     * @return void
    */
    public void PrintAllGenes (String dnaStr) {
        int StartIndex = 0;
        
        while (true) {
           String currentGene = FindGene(dnaStr,StartIndex);
           
           if (currentGene.isEmpty()) {
               break;
               
           }//if statement end
           System.out.println(currentGene);
           StartIndex = dnaStr.indexOf(currentGene,StartIndex) + currentGene.length();
        }//While loop end
        
        
        }
        
        public void testOn (String dnaStr) {  
        StorageResource genes= getAllGenes(dnaStr);
    
        for(String g : genes.data()){
            System.out.println(g);
        }
        
    }
    
    /** The test method for the findGene() method. Takes a file with an amount of DNA string
     * that would take a large amount of time to find the DNA of and finds the DNA.
     * Prints out the resulting DNA found
     */
    public void testFindGene() {
        FileResource fr = new FileResource("brca1line.fa");
        String dnaStr = fr.asString();
        String Visa = dnaStr;
        int StartIndex =0;
        String DNA = FindGene(Visa,StartIndex);
        System.out.println(DNA);
    }
    public void Test() {
        FileResource fr = new FileResource("brca1line.fa");
        String dnaStr = fr.asString();
        testOn(dnaStr);
    }
    
    public StorageResource getAllGenes (String dnaStr) {
       StorageResource geneList = new StorageResource();
       
       int StartIndex= 0;
       
       
         while (true) {
           String currentGene = FindGene(dnaStr,StartIndex);
           
           if (currentGene.isEmpty()) {
               break;
               
           }//if statement end
           geneList.add(currentGene);
           StartIndex = dnaStr.indexOf(currentGene,StartIndex) + currentGene.length();
        }//While loop end
       
       
        return geneList; 
     }

     /** The method testFindStopCodon is used too examine the accuracy of the FindStopCodon method 
      * If there is an issue a pre-written error message will be displayed and the test will be concluded.     
      */
    
     public void testFindStopCodon () {
        FileResource Tfile = new FileResource();
        
        String dnaStr = "xxxyyyzzztaaxxxyyyzzztaaxx";
        int dex = FindStopCodon(dnaStr,0,"taa");
        
        if (dex !=9) System.out.println("Error on line 9");
        dex = FindStopCodon(dnaStr,9,"taa");
        
        if (dex !=21) System.out.println("Error on line 9");
        dex = FindStopCodon(dnaStr,1,"taa");
        
        if (dex !=-1) System.out.println("Error on line 26");
        dex = FindStopCodon(dnaStr,0,"tag");
        
        if (dex !=-1) System.out.println("Error on line 26");
        
        System.out.println("All tests are finished");
        

    }

    public static void main(String[] args) {
        ProcessDNA dna = new ProcessDNA();
        StorageResource sr = new StorageResource();
        String dnaStr = "";
    
        dna.TestProcessGene(dnaStr);
        System.out.println();
        System.out.println("Genes have been processed");

        
        System.out.println();
         dna.TestCGRatio();


        dna.TestCTG();

    }

}
