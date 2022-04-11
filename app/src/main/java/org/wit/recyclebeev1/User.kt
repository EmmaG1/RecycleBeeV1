package org.wit.recyclebeev1

data class User(
    val username: String? =null,
    val email : String? = null,
    val password : String?= null,
    val firstName : String?=null,
    val lastName : String?=null,
    val eircode : String? =null,
    //val uid: String? = null, //new
    val busName : String? = null, //new
    val busAddress : String? = null //new

    )
//
//class User {
//    var email = ""
//    var password = ""
//
//    constructor(email: String, password: String) {
//        this.email = email
//        this.password = password
//    }
//}

