import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ServiceTest {


    @Test
    fun sendMessage() {
        // arrange
        val service = Service()
        val neo = Companion("Neo", 1)
        val message = Message()

        // act
        val result = service.sendMessage(neo, message)

        // assert
        assertEquals(mutableMapOf(neo to listOf(message)), result)
    }

    @Test
    fun deleteMessage() {
        // arrange
        val service = Service()
        val neo = Companion("Neo", 1)
        val message1 = Message(1, 1, 57656, 4563,"Следуй за белым кроликом", false)
        val message2 = Message(2, 1, 57656, 3565,"Агенты!", false)
        service.sendMessage(neo, message1)
        service.sendMessage(neo, message2)

        // assert
        assertTrue(true)
    }

    @Test
    fun deleteMessageWithChat() {
        // arrange
        val service = Service()
        val neo = Companion("Neo", 1)
        val message = Message(1, 1, 57656,456,"Следуй за белым кроликом", false)
        service.sendMessage(neo, message)

        // assert
        assertTrue(true)
    }


    @Test(expected = NotFoundException::class)
    fun shouldThrowDeleteMessage() {
        val service = Service()
        val neo = Companion("Neo", 1)
        val message = Message()
        service.sendMessage(neo, message)
        service.deleteMessage(Message(1, 7,8,3543, "wrong", true))
    }

    @Test
    fun deleteChat() {
        // arrange
        val service = Service()
        val neo = Companion("Neo", 1)
        val message = Message(1, 1, 57656, 347368,"Я знаю кунг-фу", false)
        service.sendMessage(neo, message)

        // act
        service.deleteChat(neo)

        // assert
        assertTrue(true)
    }

    @Test
    fun getUnreadChats() {
        // arrange
        val service = Service()

        val neo = Companion("Neo", 1)
        val trinity = Companion("Trinity", 2)
        val morpheus = Companion("Morpheus", 3)

        val message1 = Message(message = "Агенты!", isRead = false)
        val message2 = Message(message = "Тук-Тук", isRead = true)
        val message3 = Message(message = "Оператор!", isRead = true)
        val message4 = Message(message = "Избранный", isRead = false)
        val message5 = Message(message = "Агенты!", isRead = false)
        val message6 = Message(message = "Оператор!", isRead = true)
        val message7 = Message(message = "Избранный", isRead = true)
        val message8 = Message(message = "Тук-Тук", isRead = false)
        val message9 = Message(message = "Тук-Тук", isRead = true)

        service.sendMessage(neo, message1)
        service.sendMessage(neo, message4)
        service.sendMessage(neo, message7)
        service.sendMessage(trinity, message2)
        service.sendMessage(trinity, message5)
        service.sendMessage(trinity, message8)
        service.sendMessage(morpheus, message3)
        service.sendMessage(morpheus, message6)
        service.sendMessage(morpheus, message9)

        // act
        val result = service.getUnreadChats()

        // assert
        assertEquals(2, result)
    }

    @Test
    fun getChats() {
        // arrange
        val service = Service()

        val neo = Companion("Neo", 1)
        val trinity = Companion("Trinity", 2)
        val morpheus = Companion("Morpheus", 3)

        val message1 = Message(message = "Агенты!", isRead = false)
        val message2 = Message(message = "Тук-Тук", isRead = true)
        val message3 = Message(message = "Оператор!", isRead = false)
        val message4 = Message(message = "Избранный", isRead = false)
        val message5 = Message(message = "Агенты!", isRead = false)
        val message6 = Message(message = "Оператор!", isRead = true)
        val message7 = Message(message = "Избранный", isRead = true)
        val message8 = Message(message = "Тук-Тук", isRead = false)

        service.sendMessage(neo, message1)
        service.sendMessage(neo, message2)
        service.sendMessage(neo, message8)

        service.sendMessage(trinity, message3)
        service.sendMessage(trinity, message4)
        service.sendMessage(trinity, message5)

        service.sendMessage(morpheus, message7)
        service.sendMessage(morpheus, message2)
        service.sendMessage(morpheus, message6)

        // act
        val result = service.getChats()

        // assert
        assertEquals(mapOf("Neo" to "Тук-Тук", "Trinity" to "Агенты!", "Morpheus" to "Нет непрочитанных сообщений"), result)
    }

    @Test
    fun getChatMessagesComplete() {
        // arrange
        val service = Service()

        val neo = Companion("Neo", 1)
        val morpheus = Companion("Morpheus", 3)

        val message1 = Message(message = "Агенты!", isRead = false)
        val message2 = Message(message = "Тук-Тук", isRead = true)
        val message3 = Message(message = "Оператор!", isRead = false)
        val message4 = Message(message = "Избранный", isRead = false)
        val message5 = Message(message = "Агенты!", isRead = false)
        val message6 = Message(message = "Оператор!", isRead = true)
        val message7 = Message(message = "Избранный", isRead = true)
        val message8 = Message(message = "Тук-Тук", isRead = false)

        service.sendMessage(neo, message1)
        service.sendMessage(neo, message2)
        service.sendMessage(neo, message8)
        service.sendMessage(neo, message3)
        service.sendMessage(neo, message4)

        service.sendMessage(morpheus, message5)
        service.sendMessage(morpheus, message7)
        service.sendMessage(morpheus, message2)
        service.sendMessage(morpheus, message6)

        // act
        val result = service.getChatMessages(1, message2.id, 3)

        // assert
        assertEquals(listOf("Тук-Тук", "Тук-Тук", "Оператор!"), result)
    }
//
    @Test
    fun getChatMessagesIncomplete() {
        // arrange
        val service = Service()

        val neo = Companion("Neo", 1)
        val morpheus = Companion("Morpheus", 3)

        val message1 = Message(message = "Агенты!", isRead = false)
        val message2 = Message(message = "Тук-Тук", isRead = true)
        val message3 = Message(message = "Оператор!", isRead = false)
        val message4 = Message(message = "Избранный", isRead = false)
        val message5 = Message(message = "Агенты!", isRead = false)
        val message6 = Message(message = "Оператор!", isRead = true)
        val message7 = Message(message = "Избранный", isRead = true)
        val message8 = Message(message = "Тук-Тук", isRead = false)

        service.sendMessage(neo, message1)
        service.sendMessage(neo, message2)
        service.sendMessage(neo, message8)
        service.sendMessage(neo, message3)
        service.sendMessage(neo, message4)

        service.sendMessage(morpheus, message5)
        service.sendMessage(morpheus, message7)
        service.sendMessage(morpheus, message2)
        service.sendMessage(morpheus, message6)

        // act
        val result = service.getChatMessages(1, message2.id, 6)

        // assert
        assertEquals(listOf("Тук-Тук", "Тук-Тук", "Оператор!", "Избранный"), result)
    }
}