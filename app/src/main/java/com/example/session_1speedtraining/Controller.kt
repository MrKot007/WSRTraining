package com.example.session_1speedtraining

import java.util.LinkedList
import java.util.Queue

class Controller {
    val queue: Queue<Triple<String, String, Int>> = LinkedList()

    fun getSize(): Int {
        val sz = queue.size
        return sz
    }

    fun getElement() : Triple<String, String, Int> {
        val el = queue.element()
        return el
    }

    fun addElement(element: Triple<String, String, Int>) {
        val el = element
        queue.add(el)
    }

    fun removeElement() {
        queue.remove()
    }
}