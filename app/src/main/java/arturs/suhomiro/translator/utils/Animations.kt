package arturs.suhomiro.translator.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation

fun fadeInAnimation(viewToFadeIn: View) {
    val fadeIn = ObjectAnimator.ofFloat(viewToFadeIn, "alpha", 0f, 1f)
    fadeIn.duration = 500
    fadeIn.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            viewToFadeIn.visibility = View.VISIBLE
            viewToFadeIn.alpha = 0f
        }
    })
    fadeIn.start()
}

fun fadeOutAnimation(viewToFadeOut: View) {
    val fadeOut = ObjectAnimator.ofFloat(viewToFadeOut, "alpha", 1f, 0f)
    fadeOut.duration = 500
    fadeOut.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            viewToFadeOut.visibility = View.GONE
        }
    })
    fadeOut.start()
}

fun setAdapterFadeAnimation(view: View) {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = 300
    view.startAnimation(anim)
}