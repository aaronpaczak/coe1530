package coe1530.eatyourleftovers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import coe1530.eatyourleftovers.TodoItemFragment.OnListFragmentInteractionListener;
import coe1530.eatyourleftovers.ToDoList.ToDoItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyTodoItemRecyclerViewAdapter extends RecyclerView.Adapter<MyTodoItemRecyclerViewAdapter.ViewHolder> {

    private final List<ToDoItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTodoItemRecyclerViewAdapter(List<ToDoItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todoitem_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitleView.setText(mValues.get(position).name);
        switch(mValues.get(position).priority)
        {
            case 1:
                holder.mPriorityView.setText("HIGH");
                break;
            case 2:
                holder.mPriorityView.setText("MEDIUM");
                break;
            case 3:
                holder.mPriorityView.setText("LOW");
                break;
        }

        // The click action when and item in the to do list is clicked
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    Log.w("ITEM CLICKEDDDD", "title: " + holder.mItem.name);
                    Context context=holder.mView.getContext();
                    Intent editTaskIntent=new Intent(context, EditTaskActivity.class);
                    editTaskIntent.putExtra("Task Name",holder.mItem.name);
                    context.startActivity(editTaskIntent);
                    Log.w("checking",holder.mItem.name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mPriorityView;
        public ToDoItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mPriorityView = (TextView) view.findViewById(R.id.priorityDisplay);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPriorityView.getText() + "'";
        }
    }
}
