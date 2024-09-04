package gg.aquatic.aquaticseries.lib.editor.editable

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.IEditable

class Editable<T>(var value: T, val editType: Edit<T>) : IEditable<T> {
}