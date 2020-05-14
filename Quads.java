package Parser;

import java.io.*;
import java.util.*;

public class Quads
{
   private String sym;
   private String arg1;
   private String arg2;
   private String arg3;
   
   public Quads()
   {
      sym = "|--|";
      arg1 = "|--|";
      arg2 = "|--|";
      arg3 = "|--|";
   }
   
   public Quads(String s)
   {
      sym = s;
      arg1 = "|--|";
      arg2 = "|--|";
      arg3 = "|--|";
   }
   
   public Quads(String s1, String s2)
   {
      sym = s1;
      arg1 = s2;
      arg2 = "|--|";
      arg3 = "|--|";
   }
   
   public Quads(String s1, String s2, String s3)
   {
      sym = s1;
      arg1 = s2;
      arg2 = s3;
      arg3 = "|--|";
   }
   
   public Quads(String s1, String s2, String s3, String s4)
   {
      sym = s1;
      arg1 = s2;
      arg2 = s3;
      arg3 = s4;
   }
   
   public String getSym()
   {
      return sym;
   }
   
   public String getArg1()
   {
      return arg1;
   }
   
   public String getArg2()
   {
      return arg2;
   }
   
   public String getArg3()
   {
      return arg3;
   }
   
   public void setSymbol(String s)
   {
      sym = s;
   }
   
   public void setArg1(String s)
   {
      arg1 = s;
   }
   
   public void setArg2(String s)
   {
      arg2 = s;
   }
   
   public void setArg3(String s)
   {
      arg3 = s;
   }
   
   public String toString()
   {
      return sym + " " + arg1 + " " + arg2 + " " + arg3;
   }
}