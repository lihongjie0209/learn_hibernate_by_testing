package cn.lihongjie.entity.relation;

import java.util.Set;

/**
 * @author 982264618@qq.com
 */
public class RoleEntity {


	private long id;

	private Set<UserEntity> users;


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
}
