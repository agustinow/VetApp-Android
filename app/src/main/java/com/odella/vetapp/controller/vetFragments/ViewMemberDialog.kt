package com.odella.vetapp.controller.vetFragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.odella.vetapp.R
import com.odella.vetapp.constants.MEMBER_ID
import com.odella.vetapp.constants.formatDate
import com.odella.vetapp.model.Owner
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.activity_view_member_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMemberDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_member_dialog)

        val id = intent.getStringExtra(MEMBER_ID)
        val call = NetworkService.create().getOwner(id).also {
            it.enqueue(object : Callback<Owner>{
                override fun onFailure(call: Call<Owner>, t: Throwable) {
                }

                override fun onResponse(call: Call<Owner>, response: Response<Owner>) {
                    if (response.isSuccessful) {
                        val member = response.body()
                        if(member!= null){
                            txtMemberName.text=member.name.toString()
                            txtMemberId.text=member.memberID.toString()
                            txtMemberAddress.text=member.address.toString()
                            txtMemberEmail.text=member.email.toString()
                            txtMemberPhone.text=member.cellPhone.toString()
                            txtMemberHomePhone.text=member.homePhone.toString()
                            txtMemberStart.text= formatDate(member.memberStart!!)
                            Glide.with(this@ViewMemberDialog).load(member.image).into(imgMember)

                        }
                    }

                }

            })
        }
    }
}
