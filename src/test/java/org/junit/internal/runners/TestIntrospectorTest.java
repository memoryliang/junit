package org.junit.internal.runners;

import org.junit.Test;
import org.junit.samples.ListTest;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by memoryliang on 2016/10/30.
 */
public class TestIntrospectorTest {

  @Test
  public void testGetTestMethods() throws Exception {
    TestIntrospector testIntrospector = new TestIntrospector(ListTest.class);
    List<Method> methodList = testIntrospector.getTestMethods(Test.class);
//    methodList.addAll(testIntrospector.getTestMethods(Before.class));
    for (Method method : methodList) {
      System.out.println(method.getName());
    }

  }

  @Test
  public void testIsIgnored() throws Exception {

  }

  @Test
  public void testGetTimeout() throws Exception {

  }

  @Test
  public void testExpectedException() throws Exception {

  }
}