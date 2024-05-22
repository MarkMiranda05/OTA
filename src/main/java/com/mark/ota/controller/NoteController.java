package com.mark.ota.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mark.ota.entity.Note;
import com.mark.ota.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    private ObjectWriter writer = new ObjectMapper().writer()
        .withDefaultPrettyPrinter();

    @Autowired
    private NoteService noteService;

    @PostMapping("/notes")
    public ResponseEntity<String> addNote(@RequestBody Note note) {

        try {
            int newNoteId = noteService.createNote(note);

            return new ResponseEntity<>("New note added with id: " + newNoteId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notes")
    public ResponseEntity<String> getAllNotes() {

        try {
            List<Note> notes = noteService.retrieveAllNotes();

            if (notes.isEmpty()) {
                return new ResponseEntity<>("Note not found.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(writer.writeValueAsString(notes), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<String> getNoteById(@PathVariable int id) {

        try {
            Note note = noteService.retrieveNoteById(id);

            return new ResponseEntity<>(writer.writeValueAsString(note), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<String> updateNoteById(@PathVariable int id, @RequestBody Note newNote) {

        try {
            Note note = noteService.updateNoteById(id, newNote);

            return new ResponseEntity<>(writer.writeValueAsString(note), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable int id) {

        try {
            boolean isNoteDeleted = noteService.deleteNoteById(id);

            if (isNoteDeleted) {
                return new ResponseEntity<>("Note with id: " + id + " successfully deleted.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete note with id: " + id + ".", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
