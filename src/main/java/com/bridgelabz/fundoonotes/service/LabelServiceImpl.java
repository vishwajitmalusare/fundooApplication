package com.bridgelabz.fundoonotes.service;

//import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.exception.UserException;
//import com.sun.mail.iap.ResponseHandler;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.utility.ResponseHelper;
import com.bridgelabz.fundoonotes.utility.TokenUtil;

@Service("labelService")
public class LabelServiceImpl implements LabelService {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private TokenUtil userToken;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Override
	public Response createLabel(LabelDTO labelDto, String token) {
		// TODO Auto-generated method stub
		
		long userId=userToken.decodeToken(token);
		Optional<User> user=userRepository.findById(userId);
		
		if(!user.isPresent()) {
			throw new UserException(100,"Invalid User");
		}
		Optional<Label> labelPresent = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelPresent.isPresent()) {
			throw new UserException(101,"User laready Exist");
		}
		Label label = modelMapper.map(labelDto,Label.class);
		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);
		label.setCreated(LocalDateTime.now());
		label.setModified(LocalDateTime.now());
		user.get().getLabel().add(label);
		
		labelRepository.save(label);
		userRepository.save(user.get());
		
		Response response = ResponseHelper.statusResponse(200, environment.getProperty("status.label.created"));
		System.out.println("Back to service");
		return response;
	}

	@Override
	public Response updateLabel(LabelDTO labelDto, String token, long labelId) {
		// TODO Auto-generated method stub
		long userId =userToken.decodeToken(token);
		Optional<User> user= userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(100,"Invalid User ");
		}
		Label label =labelRepository.findByLabelIdAndUserId(labelId,userId);
		if(label== null) {
			throw new UserException(-6,"Label Not Exist");
		}
		Optional<Label> labelPresent = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelPresent.isPresent()) {
			throw new UserException(101,"label Already exist");
		}
		label.setLabelName(labelDto.getLabelName());
		label.setModified(LocalDateTime.now());
		labelRepository.save(label);
		Response response = ResponseHelper.statusResponse(300, environment.getProperty("status.label.updated"));	
		return response;
	}

	@Override
	public Response deleteLabel(String token, long labelId) {
		// TODO Auto-generated method stub
		long userId =userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(100,"Invalid User");
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label==null) {
			throw new UserException(101,"Label not exist");
		}
		
		labelRepository.delete(label);
 		Response response = ResponseHelper.statusResponse(400,environment.getProperty("status.label.deleted"));
 		return response;
	}
		@Override
	public List<Label> getAllLabelFromUser(String token) {
		// TODO Auto-generated method stub
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException("Invalis input ");
		}
		List<Label> labels = labelRepository.findByUserId(userId);
		List<Label> listLabel =  new ArrayList<>();
		for(Label noteLabel:labels) {
			Label labelDto = modelMapper.map(noteLabel, Label.class);
			listLabel.add(labelDto);
		}
			return listLabel;
	}	@Override
	public Response removeLabelFromNote(long labelId, String token, long noteId) {
		// TODO Auto-generated method stub
		long userId = userToken.decodeToken(token);
		Optional<User> user= userRepository.findById(userId);
		System.out.println("User is "+user);
		if(user==null) {
			throw new UserException(-6,"Invalid Input");
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		System.out.println("Label is "+label);
		
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);
		System.out.println("Note is "+note);
		if(note == null) {
			throw new UserException(-6,"Invalid label ");
		}
		label.setModified(LocalDateTime.now());
		labelRepository.delete(label);
		note.setModified(LocalDateTime.now());
		noteRepository.save(note);
		
		Response response = ResponseHelper.statusResponse(100, environment.getProperty("status.label.removed"));
		return response;
	}

	@Override
	public Response addLabelToNote(long labelId, String token, long noteId) {
		// TODO Auto-generated method stub
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(user== null) {
			throw new UserException(-6,"Invalid Input");
		}
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);
		if(note == null) {
			throw new UserException(101,"Invalid Note");
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new UserException(-6,"Invalid label");
		}
		note.getListLabel().add(label);
		note.setModified(LocalDateTime.now());
		label.getNotes().add(note);
		label.setModified(LocalDateTime.now());
		noteRepository.save(note);
		labelRepository.save(label);
		
		Response response = ResponseHelper.statusResponse(100, environment.getProperty("status.label.addnote"));
		return response;
	}

	@Override
	public List<LabelDTO> getLabelsOfNote(String token, long noteId) {
		// TODO Auto-generated method stub
		long userId= userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(user == null) {
			throw new UserException(-6,"User dose not exist ");
		}
		Optional<Note> note = noteRepository.findById(noteId);
		if(!note.isPresent()) {
			throw new UserException(-6,"Note Dose Not exist ");
		}
		List<Label> label = note.get().getListLabel();
		List<LabelDTO> listlabel = new ArrayList<>();
		for(Label noteLabel: label) {
			LabelDTO labeldto= modelMapper.map(noteLabel,LabelDTO.class);
			listlabel.add(labeldto);
			
		}
		return listlabel;
	}

	@Override
	public List<NoteDTO> getNotesOfLabel(String token, long labelId) {
		// TODO Auto-generated method stub
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6,"Invalid User");
		}
		
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new UserException(-6,"Invalid Label");
		}
		List<Note> notes = label.getNotes();
		List<NoteDTO> noteList = new ArrayList<>();
		for(Note note : notes) {
			NoteDTO noteDto= modelMapper.map(note, NoteDTO.class);
			noteList.add(noteDto);
		}
		return noteList;
	}

}	