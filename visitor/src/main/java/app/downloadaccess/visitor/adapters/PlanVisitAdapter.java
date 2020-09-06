package app.downloadaccess.visitor.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.downloadaccess.resources.models.Slot;
import app.downloadaccess.visitor.R;
import app.downloadaccess.visitor.stickyheaders.AdapterDataProvider;

public class PlanVisitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDataProvider {

    private List<Slot> dataList = new ArrayList<>();
    private AdapterCallback callback;
    private Activity mActivity;
    private Slot mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public PlanVisitAdapter(Activity activity) {
        mActivity = activity;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //1 = header
//        //10 = standard
//        //11 = standard planned
//        //12 = standard disabled
//        //20 = priority
//        //21 = priority planned
//        //22 = priority disabled
//        if (viewType == 1) {
//            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_planner_header, parent, false));
//        } else {
//            ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot, parent, false));
//            if (viewType % 10 == 2) {
//                itemViewHolder.linearLayout.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.grey));
//                itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.disabled_tint));
//                itemViewHolder.hourFromTo.setTextColor(ContextCompat.getColor(mActivity, R.color.disabled_tint));
//                itemViewHolder.occupiedMax.setTextColor(ContextCompat.getColor(mActivity, R.color.disabled_tint));
//                itemViewHolder.checkIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.grey));
//                itemViewHolder.personIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.disabled_tint));
//
//                itemViewHolder.itemView.setOnClickListener(v ->
//                        Toast.makeText(getContext(), "You have reached the daily limit for this place!", Toast.LENGTH_SHORT).show()
//                );
//                return itemViewHolder;
//            }
//            if (viewType < 20) {
//                itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorPrimary));
//            }
//            if (viewType % 10 == 0) {
//                itemViewHolder.hourFromTo.setTextColor(ContextCompat.getColor(mActivity, R.color.text_grey));
//                itemViewHolder.personIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.icon_tint));
//                itemViewHolder.occupiedMax.setTextColor(ContextCompat.getColor(mActivity, R.color.text_grey));
//            }
//            if (viewType == 20)
//                itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
//            if (viewType == 21) {
//                itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
//                itemViewHolder.priorityText.setTextColor(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
//            }
//            if (viewType % 10 == 1) {
//                itemViewHolder.linearLayout.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
//                itemViewHolder.hourFromTo.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
//                itemViewHolder.occupiedMax.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
//                itemViewHolder.checkIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
//                itemViewHolder.personIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
//            }
//            itemViewHolder.itemView.setOnClickListener(v -> callback.onItemClick(itemViewHolder.getAdapterPosition()));
//            return itemViewHolder;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        final Slot slot = dataList.get(position);
//        if (holder instanceof ItemViewHolder) {
//            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//            itemViewHolder.hourFromTo.setText(slot.getFrom() + " - " + slot.getTo());
//
//            itemViewHolder.priorityText.setText(slot.getType().charAt(0) + "");
//            itemViewHolder.occupiedMax.setText(slot.getOccupiedSlots() + "/" + slot.getMaxSlots());
//        } else if (holder instanceof HeaderViewHolder) {
//            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
//            try {
//                cal.setTime(sdf.parse(slot.getFrom()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            headerViewHolder.shortDate.setText(new SimpleDateFormat("EEE", Locale.getDefault()).format(cal.getTime()));
//            headerViewHolder.date.setText(cal.get(Calendar.DAY_OF_MONTH) + "th " + new SimpleDateFormat("MMM").format(cal.getTime()));
//        }
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot, parent, false));
            itemViewHolder.itemView.setOnClickListener(v -> callback.onItemClick(itemViewHolder.getAdapterPosition()));
            itemViewHolder.priorityText.setOnClickListener(v -> callback.onItemClick(itemViewHolder.getAdapterPosition()));
            return itemViewHolder;

        } else {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_planner_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Slot slot = dataList.get(position);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.hourFromTo.setText(slot.getFrom() + " - " + slot.getTo());

            itemViewHolder.priorityText.setText(slot.getType().charAt(0) + "");
            itemViewHolder.occupiedMax.setText(slot.getOccupiedSlots() + "/" + slot.getMaxSlots());

            if (slot.getType().equals("Standard")) {
                itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorPrimary));
            } else {
                if (slot.isPlanned()) {
                    itemViewHolder.priorityText.setTextColor(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
                    itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
                } else {
                    itemViewHolder.priorityText.setTextColor(ContextCompat.getColorStateList(mActivity, R.color.white));
                    itemViewHolder.priorityText.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
                }
            }
            if (slot.isPlanned()) {
                itemViewHolder.mainLayout.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.colorAccent));
                itemViewHolder.hourFromTo.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
                itemViewHolder.occupiedMax.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
                itemViewHolder.checkIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
                itemViewHolder.personIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.white));
            } else {
                itemViewHolder.mainLayout.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.grey));
                itemViewHolder.hourFromTo.setTextColor(ContextCompat.getColor(mActivity, R.color.disabled_tint));
                itemViewHolder.occupiedMax.setTextColor(ContextCompat.getColor(mActivity, R.color.disabled_tint));
                itemViewHolder.checkIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.grey));
                itemViewHolder.personIcon.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.disabled_tint));
            }
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            try {
                cal.setTime(sdf.parse(slot.getFrom()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            headerViewHolder.shortDate.setText(new SimpleDateFormat("EEE", Locale.getDefault()).format(cal.getTime()));
            headerViewHolder.date.setText(cal.get(Calendar.DAY_OF_MONTH) + "th " + new SimpleDateFormat("MMM").format(cal.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getViewType();
    }

    @Override
    public List<Slot> getAdapterData() {
        return dataList;
    }

    public void setDataList(List<Slot> items) {
        dataList = items;
    }

    public Context getContext() {
        return mActivity;
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = dataList.get(position);
        mRecentlyDeletedItemPosition = position;
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = mActivity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, "Undo slot delete", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo slot delete", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        dataList.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public void setAdapterCallback(AdapterCallback callback) {
        this.callback = callback;
    }

    private static final class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView hourFromTo, priorityText, occupiedMax;
        RelativeLayout mainLayout;
        ImageView personIcon, checkIcon;

        ItemViewHolder(View itemView) {
            super(itemView);

            personIcon = itemView.findViewById(R.id.personIconSlot);
            checkIcon = itemView.findViewById(R.id.checkIcon);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            hourFromTo = itemView.findViewById(R.id.hourFromTo);
            priorityText = itemView.findViewById(R.id.priority_text);
            occupiedMax = itemView.findViewById(R.id.occupiedMax);
        }
    }

    private static final class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView shortDate, date;

        HeaderViewHolder(View itemView) {
            super(itemView);

            shortDate = itemView.findViewById(R.id.shortDay);
            date = itemView.findViewById(R.id.date);
        }
    }

    public interface AdapterCallback {
        void onItemClick(int position);
    }
}