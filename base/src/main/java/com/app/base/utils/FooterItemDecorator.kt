package com.app.base.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FooterItemDecorator(val myView: View) : RecyclerView.ItemDecoration() {


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if (myView.visibility != View.VISIBLE) return

        myView.measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        myView.layout(parent.left, 0, parent.right, myView.measuredHeight);
        for (i in 0..parent.childCount - 1) {
            val view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
                c.save();
                c.translate(0f, view.bottom.toFloat());
                myView.draw(c);
                c.restore();
                break;
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (myView.visibility == View.GONE) {
            outRect.setEmpty();
            return
        }

        if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
            myView.measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            outRect.set(0, 0, 0, myView.measuredHeight);
        } else {
            outRect.setEmpty();
        }
    }
}
