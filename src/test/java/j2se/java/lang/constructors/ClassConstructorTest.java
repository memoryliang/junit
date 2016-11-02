package j2se.java.lang.constructors;

import java.lang.reflect.Constructor;

/**
 * Created by memoryliang on 2016/11/1.
 */
public class ClassConstructorTest {
  public static void main(String args[]) throws Throwable {
    Class ctClass = ConstructorTest.class;

    // creating an object calling no argument constructor via newInstance of Class object
//    ConstructorTest ctNoArgs = (ConstructorTest) ctClass.getConstructor().newInstance();
    ConstructorTest ctNoArgs = (ConstructorTest) ctClass.newInstance();
    ctNoArgs.setPub("created this with ctClass.newInstance()");
    System.out.println("pub:" + ctNoArgs.getPub());

    // creating an object by getting Constructor object (with parameters) and calling newInstance (with parameters) on it
    Constructor constructor = ctClass.getConstructor(new Class[] { String.class, String.class, String.class });
    ConstructorTest ctArgs = (ConstructorTest) constructor.newInstance(new Object[] { "first", "second", "third" });
    ctArgs.setPub("created this with constructor.newInstance(new Object[] { \"first\", \"second\", \"third\" })");

    System.out.println("\npub:" + ctArgs.getPub());
    System.out.println("pro:" + ctArgs.getPro());
    System.out.println("pri:" + ctArgs.getPri());
  }
}
