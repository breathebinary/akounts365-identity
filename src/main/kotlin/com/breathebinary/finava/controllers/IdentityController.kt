package com.breathebinary.finava.controllers

import com.breathebinary.finava.dto.UserCredentialsDTO
import com.breathebinary.finava.dto.UserDTO
import com.breathebinary.finava.security.getCurrentUserLogin
import com.breathebinary.finava.services.IdentityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class IdentityController(@Autowired private val identityService: IdentityService) {


    @PostMapping("/register")
    public fun saveUser(@RequestBody userdto: UserDTO): String {
        identityService.addUser(userdto)
        return "success"
    }

    @PostMapping("/login")
    public fun loginUser(@RequestBody userCredetialsDTO: UserCredentialsDTO): String? {
       return identityService.authenticate(userCredetialsDTO.userName, userCredetialsDTO.password)
    }


    @PostMapping("/get")
    public fun getUser(): UserDTO? {
        try{
            val userName: String = getCurrentUserLogin().orElse("")
            return identityService.getUser(userName)
        } catch (ex:Exception){
            throw ex
        }
        return null
    }
}
