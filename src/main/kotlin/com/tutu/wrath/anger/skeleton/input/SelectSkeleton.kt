package com.tutu.wrath.anger.skeleton.input

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.html.P
import io.kvision.html.span

class SelectSkeleton(isLoading: Boolean) : P(className = "animate-pulse w-full") {

    var isLoading by refreshOnUpdate(isLoading)

    init {
        span(className = "inline-block min-h-[1em] w-full rounded flex-auto cursor-wait bg-current align-middle text-base text-neutral-700 opacity-50 dark:text-neutral-50") {
            height = CssSize(36, UNIT.px)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (!isLoading) {
            classSetBuilder.add("hidden")
        }
    }
}


fun Container.selectSkeleton(isLoading: Boolean): SelectSkeleton {
   return SelectSkeleton(isLoading)
       .also { add(it) }
}