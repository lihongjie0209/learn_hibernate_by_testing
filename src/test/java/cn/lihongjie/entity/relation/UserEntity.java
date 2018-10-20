package cn.lihongjie.entity.relation;

/**
 * @author 982264618@qq.com
 */
public class UserEntity {

	private long id;

	private OrganizationEntity organization;

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


}
