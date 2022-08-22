package com.stefanini.imgurcatfetcher

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefanini.imgurcatfetcher.adapters.MyRecyclerViewAdapter
import com.stefanini.imgurcatfetcher.api.ApiService
import com.stefanini.imgurcatfetcher.model.ImageDto
import com.stefanini.imgurcatfetcher.model.ImgurImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL = "https://api.imgur.com/3/"

class MainActivity : AppCompatActivity() {

    private val imgList = mutableListOf<ImageDto>()
    private lateinit var recyclerView: RecyclerView
    var isLoading = false
    var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        fetchApiData(pageNumber)
        initScrollListener()

        /*val model: MyViewModel by viewModels()
        model.getImages().observe(this, Observer<List<Image>>{ images ->
            // update UI
        })*/

        supportActionBar?.hide()
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val visibleThreshold = 40

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager?

                if (isLoading || gridLayoutManager == null) {
                    return
                }

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() + visibleThreshold >= gridLayoutManager.itemCount) {
                    isLoading = true
                    pageNumber = pageNumber + 1
                    fetchApiData(pageNumber)
                }
            }
        })
    }

    private fun fetchApiData(pageNumber: Int) {
        val retrofitBuilder = ApiService.getApi()

        CoroutineScope(Job() + Dispatchers.IO).launch {
            val retrofitData = retrofitBuilder?.getData(pageNumber.toString())
            retrofitData?.enqueue(object : Callback<ImgurImage?> {
                override fun onResponse(call: Call<ImgurImage?>, response: Response<ImgurImage?>) {
                    //Log.d("IMG_API", "fetched page $pageNumber")
                    val responseBody = response.body()!!
                    for(myData in responseBody.data) {
                        if (myData.is_album) {
                            for (img in myData.images) {
                                if (!img.animated) {
                                    imgList.add(ImageDto(img.id, img.link))
                                }
                            }
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                    isLoading = false
                }

                override fun onFailure(call: Call<ImgurImage?>, t: Throwable) {
                    //throw t
                    Log.d("IMG_API", t.message!!)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvGrid)
        recyclerView.adapter = MyRecyclerViewAdapter(imgList)
        //val layoutManager = GridLayoutManager(this, 4)
        //recyclerView.layoutManager = layoutManager
        recyclerView.autoFitColumns(100)
    }

    /**
     * @param columnWidth - in dp
     */
    fun RecyclerView.autoFitColumns(columnWidth: Int) {
        val displayMetrics = this.context.resources.displayMetrics
        val noOfColumns = ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
        this.layoutManager = GridLayoutManager(this.context, noOfColumns)
    }


}