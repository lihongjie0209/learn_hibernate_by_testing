package cn.lihongjie;

import cn.lihongjie.entity.jpa.OrganizationEntity;
import cn.lihongjie.entity.jpa.UserEntity;
import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.apache.log4j.Logger.getLogger;

/**
 * @author 982264618@qq.com
 */
public class JPATest {

	private static final Logger logger = getLogger(JPATest.class);

	private EntityManager entityManager;
	private static EntityManagerFactory entityManagerFactory;
	private EntityTransaction transaction;

	@BeforeClass
	public static void init() {


		// session factory
		entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");




	}

	@Before
	public void setUp() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
	}


	@After
	public void tearDown() throws Exception {
		entityManager.close();
	}

	@AfterClass
	public static void destroy() {


	}



	@Test
	public void testInsert() throws Exception {


		UserEntity user1 = new UserEntity("test");

		logger.info(String.format("before persistent %s", user1.toString()));
		transaction.begin();
		entityManager.persist(user1);
		transaction.commit();
		logger.info(String.format("after persistent %s", user1.toString()));

		UserEntity find = entityManager.find(UserEntity.class, user1.getId());

		Assert.assertThat(find.getId(), Is.is(user1.getId()));
		Assert.assertThat(find.getName(), Is.is(user1.getName()));

	}


	@Test
	public void testUpdate() throws Exception {


		UserEntity user1 = new UserEntity("test");

		logger.info(String.format("before persistent %s", user1.toString()));
		transaction.begin();
		entityManager.persist(user1);
		transaction.commit();
		logger.info(String.format("after persistent %s", user1.toString()));

		entityManager.getTransaction().begin();

		UserEntity find = entityManager.find(UserEntity.class, user1.getId());
		find.setName("new test name");
		entityManager.getTransaction().commit();
		UserEntity find2 = entityManager.find(UserEntity.class, user1.getId());
		Assert.assertThat(find2.getName(), Is.is(find.getName()));

	}

	@Test
	public void testDelete() throws Exception {


		UserEntity user1 = new UserEntity("test");

		logger.info(String.format("before persistent %s", user1.toString()));
		transaction.begin();
		entityManager.persist(user1);
		transaction.commit();
		logger.info(String.format("after persistent %s", user1.toString()));

		entityManager.getTransaction().begin();

		UserEntity find = entityManager.find(UserEntity.class, user1.getId());
		entityManager.remove(find);
		entityManager.getTransaction().commit();
		UserEntity find2 = entityManager.find(UserEntity.class, user1.getId());
		Assert.assertNull(find2);

	}


	@Test
	public void testInheritance() throws Exception {











	}


	/**
	 *
	 * 关系的建立可以使用
	 * 1. 外键 @JoinColumn
	 * 2. 连接表 @joinTable
	 *
	 *
	 *
	 * @throws Exception
	 */
	@Test
	public void testOneToOne() throws Exception {


		transaction.begin();


		UserEntity test = new UserEntity("test");
		OrganizationEntity entity = new OrganizationEntity("test_org");

		test.setOrganization(entity);



		entityManager.persist(entity);
		entityManager.persist(test);

		transaction.commit();


		UserEntity inserted = entityManager.find(test.getClass(), test.getId());


		Assert.assertNotNull(inserted.getOrganization());
		Assert.assertThat(inserted.getOrganization().getName(), Is.is(entity.getName()));


	}


	@Test
	public void testOneToMany() throws Exception {


		transaction.begin();


		UserEntity test = new UserEntity("test");
		UserEntity test2 = new UserEntity("test2");
		OrganizationEntity entity = new OrganizationEntity("test_org");

		entityManager.persist(test);
		entityManager.persist(test2);

		entityManager.persist(entity);

		entity.setUsers(new LinkedHashSet<UserEntity>(Arrays.asList(test, test2)));



		transaction.commit();


		OrganizationEntity inserted = entityManager.find(entity.getClass(), entity.getId());


		Assert.assertNotNull(inserted.getUsers());
		Assert.assertThat(inserted.getUsers().size(), Is.is(2));


	}





}
