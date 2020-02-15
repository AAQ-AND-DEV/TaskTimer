package com.example.tasktimer

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //testInsert()
        //testUpdate()
        testBulkUpdate()

        val projection = arrayOf(TasksContract.Columns.TASK_NAME, TasksContract.Columns.TASK_SORT_ORDER)
        val sortColumn = TasksContract.Columns.TASK_SORT_ORDER
        //val cursor = contentResolver.query(TasksContract.buildUriFromId(2), projection, null, null, sortColumn)
        val cursor = contentResolver.query(TasksContract.CONTENT_URI, null, null, null, sortColumn)
        Log.d(TAG, "***************")
        cursor.use{
            if (it!= null && cursor!=null){
            while (it.moveToNext()){
                //cycle through all records
                with(cursor){
                    val id = getLong(0)
                    val name = getString(1)
                    val desc = getString(2)
                    val sortOrder = getString(3)
                    val result = "ID: $id, Name: $name, desc: $desc sort Order: $sortOrder"
                    Log.d(TAG, "onCreate: reading data $result")
                }
            }
            }
        }
        Log.d(TAG, "*****************")

    }

    private fun testBulkUpdate(){
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_SORT_ORDER, 99)
            put(TasksContract.Columns.TASK_DESCRIPTION, "Completed")
        }
        val selection = TasksContract.Columns.TASK_SORT_ORDER + " =2"

        //val taskUri = TasksContract.buildUriFromId(4)
        val rowAffected  = contentResolver.update(TasksContract.CONTENT_URI, values, selection, null)
        Log.d(TAG, "nu. rows updated is $rowAffected")
    }
    private fun testUpdate(){
        val values = ContentValues().apply{
            put(TasksContract.Columns.TASK_NAME, "Content Provider")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Record Content provider videos")
        }
        val taskUri = TasksContract.buildUriFromId(4)
        val rowAffected  = contentResolver.update(taskUri, values, null, null)
        Log.d(TAG, "nu. row updated is $rowAffected")

    }

    private fun testInsert(){
        val values = ContentValues().apply{
            put(TasksContract.Columns.TASK_NAME, "New Task 1")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Desc 1")
            put(TasksContract.Columns.TASK_SORT_ORDER, 2)
        }
        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
        Log.d(TAG, "New row id (in uri) is $uri")
        if (uri!= null){
        Log.d(TAG, "id (in uri) is ${TasksContract.getId(uri)}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
