package com.bill.videosaver

import com.bill.videosaver.home.HomeFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun homeScreen() = FragmentScreen { HomeFragment() }
}