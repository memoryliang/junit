package org.junit.tests;

import junit.extensions.TestDecorator;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.OldTestClassRunner;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class OldTestClassRunnerTest {
  public static class MyTest extends TestCase {
    public void testA() {

    }
  }

  @Test
  public void plansDecoratorCorrectly() {
    OldTestClassRunner runner = new OldTestClassRunner(new TestDecorator(new TestSuite(MyTest.class)));
    assertEquals(1, runner.testCount());
  }

  public static class AnnotatedTest {
    @Test
    public void foo() {
      Assert.fail();
    }
  }

  @Test
  public void canUnadaptAnAdapter() {
    OldTestClassRunner runner = new OldTestClassRunner(new JUnit4TestAdapter(AnnotatedTest.class));
    Result result = new JUnitCore().run(runner);
    Failure failure = result.getFailures().get(0);
    assertEquals(Description.createTestDescription(AnnotatedTest.class, "foo"), failure.getDescription());
  }
}
