package com.example.session_1speedtraining

import java.util.LinkedList
import java.util.Queue

class Controller {
    val queue: Queue<Triple<String, String, Int>> = LinkedList()

    fun getSize(): Int {
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
}