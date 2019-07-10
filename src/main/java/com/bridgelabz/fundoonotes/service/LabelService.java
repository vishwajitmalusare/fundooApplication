package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.response.Response;

public interface LabelService {
	
	public Response createLabel(LabelDTO labelDto, String token);
	
	public Response updateLabel(LabelDTO labelDto,String token, long labelId);
	
	public  Response deleteLabel(String token, long labelId);
	
	public List<Label> getAllLabelFromUser(String token);
	
	public Response removeLabelFromNote(long labelId,String token, long noteId);
	
	public Response addLabelToNote(long labelId, String token, long noteId);
	
	public List<LabelDTO> getLabelsOfNote(String token, long noteId);
	
	public List<NoteDTO> getNotesOfLabel(String token, long labelId);

	
}