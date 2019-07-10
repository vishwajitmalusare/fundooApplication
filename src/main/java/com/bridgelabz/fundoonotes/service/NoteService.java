package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.response.Response;

public interface NoteService {

	public Response createNote(NoteDTO notedto, String token);
	
	public Response updateNote(NoteDTO notedto, String token, long noteId);	
	
	public Response retrieveNote(String token, long noteId);
	
	public Response deleteNote(String token, long noteId);
	
	public Response deleteNotePermenantly(String token, long noteId);
	
	public Response checkPinOrNot(String token, long noteId);
	
	public List<Note> restoreTrashNotes(String token);
	
	public Response checkArchieveOrNot(String token, long noteId);
	
	public Response setColour(String token, long noteId, String color);
	
	public List<Note> getPinnedNote(String token);
	
	public List<Note> getArchievedNote(String token);
	
	public List<Note> sortByTitle(String token);
	
	public List<Note> sortByDate(String token);
	
	public Response addColleaborator(String token, String email, long noteId);
	
	public Response deleteCollaboratorToNote(String token, long noteId, String emialId);

	
	
}