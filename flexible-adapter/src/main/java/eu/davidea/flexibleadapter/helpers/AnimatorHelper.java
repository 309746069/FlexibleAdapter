/*
 * Copyright 2016 Davide Steduto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.davidea.flexibleadapter.helpers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.EnumSet;
import java.util.List;

/**
 * @author Davide Steduto
 * @since 05/09/2016
 */
public class AnimatorHelper {

	private enum AnimatorEnum {
		ALPHA, SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_BOTTOM, SLIDE_IN_TOP, SCALE
	}

	/**
	 * Contains type of animators already added
	 */
	private static EnumSet<AnimatorEnum> animatorsUsed = EnumSet.noneOf(AnimatorEnum.class);

	/*-----------*/
	/* ANIMATORS */
	/*-----------*/

	/**
	 * This is the default animator.<br/>
	 * Alpha animator will be always automatically added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added.<br/>
	 * Incompatible with ALPHA animator.</p>
	 *
	 * @param animators user defined list
	 * @param view      itemView to animate
	 * @param alphaFrom starting alpha value
	 * @since 5.0.0-b1
	 */
	public static void alphaAnimator(
			@NonNull List<Animator> animators, @NonNull View view, @FloatRange(from = 0.0, to = 1.0) float alphaFrom) {
		if (animatorsUsed.contains(AnimatorEnum.ALPHA)) return;
		animators.add(ObjectAnimator.ofFloat(view, "alpha", alphaFrom, 1f));
		animatorsUsed.add(AnimatorEnum.ALPHA);
	}

	/**
	 * Item will slide from Left to Right.<br/>
	 * Ignored if LEFT, RIGHT, TOP or BOTTOM animators were already added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added per time.<br/>
	 * Incompatible with LEFT, TOP, BOTTOM animators.<br/>
	 *
	 * @param animators user defined list
	 * @param view      itemView to animate
	 * @param percent   any % multiplier (between 0 and 1) of the LayoutManager Width
	 * @since 5.0.0-b1
	 */
	public static void slideInFromLeftAnimator(
			@NonNull List<Animator> animators, @NonNull View view,
			RecyclerView recyclerView, @FloatRange(from = 0.0, to= 1.0) float percent) {
		if (animatorsUsed.contains(AnimatorEnum.SLIDE_IN_LEFT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_RIGHT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_TOP) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_BOTTOM)) return;
		animators.add(ObjectAnimator.ofFloat(view, "translationX", -recyclerView.getLayoutManager().getWidth() * percent, 0));
		animatorsUsed.add(AnimatorEnum.SLIDE_IN_LEFT);
	}

	/**
	 * Item will slide from Right to Left.<br/>
	 * Ignored if LEFT, RIGHT, TOP or BOTTOM animators were already added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added per time.<br/>
	 * Incompatible with RIGHT, TOP, BOTTOM animators.<br/>
	 *
	 * @param animators user defined list
	 * @param view      ItemView to animate
	 * @param percent   Any % multiplier (between 0 and 1) of the LayoutManager Width
	 * @since 5.0.0-b1
	 */
	public static void slideInFromRightAnimator(
			@NonNull List<Animator> animators, @NonNull View view,
			RecyclerView recyclerView, @FloatRange(from = 0.0, to = 1.0) float percent) {
		if (animatorsUsed.contains(AnimatorEnum.SLIDE_IN_LEFT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_RIGHT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_TOP) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_BOTTOM)) return;
		animators.add(ObjectAnimator.ofFloat(view, "translationX", recyclerView.getLayoutManager().getWidth() * percent, 0));
		animatorsUsed.add(AnimatorEnum.SLIDE_IN_RIGHT);
	}

	/**
	 * Item will slide from Top of the screen to its natural position.<br/>
	 * Ignored if LEFT, RIGHT, TOP or BOTTOM animators were already added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added per time.<br/>
	 * Incompatible with LEFT, RIGHT, TOP, BOTTOM animators.</p>
	 *
	 * @param animators user defined list
	 * @param view      itemView to animate
	 * @since 5.0.0-b7
	 */
	public static void slideInFromTopAnimator(
			@NonNull List<Animator> animators, @NonNull View view,
			RecyclerView recyclerView) {
		if (animatorsUsed.contains(AnimatorEnum.SLIDE_IN_LEFT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_RIGHT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_TOP) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_BOTTOM)) return;
		animators.add(ObjectAnimator.ofFloat(view, "translationY", -recyclerView.getMeasuredHeight() >> 1, 0));
		animatorsUsed.add(AnimatorEnum.SLIDE_IN_TOP);
	}

	/**
	 * Item will slide from Bottom of the screen to its natural position.<br/>
	 * Ignored if LEFT, RIGHT, TOP or BOTTOM animators were already added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added per time.<br/>
	 * Incompatible with LEFT, RIGHT, TOP, BOTTOM animators.</p>
	 *
	 * @param animators user defined list
	 * @param view      itemView to animate
	 * @since 5.0.0-b1
	 */
	public static void slideInFromBottomAnimator(
			@NonNull List<Animator> animators, @NonNull View view,
			RecyclerView recyclerView) {
		if (animatorsUsed.contains(AnimatorEnum.SLIDE_IN_LEFT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_RIGHT) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_TOP) ||
				animatorsUsed.contains(AnimatorEnum.SLIDE_IN_BOTTOM)) return;
		animators.add(ObjectAnimator.ofFloat(view, "translationY", recyclerView.getMeasuredHeight() >> 1, 0));
		animatorsUsed.add(AnimatorEnum.SLIDE_IN_BOTTOM);
	}

	/**
	 * Item will scale.<br/>
	 * Ignored if SCALE animator was already added.
	 * <p><b>Note:</b> Only 1 animator of the same compatible type can be added per time.<br/>
	 * Incompatible with LEFT, RIGHT, BOTTOM animators.<br/>
	 *
	 * @param animators user defined list
	 * @param view      itemView to animate
	 * @param scaleFrom initial scale value
	 * @since 5.0.0-b1
	 */
	public static void scaleInAnimator(
			@NonNull List<Animator> animators, @NonNull View view, @FloatRange(from = 0.0, to = 1.0) float scaleFrom) {
		if (animatorsUsed.contains(AnimatorEnum.SCALE)) return;
		animators.add(ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, 1f));
		animators.add(ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, 1f));
		animatorsUsed.add(AnimatorEnum.SCALE);
	}

}