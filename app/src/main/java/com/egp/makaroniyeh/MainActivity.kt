package com.egp.makaroniyeh

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.egp.makaroniyeh.adapter.BestSellerAdapter
import com.egp.makaroniyeh.adapter.NewItemAdapter
import com.egp.makaroniyeh.adapter.PopularAdapter
import com.egp.makaroniyeh.model.Data
import com.egp.makaroniyeh.model.Product
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {

    private val dataBestSeller: ArrayList<Data> = ArrayList()
    private lateinit var bestSellerAdapter: BestSellerAdapter

    private val dataPopular: ArrayList<Data> = ArrayList()
    private lateinit var popularAdapter: PopularAdapter

    private var dataNewItem: ArrayList<Data> = ArrayList()
    private lateinit var newItemAdapter: NewItemAdapter

    private lateinit var btnAll: RelativeLayout
    private lateinit var textAll: TextView

    private lateinit var btnPedes: RelativeLayout
    private lateinit var textPedes: TextView

    private lateinit var btnAsin: RelativeLayout
    private lateinit var textAsin: TextView

    private lateinit var btnGurih: RelativeLayout
    private lateinit var textGurih: TextView

    private lateinit var btnKuah: RelativeLayout
    private lateinit var textKuah: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentState = "all"

        btnAll = findViewById(R.id.bgAllMakaroni)
        textAll = findViewById(R.id.allMakaroni)

        btnPedes = findViewById(R.id.btnMakaroniPedes)
        textPedes = findViewById(R.id.makaroniPedas)

        btnAsin = findViewById(R.id.bgmakaroniasin)
        textAsin = findViewById(R.id.makaroniAsin)

        btnGurih = findViewById(R.id.bgmakaronigurih)
        textGurih = findViewById(R.id.makaroniGurih)

        btnKuah = findViewById(R.id.bgmakaronikuah)
        textKuah = findViewById(R.id.makaroniKuah)

        btnAll.setOnClickListener {
            getNewItem()
            currentState = "all"
            allChange("all")
        }

        btnPedes.setOnClickListener {
            getByCategory(1)
            currentState = "pedes"
            allChange("pedes")
        }

        btnAsin.setOnClickListener {
            getByCategory(2)
            currentState = "asin"
            allChange("asin")
        }

        btnGurih.setOnClickListener {
            getByCategory(3)
            currentState = "gurih"
            allChange("gurih")
        }

        btnKuah.setOnClickListener {
            getByCategory(4)
            currentState = "kuah"
            allChange("kuah")
        }

        getBestSeller()
        getPopular()
        getNewItem()
    }

    fun changeState(tv: TextView, bg: RelativeLayout, turn: Boolean) {
        if (turn) {
            changeColorToOn(bg)
            removeTextBg(tv)
        } else {
            changeColorToOff(tv)
        }
    }

    fun allChange(currentState: String) {
        changeState(textAll, btnAll, currentState == "all")
        changeState(textPedes, btnPedes, currentState == "pedes")
        changeState(textAsin, btnAsin, currentState == "asin")
        changeState(textGurih, btnGurih, currentState == "gurih")
        changeState(textKuah, btnKuah, currentState == "kuah")
    }

    fun changeColorToOn(bg: RelativeLayout) {
        bg.background.setTint((Color.parseColor("#40AF47")))
    }

    fun removeTextBg(tv: TextView) {
        tv.setTextColor(Color.WHITE)
        tv.setBackgroundResource(0)
    }

    fun changeColorToOff(tv: TextView) {
        tv.setBackgroundResource(R.drawable.shape_rectangle_12_fffborder)
        tv.setTextColor(Color.parseColor("#40AF47"))
    }

    fun getByCategory(id: Int) {
        dataNewItem = ArrayList<Data>()

        newItemAdapter = NewItemAdapter(dataNewItem)
        var rvnewitem : RecyclerView = findViewById(R.id.rvNewItem)
        rvnewitem.layoutManager = LinearLayoutManager(this)
        rvnewitem.adapter = newItemAdapter

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("http://10.0.2.2:8000/api/products/" + id)
            .build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: Product) {
                    dataNewItem.addAll(response.data)
                    newItemAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, anError.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }

    fun getBestSeller() {
        bestSellerAdapter = BestSellerAdapter(dataBestSeller)
        var rvbestSeller : RecyclerView = findViewById(R.id.rvbestseller)
        rvbestSeller.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvbestSeller.adapter = bestSellerAdapter

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("http://10.0.2.2:8000/api/products/best")
            .build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: Product) {
                    dataBestSeller.addAll(response.data)
                    bestSellerAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, anError.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }

    fun getPopular() {
        popularAdapter = PopularAdapter(dataPopular)
        var rvpopular : RecyclerView = findViewById(R.id.rvpopular)
        rvpopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvpopular.adapter = popularAdapter

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("http://10.0.2.2:8000/api/products/popular")
            .build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: Product) {
                    dataPopular.addAll(response.data)
                    popularAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, anError.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }

    fun getNewItem() {
        newItemAdapter = NewItemAdapter(dataNewItem)
        var rvnewitem : RecyclerView = findViewById(R.id.rvNewItem)
        rvnewitem.layoutManager = LinearLayoutManager(this)
        rvnewitem.adapter = newItemAdapter

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("http://10.0.2.2:8000/api/products")
            .build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: Product) {
                    dataNewItem.addAll(response.data)
                    newItemAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, anError.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }
}