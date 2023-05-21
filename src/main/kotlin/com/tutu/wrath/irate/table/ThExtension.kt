package com.tutu.wrath.irate.table

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.util.ContainerAttributes
import com.tutu.wrath.util.ContainerStyle
import com.tutu.wrath.util.className
import io.kvision.html.Th
import io.kvision.html.Tr

class ThExtension {

    companion object {
        fun Tr.th(
            content: String? = null,
            className: ContainerStyle? = null,
            attributes: ContainerAttributes? = null,
            init: (Th.() -> Unit)? = null
        ): Th {
            val th = Th(content, rich, align, className?.className(), init)
            attributes?.let { th.attributes(*it.all()) }
            this.add(th)
            return th
        }
    }

}