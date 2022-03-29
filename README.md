# ClassesTransformer

It's used to modify .class file use ASM.

Now it can be used to debounce click event, includes 
setOnClickListener(this)
setOnClickListener(object: OnClickListener...)
setOnClickListener{// lambda}
onClick=method() in xml

Add extra method by annotation
@DebounceClick

Exclude method by annotation
@NoDebounceClick

# TODO
Support super class implement OnClickListener interface and subclass implement onClick method.
Support ListView&RecyclerView onClick
Support UnknownView onClick