package com.mark.ota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mark.ota.entity.Note;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private int id = 0;

    private String noteNotFoundMessage = "Note not found.";

    private List<Note> notes = new ArrayList<>();

    public int createNote(Note note) {

        if (note.getTitle()
            .isBlank()) {
            throw new NullPointerException("Note's title can't be empty.");
        }

        if (note.getBody()
            .isBlank()) {
            throw new NullPointerException("Note's body can't be empty.");
        }

        id++;

        note.setId(id);

        notes.add(note);

        return id;
    }

    public List<Note> retrieveAllNotes() {

        return notes;
    }

    public Note retrieveNoteById(int id) {

        Note matchedNote = notes.stream()
            .filter(note -> id == note.getId())
            .findAny()
            .orElse(null);

        if (Objects.isNull(matchedNote)) {
            throw new NullPointerException(noteNotFoundMessage);
        }

        return matchedNote;
    }

    public Note updateNoteById(int id, Note newNote) {

        Note noteToBeUpdated = notes.stream()
            .filter(note -> id == note.getId())
            .findAny()
            .orElse(null);

        if (Objects.isNull(noteToBeUpdated)) {
            throw new NullPointerException(noteNotFoundMessage);
        }

        int existingNoteIndex = notes.indexOf(noteToBeUpdated);

        noteToBeUpdated.setTitle(newNote.getTitle());
        noteToBeUpdated.setBody(newNote.getBody());

        notes.set(existingNoteIndex, noteToBeUpdated);

        return noteToBeUpdated;
    }

    public boolean deleteNoteById(int id) {

        Note noteToBeDeleted = notes.stream()
            .filter(note -> id == note.getId())
            .findAny()
            .orElse(null);

        if (Objects.isNull(noteToBeDeleted)) {
            throw new NullPointerException(noteNotFoundMessage);
        }

        return notes.remove(noteToBeDeleted);
    }
}
