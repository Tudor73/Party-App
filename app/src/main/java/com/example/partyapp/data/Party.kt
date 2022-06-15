package com.example.partyapp.data

import java.time.LocalDate
import java.time.LocalDateTime


data class Party(
    var title : String="",
    var address: String = "",
    var date: String = "",
    var organizer: User = User(),
    private val invites : ArrayList<User> = ArrayList<User>(),
    private val shoppingList: HashMap<String, Int> = HashMap<String, Int>()

){

    fun addInvitation(person : User) {
        this.invites.add(person)
    }

    fun addItemToShoppingList(item : String , price : Int) {
        this.shoppingList.put(item, price)
    }
}
