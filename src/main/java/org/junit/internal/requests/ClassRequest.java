package org.junit.internal.requests;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.internal.runners.OldTestClassRunner;
import org.junit.internal.runners.TestClassRunner;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import java.lang.reflect.Constructor;

public class ClassRequest extends Request {
  private final Class<?> fTestClass;

  public ClassRequest(Class<?> each) {
    fTestClass = each;
  }

  /**
   * 反射的方式实例化Runner (RunWith注解内的Runner，TestClassRunner, OldTestClassRunner)
   *
   * @return
   */
  @Override
  public Runner getRunner() {
    Class runnerClass = getRunnerClass(fTestClass);
    try {
      Constructor constructor = runnerClass.getConstructor(Class.class);
      Runner runner = (Runner) constructor.newInstance(fTestClass);
      return runner;
    } catch (Exception e) {
     return Request.errorReport(fTestClass, e).getRunner();
    }
  }

  /**
   * 返回Runner.class
   * 如果使用第三方的Runner，使用RunWith注解
   *
   * @param testClass
   * @return
   */
  Class getRunnerClass(Class<?> testClass) {
    RunWith annotation = testClass.getAnnotation(RunWith.class);
    if(annotation != null){
      return annotation.value();
    }else if(isPre4Test(testClass)){
      return OldTestClassRunner.class;
    }else {
      return TestClassRunner.class;
    }
  }

  /**
   * 是否是Junit4之前的测试（Junit3）
   * @param testClass
   * @return
   */
  boolean isPre4Test(Class<?> testClass) {
    return junit.framework.TestCase.class.isAssignableFrom(testClass);
  }
}