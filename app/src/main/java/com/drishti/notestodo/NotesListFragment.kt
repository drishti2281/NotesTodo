package com.drishti.notestodo

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.drishti.notestodo.R
import com.drishti.notestodo.databinding.FragmentNotesListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val TAG = this.javaClass.simpleName
    var binding: FragmentNotesListBinding? = null
    lateinit var mainActivity: MainActivity
    var adapter: Adapter?=null
    val notesArrayList = arrayListOf<NotesEntity>()
    var userList =ArrayList<NotesEntity>()
    lateinit var  searchview: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentNotesListBinding.inflate(layoutInflater)
        mainActivity=activity as MainActivity
        adapter = Adapter(mainActivity, notesArrayList, object : clickinterface {
            override fun onClickListener(notes: NotesEntity) {
                val bundle= bundleOf()
                bundle.putInt("id", notes.id ?: -1)
                mainActivity.navController.navigate(R.id.addUpdateNotesFragment, bundle)
            }

            override fun onDeleteListener(notes: NotesEntity) {
//                //Toast.makeText(mainActivity, notes.title, Toast.LENGTH_SHORT).show()
                deleteUser(notes)
            }

            override fun onShareListener(notes: NotesEntity) {
                val text = "Hey Check out my note:" +
                        "Title: ${notes.title}\n" +
                        "Subtitle: ${notes.subtitle}\n\n" +
                        notes.notes
                val intent= Intent()
                intent.action= Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,text)
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))

            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNotesListBinding.inflate(layoutInflater,container,false)
//binding?.searchNotes\

        binding?.newNotesBtn?.setOnClickListener{
            mainActivity.navController.navigate(R.id.action_notesListFragment_to_addUpdateNotesFragment)
        }

        binding?.notesRecycler?.layoutManager=
            LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false)
        // binding?.notesRecycle?.layoutManager=LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
//        binding?.notesRecycler?.layoutManager= GridLayoutManager(mainActivity,4)
        binding?.notesRecycler?.adapter= adapter
//        binding?.btnAdd?.setOnClickListener {
//            adapter?.add("Bhawna")
//        }

        return binding?.root
    }




    fun filterList(query: String?) {
        if (query != null) {
            val filter = ArrayList<NotesEntity>()
            for (i in notesArrayList) {
                if (i.title?.lowercase()?.contains(query) == true || i.subtitle?.lowercase()?.contains(query) == true) {
                    filter.add(i)
                }
            }
            if (filter.isEmpty()) {
                Toast.makeText(mainActivity, "No Data Found", Toast.LENGTH_SHORT).show()
            }
            adapter?.filteredList(filter)
        }
    }


    override fun onResume() {
        super.onResume()
        getNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun getNotes() {
        class getNotes : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                notesArrayList.clear()
                notesArrayList.addAll(mainActivity.database.notesDao().getAll() as ArrayList<NotesEntity>)
                Log.e(TAG, notesArrayList.size.toString())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter?.notifyDataSetChanged()
            }
        }
        getNotes().execute()
    }

    private fun deleteUser(notes: NotesEntity) {
        class DeleteUser : AsyncTask<NotesEntity, Void, Void>(){
            override fun doInBackground(vararg p0: NotesEntity?): Void? {
                mainActivity.database.notesDao().deleteNote(notes)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                getNotes()
            }
        }
        DeleteUser().execute()
    }
    fun updateAppBarTitle(title:String){
        binding?.tvtoolbar?.text = title
    }
}


