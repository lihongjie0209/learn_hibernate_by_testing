package cn.lihongjie;

import cn.lihongjie.entity.relation.OrganizationEntity;
import cn.lihongjie.entity.xml.IdentityGenEntity;
import cn.lihongjie.entity.xml.UserEntity;
import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.hibernate.query.NativeQuery;
import org.junit.*;

import javax.persistence.Query;

import java.awt.image.SampleModel;
import java.util.Arrays;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

/**
 * hibernate对象状态管理
 * <p>
 * 瞬时状态(用户new出来的, 没有ID) delete <---> insert
 * insert
 * |
 * |
 * delete
 * session(使用session进行操作, 然后关闭session, 有ID) 对象的任何变化会更新到数据库
 * close
 * |
 * |
 * update
 * 游离状态
 *
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

		clearAllTablesInEntity();


	}

	private void clearAllTablesInEntity() {


		List<String> entities = Arrays.asList(
				"cn.lihongjie.entity.xml.IdentityGenEntity",
				"cn.lihongjie.entity.xml.UserEntity",

				"cn.lihongjie.entity.relation.UserEntity",
				"cn.lihongjie.entity.relation.OrganizationEntity"
				);


		Transaction transaction = session.beginTransaction();

		for (String entity : entities) {

			String stringQuery = String.format("DELETE FROM %s", entity);
			Query query = session.createQuery(stringQuery);
			query.executeUpdate();
		}

		transaction.commit();
	}


	@After
	public void tearDown() throws Exception {
		session.close();
	}


	@Test
	public void testDelete() throws Exception {

		UserEntity userEntity = insertTestUser();
		String entityName = session.getEntityName(userEntity);

		Transaction transaction = session.beginTransaction();

		session.delete(userEntity);

		transaction.commit();

		assertCount(0, entityName);

	}


	@Test
	public void testInsert() throws Exception {

		UserEntity userEntity = insertTestUser();

		assertCount(1L, session.getEntityName(userEntity));

	}

	private void assertCount(long c, String entityName) {
		long count = (Long) session.createCriteria(entityName)
				.setProjection(Projections.rowCount())
				.uniqueResult();


		assertThat(count, is(c));
	}

	private UserEntity insertTestUser() {
		UserEntity userEntity = new UserEntity();
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
	 * <p>
	 * 支持sequence 的数据库 使用sequence
	 * <p>
	 * <p>
	 * 使用native 自动选择
	 * <p>
	 * <p>
	 * 可以以让用户自己录入主键
	 *
	 * @throws Exception
	 */
	@Test
	public void testIdentityIdGen() throws Exception {


		IdentityGenEntity entity = new IdentityGenEntity();
		session.save(entity);
		assertThat(entity.getId(), is(1L));
		logger.info(entity);

		IdentityGenEntity entity2 = new IdentityGenEntity();
		session.save(entity2);
		assertThat(entity2.getId(), is(2L));
		logger.info(entity2);

	}


	/**
	 * 同一个session中的多个查询会被缓存
	 * 同时hibernate会保留一个关于这个对象的快照
	 *
	 * @throws Exception
	 */
	@Test
	public void testL1Cache() throws Exception {


		insertTestUser();

		// 同一个session中的对象会被缓存
		UserEntity u1 = session.get(UserEntity.class, 1L);
		UserEntity u2 = session.get(UserEntity.class, 1L);
		assertThat(u1, is(u2));

	}

	/**
	 * 通过在配置文件中配置session上下文, 我们可以通过sessionFactory获得当前的session, 并且保证线程唯一
	 * <p>
	 * 这个session在事务提交之后自动关闭
	 *
	 * @throws Exception
	 */
	@Test
	public void testThreadLocalSession() throws Exception {


		Session currentSession1 = sessionFactory.getCurrentSession();
		Session currentSession2 = sessionFactory.getCurrentSession();

		assertThat(currentSession1, is(currentSession2));


		assertThat(currentSession1, not(session));


	}


	@Test
	public void testHQL() throws Exception {

		Transaction transaction = session.beginTransaction();
		UserEntity userEntity = new UserEntity("test1", "aa", "1");
		session.save(userEntity);

		UserEntity userEntity2 = new UserEntity("test2", "aa", "1");
		session.save(userEntity2);
		transaction.commit();


		org.hibernate.query.Query query = session.createQuery("from cn.lihongjie.entity.xml.UserEntity ");

		List<UserEntity> list = query.list();


		assertThat(list.size(), is(2));

	}


	@Test
	public void testSQL() throws Exception {

		Transaction transaction = session.beginTransaction();
		UserEntity userEntity = new UserEntity("test1", "aa", "1");
		session.save(userEntity);

		UserEntity userEntity2 = new UserEntity("test2", "aa", "1");
		session.save(userEntity2);
		transaction.commit();


		NativeQuery<UserEntity> query = session.createNativeQuery("SELECT * FROM user", UserEntity.class);

		List<UserEntity> list = query.list();

		assertThat(list.size(), is(2));

	}


	@Test
	public void testOneToMany() throws Exception {

		OrganizationEntity org = new OrganizationEntity();

		Transaction transaction = session.beginTransaction();
		session.save(org);
		session.save(org.addUser());
		session.save(org.addUser());
		session.save(org.addUser());

		transaction.commit();


	}


	@Test
	public void testOneToManyCascade() throws Exception {

		OrganizationEntity org = new OrganizationEntity();

		org.addUser();
		org.addUser();
		org.addUser();

		Transaction transaction = session.beginTransaction();
		session.save(org);


		transaction.commit();


	}

	@Test
	public void testOneToManyCascadeDelete() throws Exception {

		OrganizationEntity org = new OrganizationEntity();

		org.addUser();
		org.addUser();
		org.addUser();

		Transaction transaction = session.beginTransaction();
		session.save(org);
		session.delete(org);
		transaction.commit();






	}


	@Test
	public void testManyToOne() throws Exception {














	}
}
