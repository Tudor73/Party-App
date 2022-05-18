package com.example.partyapp.data


data class Party(
    val id : Int,
    var name : String = "",
    private val invites : ArrayList<String> = ArrayList<String>(),
    private val shoppingList: HashMap<String, Int> = HashMap<String, Int>()

){

    fun addInvitation(person : String) {
        this.invites.add(person)
    }

    fun addItemToShoppingList(item : String , price : Int) {
        this.shoppingList.put(item, price)
    }
}
