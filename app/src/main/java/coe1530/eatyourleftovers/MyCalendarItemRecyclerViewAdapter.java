package coe1530.eatyourleftovers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyContent.DummyItem} and makes a call to the
 * specified {@link TodoItemFragment.OnListFragmentInteractionListener}.
 */
public class MyCalendarItemRecyclerViewAdapter extends RecyclerView.Adapter<MyCalendarItemRecyclerViewAdapter.ViewHolder> {

    private final List<CalendarList.CalendarItem> mValues;
    private final CalendarFragment.OnListFragmentInteractionListener mListener;

    public MyCalendarItemRecyclerViewAdapter(List<CalendarList.CalendarItem> items, CalendarFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_calendaritem_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitleView.setText(mValues.get(position).title);
        String timeline = mValues.get(position).startTime + " - " + mValues.get(position).endTime;
        holder.mTimeLineView.setText(timeline);

        // The click action when and item in the to do list is clicked
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    Log.w("ITEM CLICKEDDDD", "title: " + holder.mItem.title);
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
        public final TextView mTimeLineView;
        public CalendarList.CalendarItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mTimeLineView = (TextView) view.findViewById(R.id.timeLine);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
