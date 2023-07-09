package com.example.session_1speedtraining

import java.util.LinkedList
import java.util.Queue

class Controller {
    var queue: Queue<Triple<String, String, Int>> = LinkedList()

    fun getSize(): Int {
        var count = 0
        queue.forEach {
            count ++
        }
        return count
    }

    fun getElement() : Triple<String, String, Int> {
        val element = queue.toList()[0]
        return element
    }

    fun addElement(element: Triple<String, String, Int>) {
        val lst = queue.toMutableList()
        lst.add(element)
        queue = LinkedList(lst)
    }

    fun removeElement() {
        val lst = queue.toMutableList()
        lst.removeAt(0)
        queue = LinkedList(lst)
    }
}