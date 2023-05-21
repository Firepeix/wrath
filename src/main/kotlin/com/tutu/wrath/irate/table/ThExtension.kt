package com.tutu.wrath.irate.table

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.util.ContainerMetadata
import io.kvision.core.StringPair
import io.kvision.html.Th
import io.kvision.html.Tr

class ThExtension {

    companion object {
        fun Tr.th(
            content: String? = null,
            className: ContainerMetadata? = null,
            attributes: Array<StringPair>? = null,
            init: (Th.() -> Unit)? = null
        ): Th {
            val th = Th(content, rich, align, className?.className(), init)
            attributes?.let { th.attributes(*it) }
            this.add(th)
            return th
        }
    }

}