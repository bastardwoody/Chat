class Service {
    private var chats = mutableMapOf<Companion, List<Message>>()

    fun sendMessage(companion: Companion, message: Message): Int {
        if (chats.containsKey(companion)) {
            chats.forEach {
                if (it.key.id == companion.id) chats[companion] = it.value.plus(message)
            }
        } else {
            chats.keys.plus(companion)
            chats[companion] = emptyList()
            sendMessage(companion, message)
        }
        return chats.size
    }

//    fun deleteMessage(message: Message): Boolean {
//        for ((key, values) in chats)
//            if (values.contains(message)) {
//                chats[key] = values.minus(message)
//                if (values.isEmpty()) deleteChat(key.name)
//                return true
//            }
//        throw NotFoundException("Сообщение ${message.id} не найдено")
//    }

    fun deleteMessage(message: Message): Boolean {
        chats.forEach {
            if (it.value.contains(message)) it.value.minus(message)
            else throw NotFoundException("Сообщение ${message.id} не найдено")
            if (it.value.isEmpty()) deleteChat(it.key.name)
        }
        return true
    }

    fun deleteChat(companion: String): Boolean {
        chats.keys.forEach { key ->
            if (key.name == companion) {
                chats.remove(key)
                return true
            }
        }
        throw NotFoundException("Чат с $companion не найден")
    }

    fun getUnreadChats(): Int {
        var count = 0
        chats.values.forEach { values ->
            for (value in values)
                if (!value.isRead) {
                    count++
                    break
                }
        }
        return count
    }

//    fun getUnreadChats(): Int {
//        var count = 0
//        chats.values.forEach { values ->
//            values.forEach { value ->
//                if (!value.isRead)
//                    count++
//            }
//        }
//        return count
//    }


    fun getChats(): List<String> {
        val companion = emptyList<String>().toMutableList()
        chats.forEach {
            companion += buildString {
                append(it.key.name)
                append(" - ")
                append(if (!it.value.last().isRead) it.value.last().message else "нет непрочитанных сообщений")
            }
        }
        return companion
    }

    fun getChatMessages(chatId: Int, messageId: Int, amount: Int): List<String> {
        val list = emptyList<String>().toMutableList()
        chats.forEach { (key, values) ->
            if (key.id == chatId) if (values.size < amount + 1) values.subList(values.indexOfFirst { it.id == messageId },
                values.size).forEach {
                it.isRead = true
                list += it.message
            } else values.subList(values.indexOfFirst { it.id == messageId }, amount + 1).forEach {
                it.isRead = true
                list += it.message
            }
        }
        return list
    }

//    fun getChatMessages(chatId: Int, messageId: Int, amount: Int): String {
//        val exit = chats
//            .filter { it.key.id == chatId }
//            .sublist
//            .forEach { list ->
//                if (list.size < amount)
//                    list.subList(list.indexOfFirst { it.id == messageId }, list.size)
//                else list.subList(list.indexOfFirst { it.id == messageId }, amount + 1)
//            }
//        return exit.toString()
//    }
}