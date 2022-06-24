package exceptions;

import java.util.*;
import java.io.*;
import java.lang.*;


public class DivideByZeroException extends IOException{
  private final String msg;
  public DivideByZeroException(String msg){
      super(msg);
      this.msg = msg;
  }
}