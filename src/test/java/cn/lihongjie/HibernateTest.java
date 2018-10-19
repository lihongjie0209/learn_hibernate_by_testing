package cn.lihongjie;

import cn.lihongjie.entity.xml.IdentityGenEntity;
import cn.lihongjie.entity.xml.UserEntity;
import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.junit.*;

import javax.persistence.Query;

import static org.apache.log4j.Logger.getLogger;

/**
 * @author 982264618@qq.com
 */
public class HibernateTest {

	private static final Logger logger = getLogger(HibernateTest.class);

	private static SessionFactory sessionFactory;
	private Session session;

	@BeforeClass
	public static void init() {

		logger.info("start test");
		Configuration configuration = new Configuration();
		sessionFactory = configuration.configure().buildSessionFactory();


	}


	@AfterClass
	public static void destroy() {

		sessionFactory.close();
		logger.info("test finish");


	}


	@Before
	public void setUp() throws Exception {
		session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();
		String stringQuery = "DELETE FROM UserEntity ";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();

		transaction.commit();



	}


	@After
	public void tearDown() throws Exception {
		session.close();
	}


	@Test
	@Ignore
	public void testDelete() throws Exception {

		UserEntity userEntity = insertTestUser();

		Transaction transaction = session.beginTransaction();

		session.delete(userEntity);

		transaction.commit();

		assertCount(userEntity, 0);

	}


	@Test
	public void testInsert() throws Exception {

		UserEntity userEntity = insertTestUser();

		assertCount(userEntity, 1L);

	}

	private void assertCount(UserEntity userEntity, long c) {
		long count = (Long) session.createCriteria(session.getEntityName(userEntity))
				.setProjection(Projections.rowCount())
				.uniqueResult();


		Assert.assertThat(count, Is.is(c));
	}

	private UserEntity insertTestUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setName("test");
		userEntity.setEmail("abc@123.com");
		userEntity.setGender("1");

		Transaction transaction = session.beginTransaction();
		session.save(userEntity);

		transaction.commit();
		return userEntity;
	}


	/**
	 * 交给数据库生成主键
	 *
	 * 支持sequence 的数据库 使用sequence
	 *
	 *
	 * 使用native 自动选择
	 *
	 *
	 * 可以以让用户自己录入主键
	 * @throws Exception
	 */
	@Test
	public void testIdentityIdGen() throws Exception {


		IdentityGenEntity entity = new IdentityGenEntity();
		session.save(entity);
		Assert.assertThat(entity.getId(), Is.is(1L));
		logger.info(entity);

		IdentityGenEntity entity2 = new IdentityGenEntity();
		session.save(entity2);
		Assert.assertThat(entity2.getId(), Is.is(2L));
		logger.info(entity2);

	}
}
