package com.example.a0sessiontry3

import java.util.LinkedList
import java.util.Queue

class QueueController {
    var queue: Queue<Triple<String, String, Int>> = LinkedList()

    fun getSize() : Int{
        return queue.size
    }

    fun getElement() : Triple<String, String, Int> {
        return queue.element()
    }

    fun addElement(element: Triple<String, String, Int>) {
        queue.add(element)
    }

    fun removeElement() {
        queue.remove()
    }

    fun fillQueue() {
        for (i in repository) {
            queue.add(i)
        }
    }

    fun clearQueue() {
        queue.clear()
    }
}