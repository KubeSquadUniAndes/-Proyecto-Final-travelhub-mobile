package com.example.travelhubapp_mobile.notifications

import org.junit.Assert.assertEquals
import org.junit.Test

class TravelHubMessagingServiceTest {

    @Test
    fun channelId_isCorrect() {
        assertEquals("travelhub_reservas", TravelHubMessagingService.CHANNEL_ID)
    }

    @Test
    fun channelId_isNotEmpty() {
        assert(TravelHubMessagingService.CHANNEL_ID.isNotEmpty())
    }

    @Test
    fun channelId_doesNotContainSpaces() {
        assert(!TravelHubMessagingService.CHANNEL_ID.contains(" "))
    }
}
