package com.breathebinary.finava.dto

import org.springframework.security.core.GrantedAuthority

class UserPrincipal(userName:String, password:String, val passwordD:String, authorities:Collection<out GrantedAuthority>): org.springframework.security.core.userdetails.User(userName,password,authorities) {


}
