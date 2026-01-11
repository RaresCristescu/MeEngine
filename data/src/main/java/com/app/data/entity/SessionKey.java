package com.app.data.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "session_key")
public class SessionKey implements Serializable {
	private static final long serialVersionUID = -8611499897447549472L;
	
	@Id
	protected UUID id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "public_key")
	private String publicKey;

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
}
