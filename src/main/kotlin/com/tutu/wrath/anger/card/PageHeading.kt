package com.tutu.wrath.anger.card

import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.div
import io.kvision.html.h2


fun Container.pageHeading(title: String, mainAction: (Container.() -> Button)) {
    div(className = "flex w-full justify-between rounded bg-white p-4 border dark:bg-neutral-700 dark:border-neutral-800") {
        h2(title, className = "text-xl font-bold leading-loose text-neutral-800 dark:text-neutral-50")

        mainAction()
    }

}

/*<!-- Button trigger modal -->
<button
  type="button"
  class="inline-block rounded bg-primary px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)]"
  data-te-toggle="modal"
  data-te-target="#exampleModal"
  data-te-ripple-init
  data-te-ripple-color="light">
  Launch demo modal
</button>*/
