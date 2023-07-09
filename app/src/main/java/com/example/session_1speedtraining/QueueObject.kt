package com.example.session_1speedtraining

object QueueObject {
    val controller = Controller()
    val list = listOf(
        Triple("Quick Delivery At Your\nDoorstep", "Enjoy quick pick-up and delivery to\nyour destination", R.drawable.boarding1),
        Triple("Flexible Payment", "Different modes of payment either\nbefore and after delivery without\nstress", R.drawable.boarding2),
        Triple("Real-time Tracking", "Track your packages/items from the\ncomfort of your home till final destination", R.drawable.boarding3)
    )
    fun fillQueue() {
        for (i in list) {
            controller.addElement(i)
        }
    }
}