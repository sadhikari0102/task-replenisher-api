package com.walmart.replenisher.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@NotNull
	@Column(name = "NAME", unique = true)
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ROLE")
	private Role role;

	public User() {
		
	}
	
	public User(User user) {
		this.id = user.getId();
		this.password =  user.getPassword();
		this.name = user.getName();
		this.role = user.getRole();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}	
	
}
