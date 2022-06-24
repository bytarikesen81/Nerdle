package exceptions;

import java.util.*;
import java.io.*;
import java.lang.*;


public class InvalidInputException extends RuntimeException{
  private final String msg;
  public InvalidException(String msg){
    super(msg); 
    this.msg = msg
  }
}
