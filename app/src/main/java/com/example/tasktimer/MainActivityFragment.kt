package com.example.tasktimer


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
private const val TAG = "MainActivityFragment"

class MainActivityFragment : Fragment() {

    private val viewModel : TaskTimerViewModel by activityViewModels()
    private val mAdapter = CursorRecyclerViewAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "OnCreateView: called")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: called")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, Observer{cursor -> mAdapter.swapCursor(cursor)?.close()})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        super.onViewCreated(view, savedInstanceState)

        task_list_rv.layoutManager = LinearLayoutManager(context)
        task_list_rv.adapter = mAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: called")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateREstored: called")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: called")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: called")
        super.onDetach()
    }
}
