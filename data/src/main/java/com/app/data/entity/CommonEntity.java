package com.app.data.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public class CommonEntity implements Serializable {
	private static final long serialVersionUID = -4761954006481818259L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected UUID id;

	@Column(name = "creation", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected ZonedDateTime creation;

	@Column(name = "expires", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected ZonedDateTime expires;

	@Column(name = "modified", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected ZonedDateTime modified;

	@PrePersist
	public void prePersist() {
		if (this.creation == null) {
			this.creation = ZonedDateTime.now();
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (this.modified == null) {
			this.modified = ZonedDateTime.now();
		}
	}

	public CommonEntity(UUID id, ZonedDateTime creation, ZonedDateTime expires, ZonedDateTime modified) {
		this.id = id;
		this.creation = creation;
		this.expires = expires;
		this.modified = modified;
	}

	public CommonEntity() {
		this.creation = ZonedDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public ZonedDateTime getCreation() {
		return creation;
	}

	public void setCreation(ZonedDateTime creation) {
		this.creation = creation;
	}

	public ZonedDateTime getExpires() {
		return expires;
	}

	public void setExpires(ZonedDateTime expires) {
		this.expires = expires;
	}

	public ZonedDateTime getModified() {
		return modified;
	}

	public void setModified(ZonedDateTime modified) {
		this.modified = modified;
	}

}
