package cn.lihongjie.entity.jpa;

import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import java.util.Date;

/**
 * 继承关系可以用以下方式实现
 * 1. 从上往下把继承树打平, 把所有的字段都记录在一张表中, 然后添加类型字段用于分别类型
 * 2. 从下往上, 把父类的字段都添加到子类中, 创建一张表
 *
 * 但是如果只是想共享一些属性, 那么不需要继承, 可以使用注解表明这些属性会被子类使用
 *
 *
 * @author 982264618@qq.com
 */

@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP")
	private Date createTime = new Date();


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
