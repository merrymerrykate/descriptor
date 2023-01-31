/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DeScriptor;

import java.io.*;
import java.io.IOException;
import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.CYRILLIC;
import static java.lang.Character.UnicodeBlock.of;
import static java.lang.Character.isDigit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kateromanova
 */
public class TextConverter {
    
    
    private final Map<String, Integer> words = new HashMap<>();
    
        private void print(String s) throws IOException{
            
               String[] row = parseLine(s, ',') ;
               
               for(int i =0; i<row.length; i++)
               {
                   if(row[i].startsWith("\""))
                   {
                       System.out.print(row[i].replace("\"", ""));
                   } else 
                   {
                       if(words.containsKey(row[i]))
                       System.out.print(words.get(row[i]));
                       else throw new IOException("Unknown variable") ;
                   } 
               
            } 
               System.out.println();
        } 
    
        private static String[] parseLine(String s, char sep) {
        List<String> res = new ArrayList<>();
        int p = 0;
        while(p<s.length()){
            int st = p;
            String v;
            if(s.charAt(p) == '"'){
               p++;
               p = s.indexOf('"', p);
               while(p<(s.length() - 1) && s.charAt(p + 1) == '"'){
                   p += 2;
                   p = s.indexOf('"', p);
               }
               p++;
               v = s.substring(st, p).trim();
               p +=2;
            } else {
                p = s.indexOf(sep, p);
                if(p == -1)
                    p = s.length();
                v = s.substring(st, p).trim();
                if(v.length() == 0)
                    v = null;
                p++;
            }
            res.add(v);
        }
        return res.toArray(new String[0]);
    }
    
    public int getName(String s, int p){

 
                    //  считываем переменую
                    while(p < s.length() 
                            && (Character.isLetter(s.charAt(p)) || s.charAt(p) == '_' || isDigit(s.charAt(p)))){
                        p++;
                    }

                    
        return p;
    }
    
    public void setHandler(String s, int p){
       
                
                while(p < s.length()) {
                    
                    while(p < s.length() && s.charAt(p) != '$')
                        p++;
                    
                    
                    if (p == s.length()){
                        System.out.println("Ошибка при обозначении переменной");
                        break;
                    }
             
                    
                    int st = p;
                    //  считываем переменую
                    char c = s.charAt(p);
                    boolean b = Character.isLetter(s.charAt(p + 1));
                    p = getName(s, p + 1);
                    String setVar = s.substring(st, p);
                    
                    if (!setVar.isEmpty()){
                        words.put(setVar, 0);
                    }
                    else{
                        System.out.println("Ошибка при обозначении переменной");
                        break;
                    }
                    // считываем =
                    
                    while(p < s.length() && s.charAt(p) != '=')
                        p++;
                    
                    
                    
                    char sign = '+';
                    
                    while(p < s.length()){
                        
                        if (s.charAt(p) == '-' || s.charAt(p) == '+'){
                            sign = s.charAt(p);
                        }
                        else if (isDigit(s.charAt(p))){
                            int startDigit = p;
                            while(p < s.length() && isDigit(s.charAt(p))){
                                p++;   
                            }
                            String digitstr = s.substring(startDigit, p);
                            int digit = Integer.parseInt(digitstr);
                            if(sign == '+'){
                                words.replace(setVar, words.get(setVar) + digit);
                            }
                            else{
                                words.replace(setVar, words.get(setVar) - digit);
                            }
                        }
                        else if(s.charAt(p) == '$'){
                            st = p;
                            p = getName(s, p + 1);
                            String var = s.substring(st, p);
                            
                            if(var.isEmpty()){
                                System.out.println("Ошибка при обозначении переменной");
                                break;
                            }
                            else{
                                if(sign == '+'){
                                    words.put(setVar, words.getOrDefault(setVar, 0) + words.getOrDefault(var, 0));
                                }
                                else{
                                    words.put(setVar, words.getOrDefault(setVar, 0) - words.getOrDefault(var, 0));
                                }
                            }   
                     
                        }
                        p++;
                    }
                }
        
    } 
    
    public void processFile(String Filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Filename));
            
            String s;
            int lineNo = 0;
            
            while ((s= br.readLine()) != null) {
                lineNo++;
                
                if(s.isBlank() || s.charAt(0) == '#')
                continue;
                
                 //строка начинается с set 
                String set = "set";
                
                
                if(s.startsWith(set)){
                    int p = 4;

                    setHandler(s, p);
                    
                    
                    } 
                
                String prnt = "print ";
                if(s.startsWith(prnt)){
                    
                    print(s.substring(prnt.length(), s.length()));
                    
                    
                    } 
            
            }  
                         

        
        
    }

}
