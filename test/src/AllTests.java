
import com.yaps.petstore.domain.CategoryTest;
import com.yaps.petstore.domain.CustomerTest;
import com.yaps.petstore.domain.ItemTest;
import com.yaps.petstore.domain.ProductTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class launches all the tests of the application
 */
public final class AllTests extends TestCase {

    public AllTests() {
        super();
    }

    public AllTests(final String s) {
        super(s);
    }

    //==================================
    //=            Test suite          =
    //==================================
    public static TestSuite suite() {

        final TestSuite suite = new TestSuite();

        // Domain
        suite.addTest(CategoryTest.suite());
        suite.addTest(CustomerTest.suite());
        suite.addTest(ItemTest.suite());
        suite.addTest(ProductTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
