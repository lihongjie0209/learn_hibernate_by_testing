package cn.lihongjie.entity.jpa;

import javax.persistence.*;
import java.lang.annotation.Target;

/**
 * @author 982264618@qq.com
 */

@Entity
@Table(name = "j_user")
public class UserEntity extends BaseEntity {




	private String name;

	@OneToOne
	@JoinColumn(name = "org_id")
	private OrganizationEntity organization;


	public UserEntity() {
	}

	public UserEntity(String name) {

		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserEntity{");
		sb.append("id=").append(getId());
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}
}
