package Parser;

import Lex.Token;
import java.io.*;
import java.util.*;

public class Parse
{
   private TableTemp tt;
   private MyStk<Token> opStk;
   private MyStk<Token> varStk;
   private ArrayList<Quads> alq;
   private Token [] mtok;
   private char [][] table;
   private String [] ops;
   private int wCount;
   private int iCount;
   private int wEnd;
   private int iEnd;
   
   public Parse(Token [] mtok)
   {
      wCount = 1;
      iCount = 1;
      wEnd = 1;
      iEnd = 1;
      tt = new TableTemp();
      alq = new ArrayList<>();
      opStk = new MyStk<>();
      varStk = new MyStk<>();
      table = tt.getTable();
      ops = tt.getOps();
      this.mtok = mtok;
      process();
      basicOpt();
   }
   
   public void print(ArrayList<Token> temp)
   {
      for (Token q: temp)
      {
         System.out.println(q);
      }
   }

   private int getLoc(String s)
   {
      for (int i = 0; i < ops.length; i++)
      {
         if (s.equals(ops[i]))
         {
            return i;
         }
      }
      // if location not found then error occurs
      return -1;
   }

   private void process()
   {
      // initialize stack
      opStk.push(new Token(";", "<semi>"));
      int row, col;
      int tempC = 1;
      
      for (Token t: mtok)
      {
         if (!opCheck(t.getToken()))
         {
            varStk.push(t);
         }
         else
         {
            row = getLoc(opStk.peek().getToken());
            col = getLoc(t.getToken());
            
            switch(table[row][col])
            {
               case '<':
               case '=':
                  opStk.push(t);
                  break;
               case '>':
                  do
                  {
                     tempC = processQuad(tempC);
                     row = getLoc(opStk.peek().getToken());
                  }
                  while (table[row][col] == '>');
                  if (!t.getToken().equals(";"))
                  {
                     opStk.push(t);
                  }
                  break;
               default:
                  System.err.println("Error");
            }
         }
      }
   }
   
   private int processQuad(int tc)
   {
      String sym = opStk.pop().getToken();
      String arg1, arg2;
      String arg3 = "";
      switch (sym)
      {
         case "READ":
         case "WRITE":
            arg1 = varStk.pop().getToken();
            alq.add(new Quads(sym, arg1));
            break;
         case "IF":
            arg1 = "If" + iCount;
            iCount++;
            alq.add(new Quads(sym, arg1));
            break;
         case "THEN":
            alq.add(new Quads(sym));
            break;
         case "WHILE":
            arg1 = "While" + wCount;
            wCount++;
            alq.add(new Quads(sym, arg1));
            break;
         case "!=":
         case "==":
         case ">":
         case "<":
         case ">=":
         case "<=":
            if (opStk.peek().getToken().equals("IF"))
            {
               arg3 = "If" + iCount;
            }
            else if (opStk.peek().getToken().equals("WHILE"))
            {
               arg3 = "While" + wCount;
            }
            arg2 = varStk.pop().getToken();
            arg1 = varStk.pop().getToken();
            alq.add(new Quads(sym, arg1, arg2, arg3));
            break;
         case "DO":
            alq.add(new Quads(sym));
            break;
         case "=":
            arg2 = varStk.pop().getToken();
            arg1 = varStk.pop().getToken();
            alq.add(new Quads(sym, arg1, arg2));
            break;
         case "+":
         case "-":
         case "/":
         case "*":
            arg2 = varStk.pop().getToken();
            arg1 = varStk.pop().getToken();
            String temp = "__t" + tc + "__";
            alq.add(new Quads(sym, arg1, arg2, temp));
            varStk.push(new Token(temp, "<temp>"));
            return tc % 4 + 1;
      }      
      return tc;
   }
   
   public ArrayList<Quads> getQuads()
   {
      return alq;
   }
   
   public Quads [] getQuadsArray()
   {
      Quads [] temp = new Quads[alq.size()];
      int c = 0;
      for (Quads q: alq)
      {
         temp[c++] = q;
      }
      return temp;
   }
   
   private boolean opCheck(String s)
   {
      for (int i = 0; i < ops.length; i++)
      {
         if (ops[i].equals(s))
         {
            return true;
         }
      }
      return false;
   }
   
   private void basicOpt()
   {
      String symbol;
      String arg1;
      String arg2;
      for (int i = 0; i < alq.size(); i++)
      {
         symbol = alq.get(i).getSym();
         arg2 = alq.get(i).getArg2();
         
         if (symbol.equals("=") && arg2.matches("__t[0-9]+__"))
         {
            arg1 = alq.get(i).getArg1();
            alq.get(i-1).setArg3(arg1);
            alq.remove(i);
         }
      }
   }
}