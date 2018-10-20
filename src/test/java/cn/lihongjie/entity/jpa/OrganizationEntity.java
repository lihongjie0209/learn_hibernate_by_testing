package cn.lihongjie.entity.jpa;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 982264618@qq.com
 */

@Entity
@Table(name = "j_organization")
public class OrganizationEntity extends BaseEntity{




	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "org_id")
	private Set<UserEntity> users;

	public OrganizationEntity() {
	}

	public OrganizationEntity(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}
}
