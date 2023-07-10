package com.example.a0sessiontry3

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
        controller.fillQueue()
        for (i in repository) {
            val sz = controller.getSize()
            controller.getElement()
            controller.removeElement()
            assertEquals(controller.getSize(), sz-1)
        }
    }

    @Test
    fun checkOrder() {
        controller.fillQueue()
        for (i in repository) {
            assertEquals(controller.getElement(), i)
            controller.removeElement()
        }
    }
}