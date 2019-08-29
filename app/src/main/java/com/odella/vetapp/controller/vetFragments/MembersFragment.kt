package com.odella.vetapp.controller.vetFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.odella.vetapp.R
import com.odella.vetapp.adapters.UsersAdapter
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.Owner
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_members.*
import kotlinx.android.synthetic.main.fragment_members.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MembersFragment : Fragment() {
    lateinit var adapter: UsersAdapter
    lateinit var model: VetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]
        //MEME

        if(model.ownersList.isNullOrEmpty()) {
            view.isClickable = false
            NetworkService.create().getOwners(UserSingleton.actualToken)
                .enqueue(object : Callback<List<Owner>> {
                    override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                        view.isClickable = true
                        view.progressBar.visibility = View.GONE
                        Toast.makeText(context!!, "Could not load data. Sorry", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<List<Owner>>,
                        response: Response<List<Owner>>
                    ) {
                        view.isClickable = true
                        view.progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            model.ownersList = response.body()!!
                            loadData()
                        } else {
                            Toast.makeText(
                                context!!,
                                "Server returned error code ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        } else {
            view.isClickable = true
            view.progressBar.visibility = View.GONE
            loadData()
        }
        //MEME TERMINA
    }
    fun showNoData(){
        fragment_members_nodata.visibility = View.VISIBLE
        fragment_members_recycler.visibility = View.INVISIBLE
    }
    fun loadData(){
        if(model.ownersList.isNullOrEmpty()) showNoData()
        else {
            adapter = UsersAdapter(context!!) {
                Toast.makeText(
                    context!!,
                    "You clicked on ${it.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            adapter.setItems(model.ownersList!!)
            view!!.fragment_members_recycler.adapter = adapter
            var lm = LinearLayoutManager(
                context!!,
                LinearLayoutManager.VERTICAL,
                false
            )
            var decor = DividerItemDecoration(context!!, lm.orientation)
            view!!.fragment_members_recycler.layoutManager = LinearLayoutManager(
                context!!,
                LinearLayoutManager.VERTICAL,
                false
            )
            view!!.fragment_members_recycler.addItemDecoration(decor)

            view!!.fragment_members_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filterByName(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filterByName(newText)
                    return false
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MembersFragment()
    }
}
