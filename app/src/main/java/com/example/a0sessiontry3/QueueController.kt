package com.example.a0sessiontry3

import java.util.LinkedList
import java.util.Queue

class QueueController {
    var queue: Queue<Triple<String, String, Int>> = LinkedList()

    fun getSize() : Int{
        var count = 0
        queue.forEach {
            count++
        }
        return count
    }

    fun getElement() : Triple<String, String, Int> {
        val lst = queue.toList()
        return lst[0]
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

    fun fillQueue() {
        for (i in repository) {
            queue.add(i)
        }
    }

    fun clearQueue() {
        queue.clear()
    }
}