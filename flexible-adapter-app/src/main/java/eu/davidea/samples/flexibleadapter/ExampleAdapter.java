package eu.davidea.samples.flexibleadapter;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.samples.flexibleadapter.models.LayoutItem;
import eu.davidea.samples.flexibleadapter.models.ULSItem;
import eu.davidea.samples.flexibleadapter.services.DatabaseConfiguration;

/**
 * This is a custom implementation extending FlexibleAdapter. {@code AbstractFlexibleItem} is
 * used as most common Item for ALL view types.
 * <p>Items are bound with <b>METHOD A</b> (new way): AutoMap is active, you <u>don't have to</u>
 * implement {@code getItemViewType, onCreateViewHolder, onBindViewHolder}.</p>
 * Check {@code OverallAdapter} for <b>METHOD B</b> (classic way).
 *
 * @see OverallAdapter
 * @see AbstractFlexibleItem
 */
public class ExampleAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

	private static final String TAG = ExampleAdapter.class.getSimpleName();
	public static final int EXAMPLE_VIEW_TYPE = 1;

	private AbstractFlexibleItem mUseCaseItem;

	public ExampleAdapter(List<AbstractFlexibleItem> items, Object listeners) {
		//true = Items implement hashCode() and have stableIds!
		super(items, listeners, true);
	}

	@Override
	public void updateDataSet(List<AbstractFlexibleItem> items, boolean animate) {
		//NOTE: To have views/items not changed, set them into "items" before passing the final
		// list to the Adapter.

		//Overwrite the list and fully notify the change, pass false to not animate changes.
		//Watch out! The original list must a copy
		super.updateDataSet(items, animate);

		//Add example view
		//showLayoutInfo(true);
	}

	/*
	 * HEADER VIEW
	 * This method show how to add Header/Footer View as it was for ListView.
	 * The secret is the position! 0 for Header; itemCount for Footer ;-)
	 * The view is represented by a custom Item type to better represent any dynamic content.
	 */
	public void showLayoutInfo(boolean scrollToPosition) {
		if (!hasSearchText() && !isEmpty()) {
			//Define Example View
			final LayoutItem item = new LayoutItem("LAY-L");
			if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
				item.setId("LAY-S");
				item.setTitle(mRecyclerView.getContext().getString(R.string.staggered_layout));
			} else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
				item.setId("LAY-G");
				item.setTitle(mRecyclerView.getContext().getString(R.string.grid_layout));
			} else {
				item.setTitle(mRecyclerView.getContext().getString(R.string.linear_layout));
			}
			item.setSubtitle(mRecyclerView.getContext().getString(R.string.columns, getSpanCount(mRecyclerView.getLayoutManager())));
			addItemWithDelay((getItem(0) instanceof ULSItem ? 1 : 0), item, 0L,
					(!(getItem(0) instanceof ULSItem) && scrollToPosition));
			removeItemWithDelay(item, 4000L, true);
		}
	}

	/*
	 * ANOTHER HEADER VIEW
	 * This method show how to add Header/Footer View as it was for ListView.
	 * The secret is the position! 0 for Header; itemCount for Footer ;-)
	 * The view is represented by a custom Item type to better represent any dynamic content.
	 */
	public void addUserLearnedSelection(boolean scrollToPosition) {
		if (!DatabaseConfiguration.userLearnedSelection && !hasSearchText() && !(getItem(0) instanceof ULSItem)) {
			//Define Example View
			final ULSItem item = new ULSItem("ULS");
			item.setTitle(mRecyclerView.getContext().getString(R.string.uls_title));
			item.setSubtitle(mRecyclerView.getContext().getString(R.string.uls_subtitle));
			addItemWithDelay(0, item, 1500L, scrollToPosition);
		}
	}

	@Override
	public void filterItems(@NonNull List<AbstractFlexibleItem> unfilteredItems) {
		super.filterItems(unfilteredItems);
		addUserLearnedSelection(false);
		showLayoutInfo(false);
	}

	/**
	 * This is a customization of the Layout that hosts the header when sticky.
	 * The code works, but it is commented because not used (default is used).
	 */
//	@Override
//	public ViewGroup getStickySectionHeadersHolder() {
//		FrameLayout frameLayout = new FrameLayout(mRecyclerView.getContext());
//		frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
//				ViewGroup.LayoutParams.WRAP_CONTENT,//or MATCH_PARENT
//				ViewGroup.LayoutParams.WRAP_CONTENT));
//		((ViewGroup) mRecyclerView.getParent()).addView(frameLayout);//This is important otherwise the Header disappears!
//		return (ViewGroup) mInflater.inflate(R.layout.sticky_header_layout, frameLayout);
//	}

	/**
	 * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
	 * METHOD B - You override and implement this method as you prefer (don't call super).
	 */
//	@Override
//	public int getItemViewType(int position) {
//		//Not implemented: METHOD A is used
//	}

	/**
	 * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
	 * METHOD B - You override and implement this method as you prefer (don't call super).
	 */
//	@Override
//	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		//Not implemented: METHOD A is used
//	}

	/**
	 * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
	 * METHOD B - You override and implement this method as you prefer (don't call super).
	 */
//	@Override
//	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//		//Not implemented: METHOD A is used
//	}

	@Override
	public List<Animator> getAnimators(View itemView, int position, boolean isForward) {
		List<Animator> animators = new ArrayList<Animator>();
		if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
			//GridLayout
			if (position % 2 == 0)
				addSlideInFromLeftAnimator(animators, itemView, 0.5f);
			else
				addSlideInFromRightAnimator(animators, itemView, 0.5f);
		} else {
			//LinearLayout
			switch (getItemViewType(position)) {
				case R.layout.recycler_header_item:
					addSlideInFromRightAnimator(animators, itemView, 1.0f);
					break;
				case R.layout.recycler_staggered_item:
					if (isForward) //inverted to have items animated up-side-down
						addSlideInFromBottomAnimator(animators, itemView);
					else
						addSlideInFromTopAnimator(animators, itemView);
					break;
				case R.layout.recycler_staggered_header_item:
				case R.layout.recycler_layout_item:
				case R.layout.recycler_uls_item:
					addSlideInFromTopAnimator(animators, itemView);
					break;
				case R.layout.recycler_sub_item:
				case EXAMPLE_VIEW_TYPE:
					addScaleInAnimator(animators, itemView, 0.0f);
					break;
				default:
					if (isSelected(position))
						addSlideInFromRightAnimator(animators, itemView, 0.5f);
					else
						addSlideInFromLeftAnimator(animators, itemView, 0.5f);
					break;
			}
		}

		//Alpha Animator is automatically added
		return animators;
	}

	@Override
	public String onCreateBubbleText(int position) {
		if (!DatabaseConfiguration.userLearnedSelection && position == 0) {//This 'if' is for my example only
			//TODO FOR YOU: This is the normal line you should use: Usually it's the first letter
			return Integer.toString(position);
		}
		return super.onCreateBubbleText(position);
	}

}