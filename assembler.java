/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class assembler {

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        HashMap<String,String> optable = new HashMap<String,String>(); 
        HashMap<String,String> symtable = new HashMap<String,String>(); 
        HashMap<String,String> literalvalue = new HashMap<String,String>();
        HashMap<String,String> literallength = new HashMap<String,String>();
        HashMap<String,String> literaladdress = new HashMap<String,String>();
        int starting= 0; 
        int programlength = 0; 
        int flag = 0;  
        String label1 = null; 
        int lng = 0;
        int m=0;
        int R=1;
        
        String[] error = new String[100]; 
        String LOCCOUNT = null; 
        String STARTING = null; 
        String[] lc = new String[100]; 
        String[] litrals = new String[100];
        int rou = 0;
        int f = 0, q = 0, countliteral = 0;
        
        optable.put("ADD", "18");
        optable.put("AND", "40");
        optable.put("COMP", "28");
        optable.put("DIV", "24");
        optable.put("J", "3C");
        optable.put("JEQ ", "30");
        optable.put("JGT ", "34");
        optable.put("JLT", "38");
        optable.put("JSUB", "48");
        optable.put("LDA", "00");
        optable.put("LDCH", "50");
        optable.put("LDL", "08");
        optable.put("LDX", "04");
        optable.put("MUL", "20");
        optable.put("OR", "44");
        optable.put("RD", "D8");
        optable.put("RSUB", "4C");
        optable.put("STA", "0C");
        optable.put("STCH", "54");
        optable.put("STL", "14");
        optable.put("STX ", "10");
        optable.put("SUB", "1C");
        optable.put("TD", "DC");
        optable.put("TIX", "2C");
        optable.put("WD", "E0");
        optable.put("add", "18");
        optable.put("and", "40");
        optable.put("comp", "28");
        optable.put("div", "24");
        optable.put("j", "3C");
        optable.put("jeq", "30");
        optable.put("jgt", "34");
        optable.put("jlt", "38");
        optable.put("jsub", "48");
        optable.put("lda", "00");
        optable.put("ldch", "50");
        optable.put("ldl", "08");
        optable.put("ldx", "04");
        optable.put("mul", "20");
        optable.put("or", "44");
        optable.put("rd", "D8");
        optable.put("rsub", "4C");
        optable.put("sta", "0C");
        optable.put("stch", "54");
        optable.put("stl", "14");
        optable.put("stx", "10");
        optable.put("sub", "1C");
        optable.put("td", "DC");
        optable.put("tix", "2C");
        optable.put("wd", "E0");
        
        
        int p = 0; 
        int lflag = 0;
        int cnt = 0;
        String litlength = "0";
        String litvalue= null;
        int lcindex = 0; 
        int endflag = 0; 
        int pc1 = 0; 
        int startflag = 0; 
        
                int LOCCOUNT1 = 0; 

        
        try {

            String op = "null";
            String label;
            File file = new File("SRCFILE"); 
            FileInputStream input = new FileInputStream(file);
            Scanner reader = new Scanner(input);
            while (reader.hasNext()) { 
                String buffer = reader.nextLine(); 
                if (!buffer.startsWith(".")) { 
                    label = buffer.substring(0, Math.min(7, buffer.length()));
                    op = (buffer.substring(9, Math.min(14, buffer.length())));
                    String operand = buffer.substring(17, Math.min(34, buffer.length())); 
                    String blank = buffer.substring(8, Math.min(9, buffer.length()));
                    String blank2 = buffer.substring(15, Math.min(17, buffer.length()));

                    op = op.replace(" ", ""); 
                    operand = operand.replace(" ", "");
                    label = label.replace(" ", "");

                    if (operand.contains(",")) { 

                        String parts[] = operand.split("\\,");
                        operand = parts[0];

                    }
                    if (operand.startsWith("=")) {
                        lflag = 1;

                        String parts[] = operand.split("\\=");
                        operand = parts[1];
                        if (symtable.get(operand) == null) {
                            cnt++;
                        }
                        litrals[rou++] = parts[1];
                        if (parts[1].startsWith("x")) {
                            litvalue= parts[1].substring(2, (parts[1].length() - 1));
                            literalvalue.put(parts[1], litvalue);
                            literallength.put(parts[1], "3");
                        } else if (parts[1].startsWith("c")) {
                            char[] chars = parts[1].toCharArray();
                            StringBuffer hex = new StringBuffer();
                            for (int i = 3; i < (chars.length - 1); i++) {
                                hex.append(Integer.toHexString((int) chars[i]));
                            }
                            litvalue = hex.toString();
                            litlength = String.valueOf((litvalue.length() / 2));
                            literalvalue.put(parts[1], litvalue);
                            literallength.put(parts[1], litlength);
                            op = "byte";
                        }

                    }

                    
                    if (op.equalsIgnoreCase("start")) {

                        if (startflag == 1) {
                            error[pc1] = "***start is once in the program";
                        }
                        if (isNumeric(operand)) { 
                            LOCCOUNT1 = Integer.valueOf(String.valueOf(operand), 16);
                            starting = Integer.valueOf(String.valueOf(operand), 16);
                            label1 = label;
                            LOCCOUNT = Integer.toHexString(LOCCOUNT1);
                            STARTING = Integer.toHexString(starting);
                            lc[lcindex] = LOCCOUNT;
                            lcindex++;
                        } else { 
                            error[pc1] = "***must be digit operand default starting address 1000";
                            LOCCOUNT1 = 4096;
                            starting = 4096;
                            label1 = label;
                            LOCCOUNT = Integer.toHexString(LOCCOUNT1);
                            STARTING = Integer.toHexString(starting);
                            lc[lcindex] = LOCCOUNT;
                            lcindex++;
                        }
                        startflag= 1;

                        pc1++; 
                    } else {
                        if (!label.startsWith(".")) { 
                            if (optable.get(op) != null) {
                                LOCCOUNT1 = LOCCOUNT1 + 3;
                            } else if (op.equalsIgnoreCase("word")) {
                                if (!isNumeric(operand)) {
                                    error[pc1] = "***wrong operand format default operand zero";
                                    LOCCOUNT1 = LOCCOUNT1 + 3;
                                } else {
                                    LOCCOUNT1 = LOCCOUNT1 + 3;
                                }
                            } else if (op.equalsIgnoreCase("resw")) {
                                if (!isNumeric(operand)) {
                                    error[pc1] = "***wrong operand format";
                                } else {
                                    LOCCOUNT1 = LOCCOUNT1 + (3 * Integer.valueOf(operand));
                                }

                            } else if (op.equalsIgnoreCase("resb")) {
                                if (!isNumeric(operand)) {
                                    error[pc1] = "***wrong operand format";
                                } else {
                                    LOCCOUNT1 = LOCCOUNT1 + Integer.valueOf(operand);
                                }
                            } else if (op.equalsIgnoreCase("byte")) {

                                if (isNumeric(operand)) {
                                    error[pc1] = "***wrong operand format";

                                    LOCCOUNT1 = LOCCOUNT1 + (operand.length() - 3);
                                } 

                                LOCCOUNT1 = LOCCOUNT1 + (operand.length() - 3);

                            } else if (op.equalsIgnoreCase("org")) {

                                if (isNumeric(operand)) {
                                    LOCCOUNT1 = Integer.valueOf(String.valueOf(operand), 16);
                                } else {
                                    if (symtable.get(operand) != null) {
                                        LOCCOUNT1 = Integer.valueOf(String.valueOf(symtable.get(operand)), 16);
                                    } else {
                                        error[pc1] = "***wrong operand format not defined before org";
                                    }
                                }
                            } else if (op.equalsIgnoreCase("EQU")) {
                                if (isNumeric(operand)) {
                                    symtable.put(label, lc[lcindex- 1]);
                                } else {
                                    if (symtable.get(operand) != null) {
                                        symtable.put(label, symtable.get(operand));
                                    } else {
                                        String s = null;
                                        int pair= 0;
                                        if (operand.contains("+") || (operand.contains("-"))) {
                                            char[] chars = operand.toCharArray();
                                            StringBuilder builder = new StringBuilder(5);
                                            
                                            for (int i = 0; i < (chars.length); i++) {

                                                if (chars[i] == ('+') || chars[i] == '-') {
                                                    s =builder.append(chars[i]).toString();
                                                            
                                                    if (chars[i] == ('+')) {
                                                        R++;
                                                    }
                                                    if (chars[i] == ('-')) {
                                                        m++;
                                                    }
                                                }
                                            }
                                            
                                            chars = s.toCharArray();
                                            String news[] = new String[10];
                                            for (int i = 0; i < chars.length; i++) {
                                                if (chars[i] == '+') {
                                                    String parts[] = operand.split("\\+");
                                                   
                                                    String k = parts[i];
                                                    news[i] = k;

                                                    if (i == chars.length - 1) {
                                                        news[i + 1] = parts[1];
                                                    }
                                                }
                                                if (chars[i] == '-') {
                                                    String parts[] = operand.split("\\-");
                                                   
                                                    
                                                    news[i] = parts[0];
                                                    if (i == chars.length - 1) {
                                                        news[i + 1] = parts[1];
                                                    }
                                                }

                                            }
                                            if ((chars.length+1) % 2 == 0) {
                                                if (R == m) {
                                                    pair = 1;
                                                }
                                            } else {
                                                if (R == m + 1) {
                                                    pair = 1;
                                                }
                                            }
                                          
                                           int k=0;
                                            for (int i = 0; i < chars.length; i++) {
                                                  int relativeflag1 = 0, relativeflag2 = 0;
                                                 
                                                if (!isNumeric(news[i]) && pair == 1) {
                                                    news[i] = (String) symtable.get(news[i]);
                                                    relativeflag1 = 1;
                                                }
                                                if (!isNumeric(news[i + 1]) && pair== 1) {
                                                    news[i + 1] = (String) symtable.get(news[i + 1]);
                                                    relativeflag2 = 1;
                                                }
                                            
                                                  
                                              if((chars.length+1) % 2 != 0)
                                              { news[i+1]="0";
                                              
                                              relativeflag2 =relativeflag1;
                                             
                                              
                                              }
                                           
                                              
                                                if (chars[i] == '-'&& relativeflag1==relativeflag2 && pair!=0) {
                                                  k =k+ Integer.valueOf(String.valueOf(news[i]), 16) - Integer.valueOf(String.valueOf(news[i + 1]), 16);
                                                  
                                                    symtable.put(label, Integer.toHexString(k));
                                                     
                                                }  
                                                if (chars[i] == '+'&& relativeflag1==relativeflag2 && pair!=0 ) {
                                                    
                                                   k = k+Integer.valueOf(String.valueOf(news[i]), 16) + Integer.valueOf(String.valueOf(news[i + 1]), 16);
                                                    symtable.put(label, Integer.toHexString(k));
                                                      
                                                }
                                                if(pair==0)
                                                {error[pc1] = "***wrong PAIR FORMAT";}
                                                   if(relativeflag1!=relativeflag2 )
                                                {error[pc1] = "***wrong operand format relative to relative or absoulte to";}
                                                
                                            }
                                             
                                        } else {
                                            error[pc1] = "***wrong operand format";
                                        }

                                    }
                                }
                            } else if (op == null) {
                                error[pc1] = "***missing operation code";
                            } else if (op.equalsIgnoreCase("end")) {
                                if (endflag== 1) {
                                    error[pc1] = ""
                                            + "***WRONG format repeated end statement";
                                }
                                if (!(operand.equals(label1))) {

                                    if (!(operand.equals(lc[0]))) {
                                        error[pc1] = "***missing or misplaced or illegal operand";
                                    }
                                }

                                endflag = 1;
                            } else if (op.equalsIgnoreCase("LTORG")) {
                                countliteral++;
                                lc[lcindex] = Integer.toHexString(LOCCOUNT1);
                                lcindex++;
                                for (f = q; f < cnt; f++) {
                                    if (symtable.get(litrals[f]) == null) {
                                        String fk = (String) literallength.get(litrals[f]);
                                        LOCCOUNT1 = LOCCOUNT1 + (Integer.valueOf(fk, 16));
                                        lc[lcindex] = Integer.toHexString(LOCCOUNT1);
                                        literaladdress.put(litrals[f], lc[lcindex - 1]);
                                        symtable.put(litrals[f], lc[lcindex - 1]);
                                        lcindex++;
                                    }

                                }
                                q = f;
                            } else {
                                error[pc1] = "***invalid or missing operation code";

                            }
                            LOCCOUNT = Integer.toHexString(LOCCOUNT1);
                            lc[lcindex] = LOCCOUNT;
                            lcindex++;
                            if (!blank.equals(" ")) {
                                error[pc1] = "***wrong formatof instruction";
                            }
                            if (!blank2.equals("  ")) {
                                error[pc1] = "***wrong formatof instruction";
                            }

                            if (!label.equals("") && !op.equalsIgnoreCase("equ")) {
                                if (!isNumeric(label)) {
                                    if (symtable.get(label) == null) {
                                        symtable.put(label, lc[lcindex - 2]);

                                    } else {
                                        error[pc1] = "***duplication symbol";

                                    }
                                } else {
                                    error[pc1] = "***illegal format in label field";
                                }
                            }

                            pc1++;
                        }

                    }
                }
            }
            if (endflag== 1 && lflag == 1) {
                for (f = q; f < cnt; f++) {
                    if (symtable.get(litrals[f]) == null) {
                        {
                            String fk = (String) literallength.get(litrals[f]);
                            LOCCOUNT1 = LOCCOUNT1 + (Integer.valueOf(fk, 16));
                            lc[lcindex] = Integer.toHexString(LOCCOUNT1);
                            literaladdress.put(litrals[f], lc[lcindex - 1]);
                            symtable.put(litrals[f], lc[lcindex- 1]);
                        }
                        lcindex++;
                    }

                }
            }

        } catch (IOException e) {
            System.out.println("error");
        }
        programlength = (Integer.valueOf(lc[lcindex - 2], 16)) - starting;
        String prolength = Integer.toHexString(programlength);
        cnt = 0;
        f = 0;
        q = 0;
        countliteral = 0;
        

        String op = "null";
        File file = new File("SRCFILE");
        FileInputStream input = new FileInputStream(file);
        File file1 = new File("LISTFILE");
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(file1));
        File file2 = new File("OBJFILE");
        BufferedWriter writer1 = null;
        String[] object = new String[100];
        int s = 0; 
        STARTING = String.format("%6s", STARTING).replace(' ', '0'); 
        int[] x = new int[100]; 
        String[] length = new String[100]; 
        writer1 = new BufferedWriter(new FileWriter(file2));
        writer1.write("H" + "^" + label1 + "^" + STARTING + "^" + prolength); 
        writer1.newLine();

        Scanner reader = new Scanner(input);
        String objectcd;
        String label;
        lcindex = 0;
        int pc2 = 0;
        while (reader.hasNext()) {
            String buffer = reader.nextLine();
            if (!buffer.startsWith(".")) {
                label = buffer.substring(0, Math.min(7, buffer.length()));
                op = (buffer.substring(9, Math.min(14, buffer.length())));
                String operand = buffer.substring(17, Math.min(34, buffer.length()));
                op = op.replace(" ", "");
                operand = operand.replace(" ", "");
                label = label.replace(" ", "");
                if (operand.contains(",")) {
                    flag = 1;
                    String parts[] = operand.split("\\,");
                    String operand1 = parts[0];
                    String k = (String) symtable.get(operand1);
                    int y = Integer.valueOf(k, 16) + 32768; 
                    objectcd = optable.get(op) + Integer.toHexString(y);
                } else if (operand.contains("=")) {
                    String parts[] = operand.split("\\=");
                    operand = parts[1];
                    int calco = Integer.valueOf((String) (literaladdress.get(parts[1])), 16) - Integer.valueOf(lc[lcindex + countliteral + 1], 16);
                    cnt++;
                    String k = (String) optable.get(op);
                    int y = Integer.valueOf(k, 16) + 3;
                    objectcd = Integer.toHexString(y) + "2" + String.format("%3s", Integer.toHexString(calco)).replace(' ', '0');;
                } else {
                    objectcd = optable.get(op) + String.valueOf(symtable.get(operand));
                }
                if (op.equalsIgnoreCase("resw") || op.equalsIgnoreCase("end") || op.equalsIgnoreCase("start")|| op.equalsIgnoreCase("org") || op.equalsIgnoreCase("equ") || op.equalsIgnoreCase("resb")) {
                    objectcd = "      ";
                }
                if (op.equalsIgnoreCase("word")) {
                    if (!isNumeric(operand)) {
                        objectcd = "      ";

                    } else {
                        objectcd = Integer.toHexString(Integer.valueOf(operand));
                    }
                }
                if (op.equalsIgnoreCase("ltorg")) {
                    objectcd = "     ";
                    countliteral++;
                }
                if (op.equalsIgnoreCase("byte")) {
                    if (operand.startsWith("c")) {
                        char[] chars = operand.toCharArray();
                        StringBuffer hex = new StringBuffer();
                        for (int i = 3; i < (chars.length - 1); i++) {
                            hex.append(Integer.toHexString((int) chars[i]));
                        }
                        objectcd = hex.toString();
                    }
                    if (operand.startsWith("x")) {
                        objectcd = operand.substring(2, (operand.length() - 1));
                    }

                }

                String padded = String.format("%6s", objectcd).replace(' ', '0');
                writer.write(lc[lcindex + countliteral] + " " + padded + " " + buffer);
                writer.newLine();

                if (op.equalsIgnoreCase("ltorg")) {
                    for (f = q; f < cnt; f++) {
                        if (litrals[f] != null && symtable.get(litrals[f]).equals(lc[lcindex +countliteral])) {
                            if (litrals[f].startsWith("x")) {
                                String wqw = (String) literalvalue.get(litrals[f]);
                                writer.write(lc[lcindex+ countliteral] + " 000000 " + litrals[f] + "        " + "WORD" + "    "
                                        + Integer.valueOf(String.valueOf(wqw), 16));
                                writer.newLine();
                            }
                            if (litrals[f].startsWith("c")) {
                                writer.write(lc[lcindex+ countliteral] + " 000000 " + litrals[f] + "        " + "BYTE" + "    " + literalvalue.get(litrals[f]));
                                writer.newLine();
                            }
                            lcindex++;
                        }
                    }
                    q = f;
                }
                if (error[pc2] != null) {

                    writer.write(error[pc2]);
                    writer.newLine();
                }

                pc2++;

                String objectcd1 = objectcd.replace(" ", "");
                if (objectcd1 != null) {
                    object[s] = objectcd1;
                }
                lng= object[s].length() + lng;
                s++;
                if (lng >= 52 || !reader.hasNext()) {
                    lng = 0;
                    x[p] = (Integer.valueOf(String.valueOf(lc[lcindex]), 16)) - starting;
                    starting= (Integer.valueOf(String.valueOf(lc[lcindex]), 16));
                    length[p] = Integer.toHexString(starting);

                    p++;
                }
                if (!op.equalsIgnoreCase("start")) {
                    lcindex++;
                }
            } else {
                writer.write(buffer);
                writer.newLine();
            }

        }
        for (f = q; f < cnt; f++) {
            if (symtable.get(litrals[f]).equals(lc[lcindex+ countliteral])) {
                if (litrals[f].startsWith("x")) {
                    String wqw = (String) literalvalue.get(litrals[f]);

                    writer.write(lc[lcindex++ + countliteral] + " 000000 " + litrals[f] + "        " + "WORD" + "    "
                            + Integer.valueOf(String.valueOf(wqw), 16));
                    writer.newLine();
                }
                if (litrals[f].startsWith("c")) {
                    writer.write(lc[lcindex++ + countliteral] + " 000000 " + litrals[f] + "        " + "BYTE" + "    " + literalvalue.get(litrals[f]));
                    writer.newLine();
                }
            }

        }
        lng = 0;
        p = 0;
        f = 0;
        q = 0;
        writer1.write("T" + "^" + STARTING + "^" + Integer.toHexString(x[0]));
        for (s = 0; s < object.length; s++) {

            if (object[s] != null) {
                writer1.write("^" + object[s]);
                lng = object[s].length() + lng;

                if (lng>= 52) { 
                    writer1.newLine();
                    lng = 0;
                    p++;
                    writer1.write("T" + "^" + length[p] + "^" + Integer.toHexString(x[p]));
                }
            }
        }

        writer1.newLine();
        for (f = q; f < cnt; f++) {
            int kol = Integer.valueOf(String.valueOf(literaladdress.get(litrals[f])), 16) - Integer.valueOf(String.valueOf(lc[0]), 16);
            writer1.write("M" + "^" + Integer.toHexString(kol) + "^" + literallength.get(litrals[f]));
            writer1.newLine();
        }
        writer1.write("E" + "^" + STARTING);
        writer.close();
        writer1.close();

    }
}
