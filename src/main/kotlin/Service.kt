import java.lang.Integer.min

class Service {
    private var chats = mutableMapOf<Companion, List<Message>>()

    fun sendMessage(companion: Companion, message: Message): MutableMap<Companion, List<Message>> =
        chats.putOrCreate(companion, message)

    fun deleteMessage(message: Message) = chats.entries
        .firstOrNull { it.key.id == message.ownerId }
        ?.let { mutableMapOf(it.key to it.value.minus(message)) }
//        ?.let { it.values.ifEmpty { deleteChat(it.keys.last()) } }
        ?: throw NotFoundException("Сообщение ${message.id} не найдено")

    fun deleteChat(companion: Companion): Boolean = chats.entries
        .removeIf { it.key.id == companion.id }

    fun getUnreadChats(): Int = chats.entries
        .count { map -> map.value.any { !it.isRead } }

    fun getChats() = chats.entries.asSequence()
        .map { it.key.name to
                    if (it.value.last().isRead)
                        "Нет непрочитанных сообщений"
                    else it.value.last().message
        }.toMap()

    fun getChatMessages(chatId: Int, messageId: Int, amount: Int) = chats.entries
        .find { it.key.id == chatId }?.value.orEmpty()
        .let { messages -> messages.indexOfFirst { it.id == messageId } to messages }
        .let { (index, list) -> list.subList(index, min(index + amount, list.size)) }
        .onEach { it.isRead = true }
        .map { it.message }
}

// Оставил старый метод для тренировки с функцией расширения
private fun MutableMap<Companion, List<Message>>.putOrCreate(companion: Companion, message: Message): MutableMap<Companion, List<Message>> {
    message.ownerId = companion.id
    if (this.containsKey(companion)) {
        this.forEach {
            if (it.key.id == companion.id) this[companion] = it.value.plus(message)
        }
    } else {
        this.keys.plus(companion)
        this[companion] = emptyList()
        putOrCreate(companion, message)
    }
    return this
}