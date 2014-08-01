package org.jboss.migration.registration;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Named("user")
@Model
@Table(name="users")
public class User implements Serializable {

	private static final long serialVersionUID = 6172827713562877957L;
	private String username;
	private String password;
	private String name;

	public User() {}
	
	public User(String name, String password, String username) {
		this.name = name;
		this.password = password;
		this.username = username;
	}
	
	@Id @NotNull @Size(min=5, max=15)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}   
	
	@NotNull @Size(min=5, max=15)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
	@Override
	public String toString() {
		return "User(" + username + ")";
	}
}
