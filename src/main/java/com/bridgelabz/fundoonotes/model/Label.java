
package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Label implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;
	@NotNull
	@NotEmpty(message = "label name should not be empty")
	private String labelName;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
	private long userId;

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public LocalDateTime getCreated() {
		return createDate;
	}

	public void setCreated(LocalDateTime created) {
		this.createDate = created;
	}

	public LocalDateTime getModified() {
		return modifiedDate;
	}

	public void setModified(LocalDateTime modified) {
		this.modifiedDate = modified;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", created=" + createDate + ", modified="
				+ modifiedDate + ", userId=" + userId + ", Notes=" + Notes + "]";
	}

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Note> Notes;

	public List<Note> getNotes() {
		return Notes;
	}

	public void setNotes(List<Note> notes) {
		Notes = notes;
	}                    

}
