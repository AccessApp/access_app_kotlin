package app.downloadaccess.visitor.stickyheaders;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.downloadaccess.resources.models.Visit;

public class StickyLinearLayoutManager extends LinearLayoutManager {

    private AdapterDataProvider mHeaderProvider;
    private StickyHeaderHandler mHeaderHandler;

    private List<Integer> mHeaderPositions = new ArrayList<>();

    private ViewHolderFactory viewHolderFactory;

    @Nullable
    private StickyHeaderListener mHeaderListener;

    public StickyLinearLayoutManager(Context context, AdapterDataProvider headerProvider) {
        this(context, VERTICAL, false, headerProvider);
    }

    public StickyLinearLayoutManager(Context context, int orientation, boolean reverseLayout, AdapterDataProvider headerProvider) {
        super(context, orientation, reverseLayout);

        this.mHeaderProvider = headerProvider;
    }

    public void setStickyHeaderListener(@Nullable StickyHeaderListener listener) {
        this.mHeaderListener = listener;
        if (mHeaderHandler != null) {
            mHeaderHandler.setListener(listener);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        cacheHeaderPositions();
        if (mHeaderHandler != null) {
            resetHeaderHandler();
        }
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPositionWithOffset(position, 0);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scroll = super.scrollVerticallyBy(dy, recycler, state);
        if (Math.abs(scroll) > 0) {
            if (mHeaderHandler != null) {
                mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
            }
        }
        return scroll;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scroll = super.scrollHorizontallyBy(dx, recycler, state);
        if (Math.abs(scroll) > 0) {
            if (mHeaderHandler != null) {
                mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
            }
        }
        return scroll;
    }

    @Override
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
        if (mHeaderHandler != null) {
            mHeaderHandler.clearHeader();
        }
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        viewHolderFactory = new ViewHolderFactory(view);
        mHeaderHandler = new StickyHeaderHandler(view);
        mHeaderHandler.setListener(mHeaderListener);
        if (mHeaderPositions.size() > 0) {
            mHeaderHandler.setHeaderPositions(mHeaderPositions);
            resetHeaderHandler();
        }
        super.onAttachedToWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        if (mHeaderHandler != null) {
            mHeaderHandler.clearVisibilityObserver();
        }
        super.onDetachedFromWindow(view, recycler);
    }

    private void resetHeaderHandler() {
        mHeaderHandler.reset(getOrientation());
        mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
    }

    private Map<Integer, View> getVisibleHeaders() {
        Map<Integer, View> visibleHeaders = new LinkedHashMap<>();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int dataPosition = getPosition(view);
            if (mHeaderPositions.contains(dataPosition)) {
                visibleHeaders.put(dataPosition, view);
            }
        }
        return visibleHeaders;
    }

    private void cacheHeaderPositions() {
        mHeaderPositions.clear();
        List<Visit> adapterData = (List<Visit>) mHeaderProvider.getAdapterData();
        if (adapterData == null) {
            if (mHeaderHandler != null) {
                mHeaderHandler.setHeaderPositions(mHeaderPositions);
            }
            return;
        }

        for (int i = 0; i < adapterData.size(); i++) {
            if (adapterData.get(i).getViewType() == 1) {
                mHeaderPositions.add(i);
            }
        }
        if (mHeaderHandler != null) {
            mHeaderHandler.setHeaderPositions(mHeaderPositions);
        }
    }

    public interface StickyHeaderListener {

        void headerAttached(View headerView, int adapterPosition);

        void headerDetached(View headerView, int adapterPosition);
    }
}