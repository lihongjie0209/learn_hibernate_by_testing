package cn.lihongjie.entity.xml;

import com.google.common.base.Objects;

/**
 * @author 982264618@qq.com
 */
public class UserEntity {
	private long id;
	private String name;
	private String email;
	private String gender;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		UserEntity that = (UserEntity) object;
		return id == that.id &&
				Objects.equal(name, that.name) &&
				Objects.equal(email, that.email) &&
				Objects.equal(gender, that.gender);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name, email, gender);
	}
}
