package imagetrack.app.listener

import imagetrack.app.enums.Choice

interface OnItemChoiceListener<T> {

    fun clickItem(item :T, choice : Choice)

}