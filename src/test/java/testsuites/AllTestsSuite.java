package testsuites;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	testsuites.HandlersTestSuite.class,
	testsuites.ModelPersistenceTestSuite.class,
	testsuites.ModelTestSuite.class,
	testsuites.ServicesMailingTestSuite.class,
	testsuites.ServicesTestSuite.class,
	testsuites.UtilsTestSuite.class
})


public class AllTestsSuite {

}
