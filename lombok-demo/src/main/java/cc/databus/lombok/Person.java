package cc.databus.lombok;

import lombok.Data;

import java.util.Date;



/**
 * 1. compile with  java -cp /path/to/lombok.jar Person.java
 * 2. get Person.class
 * 3. use javap Person.class to see the content:
 * <pre>
 * Compiled from "Person.java"
 * public class cc.databus.lombok.Person {
 *   public cc.databus.lombok.Person();
 *   public java.lang.String getName();
 *   public java.util.Date getBirthday();
 *   public int getGender();
 *   public void setName(java.lang.String);
 *   public void setBirthday(java.util.Date);
 *   public void setGender(int);
 *   public boolean equals(java.lang.Object);
 *   protected boolean canEqual(java.lang.Object);
 *   public int hashCode();
 *   public java.lang.String toString();
 * }
 * </pre>
 */
@Data
public class Person {
    private String name;
    private Date birthday;
    private int gender;
}
