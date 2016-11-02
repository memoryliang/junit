package org.junit.samples;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * A sample test case, testing <code>java.util.Vector</code>.
 */
public class ListTest {
  protected static List<Integer> fgHeavy;
  protected List<Integer> fEmpty;
  protected List<Integer> fFull;

  /**
   * 测试没有无参构造函数
   *
   * @param fEmpty
   */
  public ListTest(List<Integer> fEmpty) {
    this.fEmpty = fEmpty;
  }

  public ListTest() {
  }

  public static void main(String... args) {
    junit.textui.TestRunner.run(suite());
  }

  @BeforeClass
  public static void setUpOnce() {
    fgHeavy = new ArrayList<Integer>();
    for (int i = 0; i < 1000; i++)
      fgHeavy.add(i);
  }

  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(ListTest.class);
  }

  @Before
  public void setUp() {
    fEmpty = new ArrayList<Integer>();
    fFull = new ArrayList<Integer>();
    fFull.add(1);
    fFull.add(2);
    fFull.add(3);
  }

  @Ignore("not today")
  @Test
  public void capacity() {
    int size = fFull.size();
    for (int i = 0; i < 100; i++)
      fFull.add(i);
    assertTrue(fFull.size() == 100 + size);
  }

  @Test
  public void testCopy() {
    List<Integer> copy = new ArrayList<Integer>(fFull.size());
    copy.addAll(fFull);
    assertTrue(copy.size() == fFull.size());
    assertTrue(copy.contains(1));
  }

  /*@Test
  public void contains() {
    assertTrue(fFull.contains(1));
    assertTrue(!fEmpty.contains(1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void elementAt() {
    int i = fFull.get(0);
    assertTrue(i == 1);
    fFull.get(fFull.size()); // Should throw IndexOutOfBoundsException
  }

  @Test
  public void removeAll() {
    fFull.removeAll(fFull);
    fEmpty.removeAll(fEmpty);
    assertTrue(fFull.isEmpty());
    assertTrue(fEmpty.isEmpty());
  }

  @Test
  public void removeElement() {
    fFull.remove(new Integer(3));
    assertTrue(!fFull.contains(3));
  }*/
}