package cn.lihongjie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 982264618@qq.com
 */
public class HibernateTest {


	private static SessionFactory sessionFactory;

	@BeforeClass
	public static void init() {

		Configuration configuration = new Configuration();
		sessionFactory = configuration.configure().buildSessionFactory();


	}




	@Test
	public void testConnection() throws Exception {


		Session session = sessionFactory.openSession();

		session.close();

	}
}
