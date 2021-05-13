package com.breathebinary.finava.services

import com.breathebinary.finava.contracts.User
import com.breathebinary.finava.dto.UserDTO
import com.breathebinary.finava.dto.UserPrincipal
import com.breathebinary.finava.security.jwt.TokenProvider
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import java.lang.RuntimeException


@Service
class IdentityService {
    private var contactAddress: String = "0x59b670e9fA9D0A427751Af201D676719a970857b"
    private var accountAddress: String = "0xf39fd6e51aad88f6f4ce6ab8827279cfffb92266"
    private var accountSecretKey: String = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80"
    private var networkUrl: String = "http://localhost:8545"
    private var web3: Web3j? = null
    private var user: User? = null


    init {
        web3 = Web3j.build(HttpService(networkUrl))
        val credentials = Credentials.create(accountSecretKey)
        user = User.load(contactAddress, web3, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT)
    }

    public fun addUser(userdto: UserDTO) {
        user?.addUser(
            userdto.userName, userdto.password, userdto.firstName,
            userdto.middleName, userdto.lastName, userdto.occupation,
            userdto.landNumber, userdto.mobileNumber, userdto.dateofAppl,
            userdto.dateofReg, userdto.nominalRoll
        )?.send()
        user?.addUserAddress(
            userdto.userName, userdto.addressLine1, userdto.addressLine2,
            userdto.addressLine3, userdto.pin, userdto.street,
            userdto.city, userdto.state, userdto.country
        )?.send()
    }

    public fun getUser(userName: String): UserDTO {
        val getUserTask = user?.getUser(userName)?.send()
        val getUserAddressTask = user?.getUserAddress(userName)?.send()
        val userdto: UserDTO = UserDTO(
            userName, "", getUserTask?.get(0).toString(), getUserTask?.get(1).toString(), getUserTask?.get(2).toString(),
            getUserTask?.get(3).toString(), getUserTask?.get(4).toString(), getUserTask?.get(5).toString(), getUserTask?.get(6).toString(), getUserTask?.get(7).toString(),
            getUserTask?.get(8).toString(), getUserAddressTask?.get(0).toString(), getUserAddressTask?.get(1).toString(), getUserAddressTask?.get(2).toString(),
            getUserAddressTask?.get(3).toString(), getUserAddressTask?.get(4).toString(), getUserAddressTask?.get(5).toString(), getUserAddressTask?.get(6).toString(), getUserAddressTask?.get(7).toString()
        )
        return userdto
    }

    public fun authenticate(userName: String, password: String): String? {
        val tokenProvider = TokenProvider()
        try{
            val validateUserTask = user?.validateUser(userName, password)?.send();
            val result: String = validateUserTask.toString();
            if(result.equals("user authenticated")) {
                val roles: List<GrantedAuthority> = ArrayList()
                val userDetails = org.springframework.security.core.userdetails.User(
                    userName,
                    password,
                    roles
                )
                val auth: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, null)
                return tokenProvider.createToken(auth, false);
            } else {
                throw java.lang.Exception("Invalid Username or Password")
            }
        } catch (ex:Exception){
            throw ex
        }
        return null
    }
}
