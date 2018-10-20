package cn.lihongjie.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 982264618@qq.com
 */

@Entity
@Table(name = "j_role")
public class RoleEntity extends BaseEntity{




	private String name;

	public RoleEntity() {
	}

	public RoleEntity(String name) {
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
