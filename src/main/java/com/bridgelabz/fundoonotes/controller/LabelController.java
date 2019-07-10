package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
@RequestMapping("/user")
public class LabelController {
@Autowired
private LabelService labelService;

@PostMapping("/createLabel")
public ResponseEntity<Response> createLabel(@RequestBody LabelDTO labeldto, @RequestHeader String token){
	Response response = labelService.createLabel(labeldto, token);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}
@PutMapping("updatelabel")
public ResponseEntity<Response> updateLabel(@RequestBody LabelDTO labelDto,@RequestHeader String token,@RequestParam long labelId){
	Response response = labelService.updateLabel(labelDto, token, labelId);
	return new ResponseEntity<Response>(response,HttpStatus.ACCEPTED);
}

@PutMapping("/deleteLabel")
public ResponseEntity<Response> deleteLabel(@RequestHeader String token, @RequestParam long labelId){
	Response response = labelService.deleteLabel(token, labelId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}

@GetMapping("/getLabel")
List<Label>getLabel(@RequestHeader String token){
	List<Label> listLabel = labelService.getAllLabelFromUser(token);
	return listLabel;
}

@PutMapping("/removeLabelFromNote")
public ResponseEntity<Response> removeFromNote(@RequestParam long labelId, @RequestHeader String token,@RequestParam long noteId){
	Response response = labelService.removeLabelFromNote(labelId, token, noteId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}

@PutMapping("/addLabelToNote")
public ResponseEntity<Response> addLabelToNote(@RequestParam long labelId, @RequestHeader String token,@RequestParam long noteId){
	Response response = labelService.addLabelToNote(labelId, token, noteId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}
@GetMapping("/getlabelofnote")
List<LabelDTO> getLabelOfNote(@RequestHeader String token, @RequestParam long noteId){
	List<LabelDTO> listlabel = labelService.getLabelsOfNote(token, noteId);
	return listlabel;
}
@GetMapping("/getNoteoflabel")
List<NoteDTO> getNoteOfLabel(@RequestHeader String token,@RequestParam long labelId)throws IllegalArgumentException{
	List<NoteDTO> labellist=labelService.getNotesOfLabel(token, labelId);
	return labellist;
}
}
 