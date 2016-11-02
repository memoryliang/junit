package org.junit.internal.runners;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Test.None;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Introspector(内窥镜)
 */
public class TestIntrospector {
  private final Class<?> fTestClass;

  public TestIntrospector(Class<?> testClass) {
    fTestClass = testClass;
  }

  /**
   * 查询有指定注解的方法
   *
   * @param annotationClass
   * @return
   */
  public List<Method> getTestMethods(Class<? extends Annotation> annotationClass) {
    List<Method> results = new ArrayList<Method>();
    for (Class eachClass : getSuperClasses(fTestClass)) {
      // 类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。当然也包括它所实现接口的方法。
      Method[] methods = eachClass.getDeclaredMethods();
      for (Method eachMethod : methods) {
        Annotation annotation = eachMethod.getAnnotation(annotationClass);
        if (annotation != null && !isShadowed(eachMethod, results))
          results.add(eachMethod);
      }
    }
    // 逆序 ，保证Before, BeforeClass注解在测试方法的最前面()
    if (runsTopToBottom(annotationClass))
      Collections.reverse(results);
    return results;
  }

  /**
   * 判断测试方法是否有Ignored注解
   *
   * @param eachMethod
   * @return
   */
  public boolean isIgnored(Method eachMethod) {
    return eachMethod.getAnnotation(Ignore.class) != null;
  }

  /**
   * 如果测试方法有Before或者BeforeClass注解
   *
   * @param annotation
   * @return
   */
  private boolean runsTopToBottom(Class<? extends Annotation> annotation) {
    return annotation.equals(Before.class) || annotation.equals(BeforeClass.class);
  }

  /**
   * 判断测试方法集合中是否有相同的测试方法
   *
   * @param method
   * @param results
   * @return
   */
  private boolean isShadowed(Method method, List<Method> results) {
    for (Method each : results) {
      if (isShadowed(method, each))
        return true;
    }
    return false;
  }

  /**
   * 判断两个方法是否相同
   *
   * @param current
   * @param previous
   * @return
   */
  private boolean isShadowed(Method current, Method previous) {
    if (!previous.getName().equals(current.getName()))
      return false;
    if (previous.getParameterTypes().length != current.getParameterTypes().length)
      return false;
    for (int i = 0; i < previous.getParameterTypes().length; i++) {
      if (!previous.getParameterTypes()[i].equals(current.getParameterTypes()[i]))
        return false;
    }
    return true;
  }

  private List<Class> getSuperClasses(Class<?> testClass) {
    ArrayList<Class> results = new ArrayList<Class>();
    Class<?> current = testClass;
    while (current != null) {
      results.add(current);
      current = current.getSuperclass();
    }
    return results;
  }

  /**
   * ?
   *
   * @param method
   * @return
   */
  long getTimeout(Method method) {
    Test annotation = method.getAnnotation(Test.class);
    long timeout = annotation.timeout();
    return timeout;
  }

  Class<? extends Throwable> expectedException(Method method) {
    Test annotation = method.getAnnotation(Test.class);
    if (annotation.expected() == None.class)
      return null;
    else
      return annotation.expected();
  }
}

