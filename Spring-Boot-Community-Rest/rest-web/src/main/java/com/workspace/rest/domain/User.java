package com.workspace.rest.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workspace.rest.domain.enums.SocialType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SocialType socialType;
	
	@Column
	private LocalDateTime createdDate;
	
	@Column
	private LocalDateTime updatedDate;

	@Builder
	public User(String name, String password, String email, SocialType socialType, LocalDateTime createdDate,
			LocalDateTime updatedDate) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.socialType = socialType;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

}
