package com.app.data.entity;

import com.app.data.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends CommonEntity {
	private static final long serialVersionUID = 8441712671336772605L;

	@Enumerated(EnumType.STRING)
	@Column(name = "code")
	private RoleEnum code;

	@Column(name = "description")
	private String description;

}
