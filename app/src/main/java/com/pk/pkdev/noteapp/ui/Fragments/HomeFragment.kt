package com.pk.pkdev.noteapp.ui.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.pk.pkdev.noteapp.Model.Notes
import com.pk.pkdev.noteapp.R
import com.pk.pkdev.noteapp.ViewModel.NotesViewModel
import com.pk.pkdev.noteapp.databinding.FragmentHomeBinding
import com.pk.pkdev.noteapp.ui.Adapter.NotesAdapter


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        viewModel.getNotes().observe(viewLifecycleOwner) {

            val notesList = it;
            oldMyNotes = notesList as ArrayList<Notes>
            binding.rvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            notesAdapter = NotesAdapter(requireContext(), notesList)
            binding.rvAllNotes.adapter = notesAdapter

        }

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) {
                val notesList = it;
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                notesAdapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = notesAdapter
            }
        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) {
                val notesList = it;
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                notesAdapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = notesAdapter
            }
        }

        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) {
                val notesList = it;
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                notesAdapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = notesAdapter
            }
        }

        binding.filterAll.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) {
                val notesList = it;
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                notesAdapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = notesAdapter
            }
        }


        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment2_to_createNotesFragment2)
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes here..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesFilter(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun notesFilter(newText: String?) {

        val newFilteredList = arrayListOf<Notes>()

        for(i in oldMyNotes){
            if(i.title.contains(newText!!) || i.subtitle.contains(newText!!)){
                newFilteredList.add(i)
            }
        }

        notesAdapter.filtering(newFilteredList)


    }
}