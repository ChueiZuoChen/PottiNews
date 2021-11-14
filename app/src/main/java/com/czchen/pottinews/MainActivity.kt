package com.czchen.pottinews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    /**
     * NavController 取得activity_main.xml裡面<fragment> id
     * 因為<fragment>標籤是navigation component用來切換fragment替換的container
     *
     * BottomNavigationView 嵌入menu標籤並展示在螢幕下方的tab
     * */
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        navController = findNavController(R.id.newsNavHostFragment)

        /** 設置bottomNavigationView ，設置後他會與NavController連動，意味著點擊就會跳到該fragment畫面 */
        bottomNavigationView.setupWithNavController(navController)
    }

}