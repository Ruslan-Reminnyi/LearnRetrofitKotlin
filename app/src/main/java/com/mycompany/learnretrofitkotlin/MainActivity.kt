package com.mycompany.learnretrofitkotlin

import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mycompany.learnretrofitkotlin.Dialogs.AddDialog
import com.mycompany.learnretrofitkotlin.Dialogs.DeleteDialog
import com.mycompany.learnretrofitkotlin.Dialogs.EditDialog
import java.util.*

class MainActivity : AppCompatActivity(), MainView, RecyclerViewAdapter.ItemClickListener {

    var fab: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var mainPresenter: MainPresenter? = null
    var adapter: RecyclerViewAdapter? = null
    var entityList: List<Entity?>? = null
    var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh)
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
        entityList = ArrayList()

        fab = findViewById(R.id.fab)
        fab?.setOnClickListener(View.OnClickListener {
            AddDialog().show(supportFragmentManager, "AddDialog")
        })

        mainPresenter = MainPresenter(this)
        mainPresenter!!.getData()

        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener { mainPresenter!!.getData() })

        currentIndex = 0

    }

    override fun showLoading() {
        swipeRefreshLayout!!.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun onGetResult(_entityList: List<Entity?>?) {
        entityList = _entityList
        adapter = RecyclerViewAdapter(this, entityList!!)
        adapter!!.notifyDataSetChanged()
        adapter!!.setClickListener(this)
        recyclerView!!.adapter = adapter
    }

    override fun onErrorLoading(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(
        view: View?,
        position: Int
    ) {
        Toast.makeText(
            this
            , "id = " + entityList!![position]?.getId()
            , Toast.LENGTH_SHORT
        ).show()

        currentIndex = position

        registerForContextMenu(recyclerView)
        openContextMenu(recyclerView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.item_adapter_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_context_edit -> {
                EditDialog(this).show(
                    supportFragmentManager,
                    "EditDialog"
                )
                true
            }
            R.id.menu_context_delete -> {
                DeleteDialog(this).show(
                    supportFragmentManager,
                    "DeleteDialog"
                )
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}
