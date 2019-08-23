package com.odella.vetapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Pet {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Pet_ID")
    @Expose
    var petID: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Age")
    @Expose
    var age: String? = null
    @SerializedName("Genus")
    @Expose
    var genus: String? = null
    @SerializedName("Breed")
    @Expose
    var breed: String? = null
    @SerializedName("Gender")
    @Expose
    var gender: String? = null
    @SerializedName("Neutered")
    @Expose
    var neutered: Boolean? = null
    @SerializedName("Owner_ID")
    @Expose
    var ownerID: String? = null
    @SerializedName("Owner_Name")
    @Expose
    var ownerName: String? = null
}

class Owner {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Member_ID")
    @Expose
    var memberID: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Member_Start")
    @Expose
    var memberStart: String? = null
    @SerializedName("Cell_Phone")
    @Expose
    var cellPhone: String? = null
    @SerializedName("Home_Phone")
    @Expose
    var homePhone: String? = null
    @SerializedName("Email")
    @Expose
    var email: String? = null
    @SerializedName("Address")
    @Expose
    var address: String? = null
    @SerializedName("Username")
    @Expose
    var username: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
}

class Vet {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("ID")
    @Expose
    var iD: Int? = null
    @SerializedName("Specialty")
    @Expose
    var specialty: String? = null
    @SerializedName("Cell_Phone")
    @Expose
    var cellPhone: String? = null
    @SerializedName("On_Call")
    @Expose
    var onCall: String? = null
    @SerializedName("Time")
    @Expose
    var time: String? = null
    @SerializedName("Days_Off")
    @Expose
    var daysOff: String? = null
    @SerializedName("Username")
    @Expose
    var username: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
}

class Med {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Category")
    @Expose
    var category: String? = null
    @SerializedName("Pills")
    @Expose
    var pills: Int? = null
    @SerializedName("Volume")
    @Expose
    var volume: Int? = null
    @SerializedName("Concentration")
    @Expose
    var concentration: Int? = null
    @SerializedName("Price")
    @Expose
    var price: Float? = null
}

class Vacc {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Genus")
    @Expose
    var genus: String? = null
    @SerializedName("Price")
    @Expose
    var price: Float? = null
    @SerializedName("Dosis_Less_16_Weeks")
    @Expose
    var dosisLess16Weeks: String? = null
    @SerializedName("Dosis_More_16_Weeks")
    @Expose
    var dosisMore16Weeks: String? = null
    @SerializedName("Frequency")
    @Expose
    var frequency: String? = null
}

class Type {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("Number")
    @Expose
    var number: Int? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
}

class Payment {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("OwnerID")
    @Expose
    var ownerID: String? = null
    @SerializedName("Date")
    @Expose
    var date: String? = null
    @SerializedName("Amount")
    @Expose
    var amount: String? = null
    @SerializedName("Type")
    @Expose
    var type: Int? = null
    @SerializedName("IsPaid")
    @Expose
    var isPaid: Boolean? = null
}

class Consult {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("PetID")
    @Expose
    var petID: String? = null
    @SerializedName("VetID")
    @Expose
    var vetID: String? = null
    @SerializedName("Date")
    @Expose
    var date: Date? = null
    @SerializedName("Message")
    @Expose
    var message: String? = null
    @SerializedName("Meds")
    @Expose
    var meds: List<Med>? = null
    @SerializedName("Vaccs")
    @Expose
    var vaccs: List<Vacc>? = null
    @SerializedName("VetName")
    @Expose
    var vetName: String? = null
    @SerializedName("PetName")
    @Expose
    var petName: String? = null
}
class TokenResponse {
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
}