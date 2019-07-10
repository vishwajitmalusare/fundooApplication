package com.bridgelabz.fundoonotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>{
	
	public Label findByLabelIdAndUserId(long LabelId,long userId);
	
	public Optional<Label> findByUserIdAndLabelName(long userId,String labelName);
	
	public List<Label> findByUserId(long UserId);
}