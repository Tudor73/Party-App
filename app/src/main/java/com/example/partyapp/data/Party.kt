package com.example.partyapp.data


data class Party(
    var name : String = "",
    var id : Int = 0,
    private val invites : ArrayList<String> = ArrayList<String>(),
    private val shoppingList: HashMap<String, Int> = HashMap<String, Int>(),

){
    init{
        this.id = counter
        increase()
    }
    companion object Counter{
        private var counter : Int = 0
        fun increase() : Int = counter ++
    }

    fun addInvitation(person : String) {
        this.invites.add(person)
    }

    fun addItemToShoppingList(item : String , price : Int) {
        this.shoppingList.put(item, price)
    }
}
