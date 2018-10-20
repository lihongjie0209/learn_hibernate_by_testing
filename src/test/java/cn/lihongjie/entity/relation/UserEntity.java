package cn.lihongjie.entity.relation;

import java.util.Set;

/**
 * @author 982264618@qq.com
 */
public class UserEntity {

	private long id;

	private OrganizationEntity organization;

	private Set<RoleEntity> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}


	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
}
