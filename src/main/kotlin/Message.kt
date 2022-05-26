data class Message(
    val id: Int = (0..99).random(),
    var ownerId: Int = (0..3).random(),
    val companionId: Int = (0..3).random(),
    val date: Int = (0..99).random(),
    val message: String = listOf("Агенты!", "Тук-Тук", "Оператор!", "Тэнк, вытащи нас отсюда", "Избранный", "Морфеус верит в тебя").random(),
    var isRead: Boolean = listOf(true, false).random()
)