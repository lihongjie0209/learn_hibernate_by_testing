package cn.lihongjie.entity.xml;

import javax.persistence.Entity;

/**
 * @author 982264618@qq.com
 */
public class IdentityGenEntity {


	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("IdentityGenEntity{");
		sb.append("id=").append(id);
		sb.append('}');
		return sb.toString();
	}
}
