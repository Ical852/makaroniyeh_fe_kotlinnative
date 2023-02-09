package com.egp.makaroniyeh

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egp.makaroniyeh.adapter.BestSellerAdapter
import com.egp.makaroniyeh.adapter.NewItemAdapter
import com.egp.makaroniyeh.adapter.PopularAdapter
import com.egp.makaroniyeh.model.Data
import com.egp.makaroniyeh.model.Product
import com.egp.makaroniyeh.services.APIClient
import com.egp.makaroniyeh.services.APIInterfaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog

    private var dataBestSeller: ArrayList<Data> = ArrayList()
    private lateinit var bestSellerAdapter: BestSellerAdapter

    private var dataPopular: ArrayList<Data> = ArrayList()
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

    var apiInterface: APIInterfaces? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentState = "all"
        apiInterface = APIClient().getClient()?.create(APIInterfaces::class.java)

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
            dataNewItem.clear()
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
        bg.background.setTint((Color.parseColor("#F86A2F")))
    }

    fun removeTextBg(tv: TextView) {
        tv.setTextColor(Color.WHITE)
        tv.setBackgroundResource(0)
    }

    fun changeColorToOff(tv: TextView) {
        tv.setBackgroundResource(R.drawable.shape_rectangle_12_fffborder)
        tv.setTextColor(Color.parseColor("#F86A2F"))
    }

    fun getByCategory(id: Int) {
        dataNewItem = ArrayList<Data>()

        newItemAdapter = NewItemAdapter(dataNewItem)
        var rvnewitem : RecyclerView = findViewById(R.id.rvNewItem)
        rvnewitem.layoutManager = LinearLayoutManager(this)
        rvnewitem.adapter = newItemAdapter

        val call: Call<Product?>? = apiInterface?.getByCatId(id)
        call!!.enqueue(object : Callback<Product?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Product?>?, response: Response<Product?>) {
                val response: Product? = response.body()
                if (response != null) {
                    dataNewItem.addAll(response.data)
                }
                newItemAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.i("Error", "Error on consuming services")
            }
        })
    }

    fun getBestSeller() {
        bestSellerAdapter = BestSellerAdapter(dataBestSeller)
        var rvbestSeller : RecyclerView = findViewById(R.id.rvbestseller)
        rvbestSeller.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvbestSeller.adapter = bestSellerAdapter

        val call: Call<Product?>? = apiInterface?.getBestSeller()
        call!!.enqueue(object : Callback<Product?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Product?>?, response: Response<Product?>) {
                val response: Product? = response.body()
                if (response != null) {
                    dataBestSeller.addAll(response.data)
                }
                bestSellerAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.i("Error", "Error on consuming services")
            }
        })
    }

    fun getPopular() {
        popularAdapter = PopularAdapter(dataPopular)
        var rvpopular : RecyclerView = findViewById(R.id.rvpopular)
        rvpopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvpopular.adapter = popularAdapter

        val call: Call<Product?>? = apiInterface?.getPopular()
        call!!.enqueue(object : Callback<Product?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Product?>?, response: Response<Product?>) {
                val response: Product? = response.body()
                if (response != null) {
                    dataPopular.addAll(response.data)
                }
                popularAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.i("Error", "Error on consuming services")
            }
        })
    }

    fun getNewItem() {
        newItemAdapter = NewItemAdapter(dataNewItem)
        var rvnewitem : RecyclerView = findViewById(R.id.rvNewItem)
        rvnewitem.layoutManager = LinearLayoutManager(this)
        rvnewitem.adapter = newItemAdapter

        val call: Call<Product?>? = apiInterface?.getNewArrivals()
        call!!.enqueue(object : Callback<Product?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Product?>?, response: Response<Product?>) {
                val response: Product? = response.body()
                if (response != null) {
                    dataNewItem.addAll(response.data)
                }
                newItemAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.i("Error", "Error on consuming services")
            }
        })
    }

}