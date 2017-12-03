package com.serzh.data

//If you declare all parameters as val you get an immutable class similar to the Lombok @Value annotation, only better. Regardless of if you use var or val (or a mix) for your data class, you get toString, hashCode/equals, copying and destructuring included
data class User(val name: String = "", val email: String = "", val id: Int = -1);