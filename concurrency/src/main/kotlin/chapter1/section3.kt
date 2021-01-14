package chapter1

fun main() {
    val words = listOf("level", "pope", "needle", "Anna", "Pete", "noon", "stats")
    filterPalindromes(words = words).forEach {
        println(it)
    }
}

// CPU 바운드 알고리즘
fun isPalindrome(word: String): Boolean {
    val lcWord = word.toLowerCase()
    return lcWord == lcWord.reversed()
}

fun filterPalindromes(words: List<String>): List<String> {
    return words.filter { isPalindrome(word = it) }
}