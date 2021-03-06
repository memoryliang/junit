package org.junit.tests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestResult;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SuiteTest {
  public static class TestA {
    @Test
    public void pass() {
    }
  }

  public static class TestB {
    @Test
    public void fail() {
      Assert.fail();
    }
  }

  @RunWith(Suite.class)
  @SuiteClasses({TestA.class, TestB.class})
  public static class All {
  }

  @Test
  public void ensureTestIsRun() {
    JUnitCore core = new JUnitCore();
    Result result = core.run(All.class);
    assertEquals(2, result.getRunCount());
    assertEquals(1, result.getFailureCount());
  }

  @Test
  public void suiteTestCountIsCorrect() throws Exception {
    Runner runner = Request.aClass(All.class).getRunner();
    assertEquals(2, runner.testCount());
  }

  @Test
  public void ensureSuitesWorkWithForwardCompatibility() {
    junit.framework.Test test = new JUnit4TestAdapter(All.class);
    TestResult result = new TestResult();
    test.run(result);
    assertEquals(2, result.runCount());
  }

  @Test
  public void forwardCompatibilityWorksWithGetTests() {
    JUnit4TestAdapter adapter = new JUnit4TestAdapter(All.class);
    List<? extends junit.framework.Test> tests = adapter.getTests();
    assertEquals(2, tests.size());
  }

  @Test
  public void forwardCompatibilityWorksWithTestCount() {
    JUnit4TestAdapter adapter = new JUnit4TestAdapter(All.class);
    assertEquals(2, adapter.countTestCases());
  }


  private static String log = "";

  @RunWith(Suite.class)
  @SuiteClasses({TestA.class, TestB.class})
  public static class AllWithBeforeAndAfterClass {
    @BeforeClass
    public static void before() {
      log += "before ";
    }

    @AfterClass
    public static void after() {
      log += "after ";
    }
  }

  @Test
  public void beforeAndAfterClassRunOnSuite() {
    log = "";
    JUnitCore.runClasses(AllWithBeforeAndAfterClass.class);
    assertEquals("before after ", log);
  }

  @RunWith(Suite.class)
  public static class AllWithOutAnnotation {
  }

  @Test
  public void withoutSuiteClassAnnotationProducesFailure() {
    Result result = JUnitCore.runClasses(AllWithOutAnnotation.class);
    assertEquals(1, result.getFailureCount());
    String expected = String.format(
        "class '%s' must have a SuiteClasses annotation",
        AllWithOutAnnotation.class.getName());
    assertEquals(expected, result.getFailures().get(0).getMessage());
  }

  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(SuiteTest.class);
  }
}
