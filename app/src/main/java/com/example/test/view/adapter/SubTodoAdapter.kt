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

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.model.SubTodo
import com.example.test.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.item_maintodo.view.createdAtTextView
import kotlinx.android.synthetic.main.item_maintodo.view.idTextView
import kotlinx.android.synthetic.main.item_maintodo.view.updatedAtTextView
import kotlinx.android.synthetic.main.item_subtodo.view.*

class SubTodoAdapter(
  private val context: Context, private val viewModel: TodoViewModel
) :
    RecyclerView.Adapter<SubTodoAdapter.SubTodoViewHolder>() {

  private var data = mutableListOf<SubTodo?>()

  interface SubTodoClickListener {
    fun onSubTodoClicked(mainTodo: MainTodo)
  }

//  val requestOptions: RequestOptions by lazy {
//    RequestOptions()
//        .error(R.drawable.no_internet)
//        .placeholder(R.drawable.ic_movie_placeholder)
//  }

  fun updateData(newData: List<SubTodo?>) {
    data.clear()
    data.addAll(newData)
    notifyDataSetChanged()
  }

  inner class SubTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(subTodo: SubTodo) {
      itemView.idTextView.text = "Id: " + subTodo?.id.toString()
      itemView.nameTextView.text = "Name: " + subTodo?.name
      itemView.doneTextView.text = "Done: " + subTodo?.done.toString()
      itemView.todo_idTextView.text = "Todo_id: " + subTodo?.todo_id.toString()
      itemView.createdAtTextView.text = "Created at: " + subTodo?.created_at
      itemView.updatedAtTextView.text = "Created by: " + subTodo?.updated_at


//      itemView.setOnClickListener {
//        subTodo?.let {
//          subTodoClickListener.onSubTodoClicked(it)
//        }
//      }
      itemView.btn_update.setOnClickListener() {
        showDialog(subTodo)
      }
      itemView.btn_patch.setOnClickListener() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Patch?")
          .setCancelable(true)
          .setPositiveButton("Yes") { dialog, id ->
            subTodo.done = false
            viewModel.subTodo = subTodo
            viewModel.patchSubTodo()
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
  private fun showDialog(subTodo: SubTodo) {
    val dialog = Dialog(context)
    val view: View = (context as Activity).layoutInflater.inflate(
      R.layout.dialog_update_subtodo,
      null
    )
//    val view: View = getLayoutInflater().inflate(R.layout.dialog_choose_item, null)
    val width = (context.getResources().displayMetrics.widthPixels * 0.90).toInt()
    var height = (context.getResources().displayMetrics.heightPixels * 0.3).toInt()
    view.minimumWidth = width
    view.minimumHeight = height
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(view)
    val name = dialog.findViewById(R.id.editName) as EditText
    name.setText(subTodo.name)
    val radio_group = dialog.findViewById(R.id.radioGroup) as RadioGroup
    val radio_done = dialog.findViewById(R.id.radio_done) as RadioButton
    val radio_undone = dialog.findViewById(R.id.radio_undone) as RadioButton
    radio_done.setOnClickListener() {
      subTodo.done = true
    }
    radio_undone.setOnClickListener() {
      subTodo.done = false
    }
    if (subTodo.done) {
      radio_group.check(R.id.radio_done)
    } else {
      radio_group.check(R.id.radio_undone)
    }
    val btn_update = dialog.findViewById(R.id.btn_update) as Button
    btn_update.setOnClickListener {
      subTodo.name = name.text.toString()
      viewModel.subTodo = subTodo
      viewModel.updateSubTodo()
      dialog.dismiss()
    }
    dialog.show()

  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTodoViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
      R.layout.item_subtodo,
      parent,
      false
    )

    return SubTodoViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: SubTodoViewHolder, position: Int) {
    data[position]?.let { holder.bind(it) }
  }

  override fun getItemCount() = data.size
  companion object {
    const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
  }
}