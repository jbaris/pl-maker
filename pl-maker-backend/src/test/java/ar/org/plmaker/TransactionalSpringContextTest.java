package ar.org.plmaker;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations = "classpath:spring/applicationTestContext.xml")
@TransactionConfiguration(defaultRollback = true)
public abstract class TransactionalSpringContextTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
