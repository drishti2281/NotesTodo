package com.drishti.notestodo

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.drishti.notestodo.R
import com.drishti.notestodo.databinding.FragmentAddUpdateNotesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddUpdateNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddUpdateNotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var mainActivity: MainActivity
    var binding: FragmentAddUpdateNotesBinding? = null
    var adapter: Adapter? = null
    val arrayListNotes = arrayListOf<NotesEntity>()
    private var notesID: Int = -1
    var isUpdate = false

    var sharedPreferences: SharedPreferences? = null
    var sharedPreferencesEditor: SharedPreferences.Editor? = null

    private var notes: NotesEntity = NotesEntity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            notesID = it.getInt("id", -1)
            isUpdate = true
            getNotesByID()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUpdateNotesBinding.inflate(layoutInflater)
        binding?.btnSaveNotes?.setOnClickListener {
            val note = NotesEntity()
            note.title = binding?.etTitle?.text.toString() ?: ""
            note.subtitle = binding?.etSubtitle?.text.toString() ?: ""
            note.notes = binding?.etNotes?.text.toString()
            if (isUpdate) {
                note.id = notesID
                updateNotes(note)
            } else {
                createNote(note)
            }
        }
        return binding?.root
    }

    fun createNote(note: NotesEntity) {
        class GetTask : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                mainActivity.database.notesDao().insertNotes(note)

                return null
            }
            override fun onPostExecute(result:Void?) {
                super.onPostExecute(result)
                findNavController().navigate(R.id.notesListFragment)
                Toast.makeText(requireContext(),"Successfully Added", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        GetTask().execute()
    }
    private fun getNotesByID() {
        class GetNotes: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                notes =  mainActivity.database.notesDao().findUserByID(notesID) ?: NotesEntity()
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.etTitle?.setText(notes.title)
                binding?.etSubtitle?.setText(notes.subtitle)
                binding?.etNotes?.setText(notes.notes)
            }
        }
        GetNotes().execute()
    }

    private fun updateNotes(note: NotesEntity) {
        class UpdateNotes: AsyncTask<NotesEntity, Void, Void>(){
            override fun doInBackground(vararg p0: NotesEntity?): Void? {
                return  null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(mainActivity,
                    resources.getString(com.drishti.notestodo.R.string.update), Toast.LENGTH_LONG).show()
                mainActivity.navController.popBackStack()
            }
        }
        UpdateNotes().execute(notes)
    }
    fun updateAppBarTitle(title:String){
        binding?.tvtoolbar?.text = title
    }
}
