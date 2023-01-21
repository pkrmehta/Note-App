package com.pk.pkdev.noteapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pk.pkdev.noteapp.Database.NotesDatabase
import com.pk.pkdev.noteapp.Model.Notes
import com.pk.pkdev.noteapp.Repository.NotesRepository

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).getNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }

    fun getNotes() : LiveData<List<Notes>> = repository.getAllNotes()

    fun getHighNotes() : LiveData<List<Notes>> = repository.getHighNotes()

    fun getLowNotes() : LiveData<List<Notes>> = repository.getLowNotes()

    fun getMediumNotes() : LiveData<List<Notes>> = repository.getMediumNotes()

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }

}