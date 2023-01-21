package com.pk.pkdev.noteapp.Repository

import androidx.lifecycle.LiveData
import com.pk.pkdev.noteapp.Dao.NotesDao
import com.pk.pkdev.noteapp.Model.Notes

class NotesRepository(val dao: NotesDao) {

    fun getAllNotes(): LiveData<List<Notes>>{
        return dao.getNotes()
    }

    fun getHighNotes(): LiveData<List<Notes>>{
        return dao.getHighNotes()
    }

    fun getLowNotes(): LiveData<List<Notes>>{
        return dao.getLowNotes()
    }

    fun getMediumNotes(): LiveData<List<Notes>>{
        return dao.getMediumNotes()
    }

    fun insertNotes(notes: Notes){
        dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int){
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        dao.updateNotes(notes)
    }

}