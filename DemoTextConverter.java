/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DeScriptor;

import java.io.IOException;

/**
 *
 * @author kateromanova
 */
public class DemoTextConverter  {
    public static void main(String[] args) throws IOException{
        
        
        TextConverter tc = new TextConverter();
        for(String arg :args){
            try{
                tc.processFile(arg);
            } catch(IOException ex){
                System.out.println("Error reading file " + arg + ":");
                System.out.println(ex.getMessage());
                System.out.println("Finishing");
                return;       
            }
        }
        
        
    }
}

