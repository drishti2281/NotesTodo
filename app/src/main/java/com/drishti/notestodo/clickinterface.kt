package com.drishti.notestodo

interface clickinterface {
    abstract fun onDeleteListener(data: NotesEntity)
    abstract fun onClickListener(data: NotesEntity)
    abstract fun onShareListener(data: NotesEntity)
}