package testsuites;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
org.wahlzeit.model.persistence.AbstractAdapterTest.class,
org.wahlzeit.model.persistence.DatastoreAdapterTest.class,
org.wahlzeit.model.persistence.GcsAdapterTest.class
})


public class ModelPersistenceTestSuite{

}
