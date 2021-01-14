package chapter1

fun main() {

}

class Profile

suspend fun getProfile(id: Int): Profile {
    val basicUserInfo = getUserInfo(id = id)
    val contactInfo = getContactInfo(id = id)

    return createProfile(userInfo = basicUserInfo, contactInfo = contactInfo)
}

fun getUserInfo(id: Int): Int = 0
fun getContactInfo(id: Int): Int = 1

fun createProfile(userInfo: Int, contactInfo: Int): Profile = Profile()