package gg.aquatic.aquaticseries.lib.editor.editable

import gg.aquatic.aquaticseries.lib.editor.IEditable
import java.util.function.Supplier

class EditableList<T>(
    val values: MutableList<T>,
    val factory: Supplier<T>,
): IEditable<T> {
}