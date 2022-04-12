package org.wit.recyclebeev1

data class User(
    val username: String? =null,
    val email : String? = null,
    val password : String?= null,
    val firstName : String?=null,
    val lastName : String?=null,
    val eircode : String? =null,
    val businessName : String? = null,
    val busAddress : String? = null,
    val businessBio : String? = null
//boolean
//if bus user we can go to same acitivty but theyll see different things
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

