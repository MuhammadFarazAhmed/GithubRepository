package com.app.base.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.transition.Transition
import androidx.transition.TransitionValues
import kotlin.math.hypot

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CircularReveal : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        val view = values.view
        val bounds = Rect()
        bounds.left = view.left
        bounds.right = view.right
        bounds.top = view.top
        bounds.bottom = view.bottom

        values.values[BOUNDS] = bounds
    }

    override fun getTransitionProperties(): Array<String>? {
        return PROPS
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }

        val startRect = startValues.values[BOUNDS] as Rect?
        val endRect = endValues.values[BOUNDS] as Rect?

        val view = endValues.view

        val circularTransition: Animator
        if (isReveal(startRect!!, endRect!!)) {
            circularTransition = createReveal(view, startRect, endRect)
            return NoPauseAnimator(circularTransition)
        } else {
            layout(startRect, view)

            circularTransition = createConceal(view, startRect, endRect)
            circularTransition.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.outlineProvider = object : ViewOutlineProvider() {
                        override fun getOutline(view: View, outline: Outline) {
                            endRect.left -= view.left
                            endRect.top -= view.top
                            endRect.right -= view.left
                            endRect.bottom -= view.top
                            outline.setOval(endRect)
                            view.clipToOutline = true
                        }
                    }
                }
            })
            return NoPauseAnimator(circularTransition)
        }
    }

    private fun layout(startRect: Rect, view: View) {
        view.layout(startRect.left, startRect.top, startRect.right, startRect.bottom)
    }

    private fun createReveal(view: View, from: Rect, to: Rect): Animator {

        val centerX = from.centerX()
        val centerY = from.centerY()
        val finalRadius = hypot(to.width().toDouble(), to.height().toDouble()).toFloat()

        return ViewAnimationUtils.createCircularReveal(
            view, centerX, centerY,
            (from.width() / 2).toFloat(), finalRadius
        )
    }

    private fun createConceal(view: View, from: Rect, to: Rect): Animator {

        val centerX = to.centerX()
        val centerY = to.centerY()
        val initialRadius = hypot(from.width().toDouble(), from.height().toDouble()).toFloat()

        return ViewAnimationUtils.createCircularReveal(
            view, centerX, centerY,
            initialRadius, (to.width() / 2).toFloat()
        )
    }

    private fun isReveal(startRect: Rect, endRect: Rect): Boolean {
        return startRect.width() < endRect.width()
    }

    companion object {

        private const val BOUNDS = "viewBounds"

        private val PROPS = arrayOf(BOUNDS)
    }
}