package j2se.java.lang.constructors;

/**
 * Created by memoryliang on 2016/11/1.
 */
public class ConstructorTest {
  private String pri;
  protected String pro;
  public String pub;

  public ConstructorTest() {
  }

  public ConstructorTest(String pri, String pro, String pub) {
    this.pri = pri;
    this.pro = pro;
    this.pub = pub;
  }

  public String getPri() {
    return pri;
  }

  public void setPri(String pri) {
    this.pri = pri;
  }

  public String getPro() {
    return pro;
  }

  public void setPro(String pro) {
    this.pro = pro;
  }

  public String getPub() {
    return pub;
  }

  public void setPub(String pub) {
    this.pub = pub;
  }
}
