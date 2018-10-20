package cn.lihongjie.entity.relation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 982264618@qq.com
 */
public class OrganizationEntity {


	private long id;

	private Set<UserEntity> users = new LinkedHashSet<UserEntity>();


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

	public UserEntity addUser() {
		UserEntity userEntity = new UserEntity();
		users.add(userEntity);
		return
		userEntity;
	}
}
