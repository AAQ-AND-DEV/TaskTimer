package com.example.tasktimer

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_items.*
import java.lang.IllegalStateException

class TaskViewHolder(override val containerView: View):
    RecyclerView.ViewHolder(containerView), LayoutContainer{

}

private const val TAG = "CursorRecyclerViewAdapt"

class CursorRecyclerViewAdapter(private var cursor: Cursor?) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_items, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, pos: Int) {
        Log.d(TAG, "onBindViewHolder: data populating view")
        val cursor = cursor //avoid problems with smart cast
        if (cursor == null || cursor.count == 0){
            Log.d(TAG, "onBindViewHolder: providing instructions")
            holder.tli_name.setText(R.string.instructions_heading)
            holder.tli_desc.setText(R.string.instructions)
            holder.tli_edit.visibility= View.GONE
            holder.tli_delete.visibility = View.GONE
        } else{
            if (!cursor.moveToPosition(pos)){
                throw IllegalStateException("Couldn't move cursor to position $pos")
            }
            //create task object from data in cursor
            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                      cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                      cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER)))
            //remember ID isn't set in constructor
            task.id = cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))

            holder.tli_name.text = task.name
            holder.tli_desc.text = task.desc
            holder.tli_edit.visibility = View.VISIBLE   //tODO: add onClick
            holder.tli_delete.visibility = View.VISIBLE  //tODO: add onClick

        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: starts")
        val cursor = cursor
        val count = if (cursor == null|| cursor.count == 0) { 1
        } else {cursor.count}
        return count
    }

    /**
     * swap in new cursor, returning old cursor
     * the returned old cursor is not closed
     * @param newCursor The new cursor to be used
     * @return Returns the previously set Cursor, or nuill if there wasn't one.
     * If the given new Cursor is the same instance as the previously set Cursor,
     * null is also returned
     */
    fun swapCursor(newCursor: Cursor?): Cursor?{
        if (newCursor === cursor){
            return null
        }
        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor
        if (newCursor != null){
            //notifying observers about the new cursor
            notifyDataSetChanged()
        } else{
            //notify observers about lack of a data set
            notifyItemRangeRemoved(0, numItems)
        }
        return oldCursor
    }
}