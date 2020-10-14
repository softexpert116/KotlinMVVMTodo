/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.test.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ViewUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.ui.view.ServerListener
import com.example.test.util.myToast
import com.example.test.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.item_maintodo.view.*

class MainTodoAdapter(
  private val context: Context, private val mainTodoClickListener: MainTodoClickListener, private val viewModel: TodoViewModel) :
    RecyclerView.Adapter<MainTodoAdapter.MainTodoViewHolder>() {

  private var data = mutableListOf<MainTodo?>()

  interface MainTodoClickListener {
    fun onMainTodoClicked(mainTodo: MainTodo)
  }

//  val requestOptions: RequestOptions by lazy {
//    RequestOptions()
//        .error(R.drawable.no_internet)
//        .placeholder(R.drawable.ic_movie_placeholder)
//  }

  fun updateData(newData: List<MainTodo?>) {
    data.clear()
    data.addAll(newData)
    notifyDataSetChanged()
  }

  inner class MainTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(mainTodo: MainTodo) {
      itemView.idTextView.text = "Id: " + mainTodo?.id
      itemView.titleTextView.text = "Title: " + mainTodo?.title
      itemView.createdByTextView.text = "Created by: " + mainTodo?.created_by
      itemView.createdAtTextView.text = "Created at: " + mainTodo?.created_at
      itemView.updatedAtTextView.text = "Updated at: " + mainTodo?.updated_at
      itemView.itemsTextView.text = "Items: " + mainTodo?.items.size.toString()

      itemView.setOnClickListener {
        mainTodo?.let {
          mainTodoClickListener.onMainTodoClicked(it)
        }
      }
      itemView.btn_delete.setOnClickListener() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Delete?")
          .setCancelable(true)
          .setPositiveButton("Yes") { dialog, id ->
            viewModel.mainTodo = mainTodo
            viewModel.todoDelete()
            dialog.dismiss()
          }
          .setNegativeButton("No") { dialog, id ->
            // Dismiss the dialog
            dialog.dismiss()
          }
        val alert = builder.create()
        alert.show()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTodoViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
        R.layout.item_maintodo,
        parent,
        false
    )

    return MainTodoViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: MainTodoViewHolder, position: Int) {
    data[position]?.let { holder.bind(it) }
  }

  override fun getItemCount() = data.size
  companion object {
    const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
  }
}