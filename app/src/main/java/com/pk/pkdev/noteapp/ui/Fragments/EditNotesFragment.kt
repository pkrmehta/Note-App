package com.pk.pkdev.noteapp.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pk.pkdev.noteapp.Model.Notes
import com.pk.pkdev.noteapp.R
import com.pk.pkdev.noteapp.ViewModel.NotesViewModel
import com.pk.pkdev.noteapp.databinding.FragmentEditNotesBinding
import java.util.*


class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentEditNotesBinding
    private lateinit var priority: String
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.edtSubtitle.setText(oldNotes.data.subtitle)
        binding.edtNotes.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            }
            "2" ->{
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            }
            "3" ->{
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
            }
        }

        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
        }

        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.btnSaveNotes.setOnClickListener {
            updateNotes(it)
        }


        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)

        val data = Notes(
            id = oldNotes.data.id,
            title = title,
            subtitle = subtitle,
            notes = notes,
            priority = priority,
            date = notesDate.toString())
        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment2_to_homeFragment2)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)

                bottomSheet.dismiss()

                //Navigation.findNavController(it).navigate(R.id.action_editNotesFragment2_to_homeFragment2)
                Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_editNotesFragment2_to_homeFragment2)

                Toast.makeText(requireContext(), "${oldNotes.data.title} Note have been deleted", Toast.LENGTH_SHORT).show()
                
            }


            bottomSheet.show()
        }
        else if(item.itemId == android.R.id.home){
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_editNotesFragment2_to_homeFragment2)
        }
        return super.onOptionsItemSelected(item)
    }


}