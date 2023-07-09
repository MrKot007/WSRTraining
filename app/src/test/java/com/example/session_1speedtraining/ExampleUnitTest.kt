package com.example.session_1speedtraining

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun checkSize() {
        val sz = QueueObject.controller.getSize()
        QueueObject.controller.removeElement()
        assertEquals(QueueObject.controller.getSize(), sz-1)
    }

    @Test
    fun checkOrder() {
        for (i in QueueObject.list) {
            assertEquals(QueueObject.controller.getElement(), i)
            QueueObject.controller.removeElement()
        }
    }
}